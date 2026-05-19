package com.ucb.Rapidex.service;

import com.ucb.Rapidex.model.Agendamento;
import com.ucb.Rapidex.model.StatusAgendamento;
import com.ucb.Rapidex.repository.AgendamentoRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.UUID;

@Service
public class AgendamentoService {

    private final AgendamentoRepository repository;

    public AgendamentoService(AgendamentoRepository repository) {
        this.repository = repository;
    }

    public Agendamento salvar(Agendamento agendamento) {
        return repository.save(agendamento);
    }

    public Agendamento obterPorId(UUID id) {
        return repository.findById(id)
                .orElseThrow(() -> new NoSuchElementException("Agendamento não encontrado: " + id));
    }

    public Agendamento obterPorPedido(UUID pedidoId) {
        return repository.findByPedidoId(pedidoId)
                .orElseThrow(() -> new NoSuchElementException("Agendamento não encontrado para o pedido: " + pedidoId));
    }

    public List<Agendamento> listarPorData(LocalDate data) {
        return repository.findByData(data);
    }

    @Transactional
    public Agendamento reagendar(UUID agendamentoId, LocalDate novaData, LocalTime novaHoraInicio) {
        var agendamento = obterPorId(agendamentoId);

        if (agendamento.getStatus() == StatusAgendamento.CANCELADO || agendamento.getStatus() == StatusAgendamento.CONCLUIDO) {
            throw new IllegalStateException("Agendamento não pode ser remarcado. Status atual: " + agendamento.getStatus());
        }

        agendamento.setData(novaData);
        agendamento.setHoraInicio(novaHoraInicio);
        agendamento.setStatus(StatusAgendamento.REAGENDADO);
        return repository.save(agendamento);
    }
}

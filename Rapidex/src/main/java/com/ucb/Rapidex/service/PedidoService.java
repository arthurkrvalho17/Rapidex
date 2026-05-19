package com.ucb.Rapidex.service;

import com.ucb.Rapidex.model.Agendamento;
import com.ucb.Rapidex.model.Pedido;
import com.ucb.Rapidex.model.StatusAgendamento;
import com.ucb.Rapidex.model.StatusPedido;
import com.ucb.Rapidex.repository.AgendamentoRepository;
import com.ucb.Rapidex.repository.PedidoRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.OffsetDateTime;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.UUID;

@Service
public class PedidoService {

    private final PedidoRepository repository;
    private final AgendamentoRepository agendamentoRepository;

    public PedidoService(PedidoRepository repository, AgendamentoRepository agendamentoRepository) {
        this.repository = repository;
        this.agendamentoRepository = agendamentoRepository;
    }

    public Pedido salvar(Pedido pedido) {
        return repository.save(pedido);
    }

    public Pedido obterPorId(UUID id) {
        return repository.findById(id)
                .orElseThrow(() -> new NoSuchElementException("Pedido não encontrado: " + id));
    }

    public List<Pedido> listarPorPrestador(UUID prestadorId) {
        return repository.findByPrestadorId(prestadorId);
    }

    public List<Pedido> listarPorContratante(UUID contratanteId) {
        return repository.findByContratanteId(contratanteId);
    }

    public List<Pedido> listarPorPrestadorEStatus(UUID prestadorId, StatusPedido status) {
        return repository.findByPrestadorIdAndStatus(prestadorId, status);
    }

    @Transactional
    public Agendamento aceitar(UUID pedidoId, LocalDate data, LocalTime horaInicio) {
        var pedido = obterPorId(pedidoId);

        if (pedido.getStatus() != StatusPedido.PENDING) {
            throw new IllegalStateException("Apenas pedidos pendentes podem ser aceitos. Status atual: " + pedido.getStatus());
        }

        pedido.setStatus(StatusPedido.ACCEPTED);
        pedido.setAtualizadoEm(OffsetDateTime.now());
        repository.save(pedido);

        var agendamento = new Agendamento();
        agendamento.setPedidoId(pedidoId);
        agendamento.setData(data);
        agendamento.setHoraInicio(horaInicio);
        agendamento.setConfirmadoPrestador(true);
        agendamento.setStatus(StatusAgendamento.AGENDADO);
        agendamento.setCriadoEm(OffsetDateTime.now());
        return agendamentoRepository.save(agendamento);
    }

    @Transactional
    public void recusar(UUID pedidoId) {
        var pedido = obterPorId(pedidoId);

        if (pedido.getStatus() != StatusPedido.PENDING) {
            throw new IllegalStateException("Apenas pedidos pendentes podem ser recusados. Status atual: " + pedido.getStatus());
        }

        pedido.setStatus(StatusPedido.REFUSED);
        pedido.setAtualizadoEm(OffsetDateTime.now());
        repository.save(pedido);
    }
}

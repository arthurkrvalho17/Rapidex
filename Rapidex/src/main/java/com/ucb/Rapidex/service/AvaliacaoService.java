package com.ucb.Rapidex.service;

import com.ucb.Rapidex.model.Avaliacao;
import com.ucb.Rapidex.repository.AvaliacaoRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.UUID;

@Service
public class AvaliacaoService {

    private final AvaliacaoRepository repository;

    public AvaliacaoService(AvaliacaoRepository repository) {
        this.repository = repository;
    }

    public Avaliacao salvar(Avaliacao avaliacao) {
        return repository.save(avaliacao);
    }

    public Avaliacao obterPorId(UUID id) {
        return repository.findById(id)
                .orElseThrow(() -> new NoSuchElementException("Avaliação não encontrada: " + id));
    }

    public Avaliacao obterPorPedido(UUID pedidoId) {
        return repository.findByPedidoId(pedidoId)
                .orElseThrow(() -> new NoSuchElementException("Avaliação não encontrada para o pedido: " + pedidoId));
    }

    public List<Avaliacao> listarPorAvaliador(UUID avaliadorId) {
        return repository.findByAvaliadorId(avaliadorId);
    }
}

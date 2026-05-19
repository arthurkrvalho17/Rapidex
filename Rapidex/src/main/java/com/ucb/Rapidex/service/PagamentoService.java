package com.ucb.Rapidex.service;

import com.ucb.Rapidex.model.Pagamento;
import com.ucb.Rapidex.repository.PagamentoRepository;
import org.springframework.stereotype.Service;

import java.util.NoSuchElementException;
import java.util.UUID;

@Service
public class PagamentoService {

    private final PagamentoRepository repository;

    public PagamentoService(PagamentoRepository repository) {
        this.repository = repository;
    }

    public Pagamento salvar(Pagamento pagamento) {
        return repository.save(pagamento);
    }

    public Pagamento obterPorId(UUID id) {
        return repository.findById(id)
                .orElseThrow(() -> new NoSuchElementException("Pagamento não encontrado: " + id));
    }

    public Pagamento obterPorPedido(UUID pedidoId) {
        return repository.findByPedidoId(pedidoId)
                .orElseThrow(() -> new NoSuchElementException("Pagamento não encontrado para o pedido: " + pedidoId));
    }
}

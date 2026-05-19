package com.ucb.Rapidex.service;

import com.ucb.Rapidex.model.ServicoOferecido;
import com.ucb.Rapidex.repository.ServicoOferecidoRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.UUID;

@Service
public class ServicoOferecidoService {

    private final ServicoOferecidoRepository repository;

    public ServicoOferecidoService(ServicoOferecidoRepository repository) {
        this.repository = repository;
    }

    public ServicoOferecido salvar(ServicoOferecido servico) {
        return repository.save(servico);
    }

    public ServicoOferecido obterPorId(UUID id) {
        return repository.findById(id)
                .orElseThrow(() -> new NoSuchElementException("Serviço não encontrado: " + id));
    }

    public List<ServicoOferecido> listarPorPrestador(UUID prestadorId) {
        return repository.findByPrestadorId(prestadorId);
    }
}

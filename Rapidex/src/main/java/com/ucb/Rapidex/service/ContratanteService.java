package com.ucb.Rapidex.service;

import com.ucb.Rapidex.model.Contratante;
import com.ucb.Rapidex.repository.ContratanteRepository;
import org.springframework.stereotype.Service;

import java.util.NoSuchElementException;
import java.util.UUID;

@Service
public class ContratanteService {

    private final ContratanteRepository repository;

    public ContratanteService(ContratanteRepository repository) {
        this.repository = repository;
    }

    public Contratante salvar(Contratante contratante) {
        return repository.save(contratante);
    }

    public Contratante obterPorId(UUID id) {
        return repository.findById(id)
                .orElseThrow(() -> new NoSuchElementException("Contratante não encontrado: " + id));
    }

    public Contratante obterPorUsuarioId(UUID usuarioId) {
        return repository.findByUsuarioId(usuarioId)
                .orElseThrow(() -> new NoSuchElementException("Contratante não encontrado para o usuário: " + usuarioId));
    }
}

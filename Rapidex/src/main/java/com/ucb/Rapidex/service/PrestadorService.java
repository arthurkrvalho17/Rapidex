package com.ucb.Rapidex.service;

import com.ucb.Rapidex.model.Prestador;
import com.ucb.Rapidex.repository.PrestadorRepository;
import org.springframework.stereotype.Service;

import java.util.NoSuchElementException;
import java.util.UUID;

@Service
public class PrestadorService {

    private final PrestadorRepository repository;

    public PrestadorService(PrestadorRepository repository) {
        this.repository = repository;
    }

    public Prestador salvar(Prestador prestador) {
        return repository.save(prestador);
    }

    public Prestador obterPorId(UUID id) {
        return repository.findById(id)
                .orElseThrow(() -> new NoSuchElementException("Prestador não encontrado: " + id));
    }

    public Prestador obterPorUsuarioId(UUID usuarioId) {
        return repository.findByUsuarioId(usuarioId)
                .orElseThrow(() -> new NoSuchElementException("Prestador não encontrado para o usuário: " + usuarioId));
    }
}

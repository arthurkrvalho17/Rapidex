package com.ucb.Rapidex.repository;

import com.ucb.Rapidex.model.Prestador;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface PrestadorRepository extends JpaRepository<Prestador, UUID> {

    Optional<Prestador> findByUsuarioId(UUID usuarioId);
}

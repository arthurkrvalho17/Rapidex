package com.ucb.Rapidex.repository;

import com.ucb.Rapidex.model.Contratante;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface ContratanteRepository extends JpaRepository<Contratante, UUID> {

    Optional<Contratante> findByUsuarioId(UUID usuarioId);
}

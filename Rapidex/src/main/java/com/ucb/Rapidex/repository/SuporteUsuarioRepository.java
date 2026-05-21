package com.ucb.Rapidex.repository;

import com.ucb.Rapidex.model.SuporteUsuario;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;
import java.util.UUID;

public interface SuporteUsuarioRepository extends JpaRepository<SuporteUsuario, UUID> {
    Optional<SuporteUsuario> findByEmail(String email);
}
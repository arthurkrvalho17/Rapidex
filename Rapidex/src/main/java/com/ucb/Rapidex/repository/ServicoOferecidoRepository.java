package com.ucb.Rapidex.repository;

import com.ucb.Rapidex.model.ServicoOferecido;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface ServicoOferecidoRepository extends JpaRepository<ServicoOferecido, UUID> {

    List<ServicoOferecido> findByPrestadorId(UUID prestadorId);
}

package com.ucb.Rapidex.repository;

import com.ucb.Rapidex.model.Avaliacao;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface AvaliacaoRepository extends JpaRepository<Avaliacao, UUID> {

    Optional<Avaliacao> findByPedidoId(UUID pedidoId);

    List<Avaliacao> findByAvaliadorId(UUID avaliadorId);
}

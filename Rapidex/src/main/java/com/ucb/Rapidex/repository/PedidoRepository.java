package com.ucb.Rapidex.repository;

import com.ucb.Rapidex.model.Pedido;
import com.ucb.Rapidex.model.StatusPedido;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface PedidoRepository extends JpaRepository<Pedido, UUID> {

    List<Pedido> findByPrestadorId(UUID prestadorId);

    List<Pedido> findByContratanteId(UUID contratanteId);

    List<Pedido> findByPrestadorIdAndStatus(UUID prestadorId, StatusPedido status);
}

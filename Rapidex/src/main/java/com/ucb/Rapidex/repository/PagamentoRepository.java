package com.ucb.Rapidex.repository;

import com.ucb.Rapidex.model.Pagamento;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface PagamentoRepository extends JpaRepository<Pagamento, UUID> {

    Optional<Pagamento> findByPedidoId(UUID pedidoId);
}

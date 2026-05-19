package com.ucb.Rapidex.repository;

import com.ucb.Rapidex.model.Agendamento;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.util.Collection;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface AgendamentoRepository extends JpaRepository<Agendamento, UUID> {

    Optional<Agendamento> findByPedidoId(UUID pedidoId);

    List<Agendamento> findByData(LocalDate data);

    List<Agendamento> findByPedidoIdIn(Collection<UUID> pedidoIds);
}

package com.ucb.Rapidex.repository;

import com.ucb.Rapidex.model.Chamado;
import com.ucb.Rapidex.model.PrioridadeChamado;
import com.ucb.Rapidex.model.StatusChamado;
import org.springframework.data.jpa.repository.JpaRepository;
import java.time.OffsetDateTime;
import java.util.List;
import java.util.UUID;

public interface ChamadoRepository extends JpaRepository<Chamado, UUID> {
    List<Chamado> findByStatusOrderByCriadoEmDesc(StatusChamado status);
    List<Chamado> findByStatusInOrderByCriadoEmDesc(List<StatusChamado> statuses);
    List<Chamado> findByUsuarioIdOrderByCriadoEmDesc(UUID usuarioId);
    long countByStatus(StatusChamado status);
    long countByPrioridade(PrioridadeChamado prioridade);
    long countByStatusAndResolvidoEmAfter(StatusChamado status, OffsetDateTime after);
}
package com.ucb.Rapidex.mapper;

import com.ucb.Rapidex.controller.dto.PedidoRequestDto;
import com.ucb.Rapidex.controller.dto.PedidoResponseDto;
import com.ucb.Rapidex.model.Pedido;
import com.ucb.Rapidex.model.StatusPedido;
import org.springframework.stereotype.Component;

import java.time.OffsetDateTime;
import java.util.List;

@Component
public class PedidoMapper {

    public Pedido toEntity(PedidoRequestDto dto) {
        var p = new Pedido();
        p.setPrestadorId(dto.prestadorId());
        p.setContratanteId(dto.contratanteId());
        p.setServicoId(dto.servicoId());
        p.setDataSugerida(dto.dataSugerida());
        p.setHoraSugerida(dto.horaSugerida());
        p.setDescricao(dto.descricao());
        p.setStatus(StatusPedido.PENDING);
        p.setCriadoEm(OffsetDateTime.now());
        p.setAtualizadoEm(OffsetDateTime.now());
        return p;
    }

    public PedidoResponseDto toResponseDto(Pedido p) {
        return new PedidoResponseDto(
                p.getId(),
                p.getPrestadorId(),
                p.getContratanteId(),
                p.getServicoId(),
                p.getStatus(),
                p.getDataSugerida(),
                p.getHoraSugerida(),
                p.getDescricao(),
                p.getCriadoEm(),
                p.getAtualizadoEm());
    }

    public List<PedidoResponseDto> toResponseDtoList(List<Pedido> pedidos) {
        return pedidos.stream().map(this::toResponseDto).toList();
    }
}

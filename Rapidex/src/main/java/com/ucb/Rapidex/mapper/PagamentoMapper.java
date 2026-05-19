package com.ucb.Rapidex.mapper;

import com.ucb.Rapidex.controller.dto.PagamentoRequestDto;
import com.ucb.Rapidex.controller.dto.PagamentoResponseDto;
import com.ucb.Rapidex.model.Pagamento;
import com.ucb.Rapidex.model.StatusPagamento;
import org.springframework.stereotype.Component;

import java.time.OffsetDateTime;

@Component
public class PagamentoMapper {

    public Pagamento toEntity(PagamentoRequestDto dto) {
        var p = new Pagamento();
        p.setPedidoId(dto.pedidoId());
        p.setValor(dto.valor());
        p.setStatus(StatusPagamento.PENDENTE);
        p.setCriadoEm(OffsetDateTime.now());
        return p;
    }

    public PagamentoResponseDto toResponseDto(Pagamento p) {
        return new PagamentoResponseDto(
                p.getId(),
                p.getPedidoId(),
                p.getValor(),
                p.getStatus(),
                p.getPagoEm(),
                p.getCriadoEm());
    }
}

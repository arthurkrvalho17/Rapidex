package com.ucb.Rapidex.mapper;

import com.ucb.Rapidex.controller.dto.AvaliacaoRequestDto;
import com.ucb.Rapidex.controller.dto.AvaliacaoResponseDto;
import com.ucb.Rapidex.model.Avaliacao;
import org.springframework.stereotype.Component;

import java.time.OffsetDateTime;
import java.util.List;

@Component
public class AvaliacaoMapper {

    public Avaliacao toEntity(AvaliacaoRequestDto dto) {
        var a = new Avaliacao();
        a.setPedidoId(dto.pedidoId());
        a.setAvaliadorId(dto.avaliadorId());
        a.setNota(dto.nota());
        a.setComentario(dto.comentario());
        a.setCriadoEm(OffsetDateTime.now());
        return a;
    }

    public AvaliacaoResponseDto toResponseDto(Avaliacao a) {
        return new AvaliacaoResponseDto(
                a.getId(),
                a.getPedidoId(),
                a.getAvaliadorId(),
                a.getNota(),
                a.getComentario(),
                a.getCriadoEm());
    }

    public List<AvaliacaoResponseDto> toResponseDtoList(List<Avaliacao> avaliacoes) {
        return avaliacoes.stream().map(this::toResponseDto).toList();
    }
}

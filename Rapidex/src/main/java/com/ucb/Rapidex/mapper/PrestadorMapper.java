package com.ucb.Rapidex.mapper;

import com.ucb.Rapidex.controller.dto.PrestadorRequestDto;
import com.ucb.Rapidex.controller.dto.PrestadorResponseDto;
import com.ucb.Rapidex.model.Prestador;
import com.ucb.Rapidex.model.StatusPrestador;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.time.OffsetDateTime;
import java.util.List;

@Component
public class PrestadorMapper {

    public Prestador toEntity(PrestadorRequestDto dto) {
        var p = new Prestador();
        p.setUsuarioId(dto.usuarioId());
        p.setNome(dto.nome());
        p.setTelefone(dto.telefone());
        p.setFotoUrl(dto.fotoUrl());
        p.setStatus(StatusPrestador.OFFLINE);
        p.setAvaliacaoMedia(BigDecimal.ZERO);
        p.setTotalAvaliacoes(0);
        p.setCriadoEm(OffsetDateTime.now());
        return p;
    }

    public PrestadorResponseDto toResponseDto(Prestador p) {
        return new PrestadorResponseDto(
                p.getId(),
                p.getUsuarioId(),
                p.getNome(),
                p.getTelefone(),
                p.getFotoUrl(),
                p.getStatus(),
                p.getAvaliacaoMedia(),
                p.getTotalAvaliacoes(),
                p.getCidade(),
                p.getUf(),
                p.getAreasAtuacao(),
                p.getCriadoEm());
    }

    public List<PrestadorResponseDto> toResponseDtoList(List<Prestador> prestadores) {
        return prestadores.stream().map(this::toResponseDto).toList();
    }
}

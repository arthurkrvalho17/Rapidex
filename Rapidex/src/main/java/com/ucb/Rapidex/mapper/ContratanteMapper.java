package com.ucb.Rapidex.mapper;

import com.ucb.Rapidex.controller.dto.ContratanteRequestDto;
import com.ucb.Rapidex.controller.dto.ContratanteResponseDto;
import com.ucb.Rapidex.model.Contratante;
import org.springframework.stereotype.Component;

import java.time.OffsetDateTime;

@Component
public class ContratanteMapper {

    public Contratante toEntity(ContratanteRequestDto dto) {
        var c = new Contratante();
        c.setUsuarioId(dto.usuarioId());
        c.setNome(dto.nome());
        c.setTelefone(dto.telefone());
        c.setFotoUrl(dto.fotoUrl());
        c.setCriadoEm(OffsetDateTime.now());
        return c;
    }

    public ContratanteResponseDto toResponseDto(Contratante c) {
        return new ContratanteResponseDto(
                c.getId(),
                c.getUsuarioId(),
                c.getNome(),
                c.getTelefone(),
                c.getFotoUrl(),
                c.getCriadoEm());
    }
}

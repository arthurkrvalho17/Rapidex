package com.ucb.Rapidex.mapper;

import com.ucb.Rapidex.controller.dto.ServicoOferecidoRequestDto;
import com.ucb.Rapidex.controller.dto.ServicoOferecidoResponseDto;
import com.ucb.Rapidex.model.ServicoOferecido;
import org.springframework.stereotype.Component;

import java.time.OffsetDateTime;
import java.util.List;

@Component
public class ServicoOferecidoMapper {

    public ServicoOferecido toEntity(ServicoOferecidoRequestDto dto) {
        var s = new ServicoOferecido();
        s.setPrestadorId(dto.prestadorId());
        s.setNome(dto.nome());
        s.setDescricao(dto.descricao());
        s.setPrecoBase(dto.precoBase());
        s.setUnidadePreco(dto.unidadePreco());
        s.setCriadoEm(OffsetDateTime.now());
        return s;
    }

    public ServicoOferecidoResponseDto toResponseDto(ServicoOferecido s) {
        return new ServicoOferecidoResponseDto(
                s.getId(),
                s.getPrestadorId(),
                s.getNome(),
                s.getDescricao(),
                s.getPrecoBase(),
                s.getUnidadePreco(),
                s.getCriadoEm());
    }

    public List<ServicoOferecidoResponseDto> toResponseDtoList(List<ServicoOferecido> servicos) {
        return servicos.stream().map(this::toResponseDto).toList();
    }
}

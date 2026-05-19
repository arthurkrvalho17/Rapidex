package com.ucb.Rapidex.mapper;

import com.ucb.Rapidex.controller.dto.AgendamentoRequestDto;
import com.ucb.Rapidex.controller.dto.AgendamentoResponseDto;
import com.ucb.Rapidex.model.Agendamento;
import com.ucb.Rapidex.model.StatusAgendamento;
import org.springframework.stereotype.Component;

import java.time.OffsetDateTime;
import java.util.List;

@Component
public class AgendamentoMapper {

    public Agendamento toEntity(AgendamentoRequestDto dto) {
        var a = new Agendamento();
        a.setPedidoId(dto.pedidoId());
        a.setData(dto.data());
        a.setHoraInicio(dto.horaInicio());
        a.setConfirmadoPrestador(false);
        a.setStatus(StatusAgendamento.AGENDADO);
        a.setCriadoEm(OffsetDateTime.now());
        return a;
    }

    public AgendamentoResponseDto toResponseDto(Agendamento a) {
        return new AgendamentoResponseDto(
                a.getId(),
                a.getPedidoId(),
                a.getData(),
                a.getHoraInicio(),
                a.isConfirmadoPrestador(),
                a.getStatus(),
                a.getCriadoEm());
    }

    public List<AgendamentoResponseDto> toResponseDtoList(List<Agendamento> agendamentos) {
        return agendamentos.stream().map(this::toResponseDto).toList();
    }
}

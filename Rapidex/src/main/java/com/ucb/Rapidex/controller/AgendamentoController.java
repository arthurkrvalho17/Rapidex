package com.ucb.Rapidex.controller;

import com.ucb.Rapidex.controller.dto.AgendamentoRequestDto;
import com.ucb.Rapidex.controller.dto.AgendamentoResponseDto;
import com.ucb.Rapidex.controller.dto.ReagendarDto;
import com.ucb.Rapidex.mapper.AgendamentoMapper;
import com.ucb.Rapidex.service.AgendamentoService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/agendamentos")
public class AgendamentoController {

    private final AgendamentoService service;
    private final AgendamentoMapper mapper;

    public AgendamentoController(AgendamentoService service, AgendamentoMapper mapper) {
        this.service = service;
        this.mapper = mapper;
    }

    @PostMapping
    public ResponseEntity<AgendamentoResponseDto> criar(@RequestBody AgendamentoRequestDto dto) {
        var agendamento = service.salvar(mapper.toEntity(dto));
        return ResponseEntity.status(HttpStatus.CREATED).body(mapper.toResponseDto(agendamento));
    }

    @GetMapping("/{id}")
    public ResponseEntity<AgendamentoResponseDto> obterPorId(@PathVariable UUID id) {
        return ResponseEntity.ok(mapper.toResponseDto(service.obterPorId(id)));
    }

    @GetMapping("/pedido/{pedidoId}")
    public ResponseEntity<AgendamentoResponseDto> obterPorPedido(@PathVariable UUID pedidoId) {
        return ResponseEntity.ok(mapper.toResponseDto(service.obterPorPedido(pedidoId)));
    }

    @GetMapping("/data/{data}")
    public ResponseEntity<List<AgendamentoResponseDto>> listarPorData(@PathVariable LocalDate data) {
        return ResponseEntity.ok(mapper.toResponseDtoList(service.listarPorData(data)));
    }

    @PatchMapping("/{id}/reagendar")
    public ResponseEntity<AgendamentoResponseDto> reagendar(@PathVariable UUID id, @RequestBody ReagendarDto dto) {
        return ResponseEntity.ok(mapper.toResponseDto(service.reagendar(id, dto.novaData(), dto.novaHoraInicio())));
    }
}

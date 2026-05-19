package com.ucb.Rapidex.controller;

import com.ucb.Rapidex.controller.dto.ServicoOferecidoRequestDto;
import com.ucb.Rapidex.controller.dto.ServicoOferecidoResponseDto;
import com.ucb.Rapidex.mapper.ServicoOferecidoMapper;
import com.ucb.Rapidex.service.ServicoOferecidoService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/servicos")
public class ServicoOferecidoController {

    private final ServicoOferecidoService service;
    private final ServicoOferecidoMapper mapper;

    public ServicoOferecidoController(ServicoOferecidoService service, ServicoOferecidoMapper mapper) {
        this.service = service;
        this.mapper = mapper;
    }

    @PostMapping
    public ResponseEntity<ServicoOferecidoResponseDto> criar(@RequestBody ServicoOferecidoRequestDto dto) {
        var servico = service.salvar(mapper.toEntity(dto));
        return ResponseEntity.status(HttpStatus.CREATED).body(mapper.toResponseDto(servico));
    }

    @GetMapping("/{id}")
    public ResponseEntity<ServicoOferecidoResponseDto> obterPorId(@PathVariable UUID id) {
        return ResponseEntity.ok(mapper.toResponseDto(service.obterPorId(id)));
    }

    @GetMapping("/prestador/{prestadorId}")
    public ResponseEntity<List<ServicoOferecidoResponseDto>> listarPorPrestador(@PathVariable UUID prestadorId) {
        return ResponseEntity.ok(mapper.toResponseDtoList(service.listarPorPrestador(prestadorId)));
    }
}

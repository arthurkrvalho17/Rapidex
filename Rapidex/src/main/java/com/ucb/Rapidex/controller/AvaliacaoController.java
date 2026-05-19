package com.ucb.Rapidex.controller;

import com.ucb.Rapidex.controller.dto.AvaliacaoRequestDto;
import com.ucb.Rapidex.controller.dto.AvaliacaoResponseDto;
import com.ucb.Rapidex.mapper.AvaliacaoMapper;
import com.ucb.Rapidex.service.AvaliacaoService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/avaliacoes")
public class AvaliacaoController {

    private final AvaliacaoService service;
    private final AvaliacaoMapper mapper;

    public AvaliacaoController(AvaliacaoService service, AvaliacaoMapper mapper) {
        this.service = service;
        this.mapper = mapper;
    }

    @PostMapping
    public ResponseEntity<AvaliacaoResponseDto> criar(@RequestBody AvaliacaoRequestDto dto) {
        var avaliacao = service.salvar(mapper.toEntity(dto));
        return ResponseEntity.status(HttpStatus.CREATED).body(mapper.toResponseDto(avaliacao));
    }

    @GetMapping("/{id}")
    public ResponseEntity<AvaliacaoResponseDto> obterPorId(@PathVariable UUID id) {
        return ResponseEntity.ok(mapper.toResponseDto(service.obterPorId(id)));
    }

    @GetMapping("/pedido/{pedidoId}")
    public ResponseEntity<AvaliacaoResponseDto> obterPorPedido(@PathVariable UUID pedidoId) {
        return ResponseEntity.ok(mapper.toResponseDto(service.obterPorPedido(pedidoId)));
    }

    @GetMapping("/avaliador/{avaliadorId}")
    public ResponseEntity<List<AvaliacaoResponseDto>> listarPorAvaliador(@PathVariable UUID avaliadorId) {
        return ResponseEntity.ok(mapper.toResponseDtoList(service.listarPorAvaliador(avaliadorId)));
    }
}

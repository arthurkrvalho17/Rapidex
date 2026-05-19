package com.ucb.Rapidex.controller;

import com.ucb.Rapidex.controller.dto.ContratanteRequestDto;
import com.ucb.Rapidex.controller.dto.ContratanteResponseDto;
import com.ucb.Rapidex.mapper.ContratanteMapper;
import com.ucb.Rapidex.service.ContratanteService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/contratantes")
public class ContratanteController {

    private final ContratanteService service;
    private final ContratanteMapper mapper;

    public ContratanteController(ContratanteService service, ContratanteMapper mapper) {
        this.service = service;
        this.mapper = mapper;
    }

    @PostMapping
    public ResponseEntity<ContratanteResponseDto> criar(@RequestBody ContratanteRequestDto dto) {
        var contratante = service.salvar(mapper.toEntity(dto));
        return ResponseEntity.status(HttpStatus.CREATED).body(mapper.toResponseDto(contratante));
    }

    @GetMapping("/{id}")
    public ResponseEntity<ContratanteResponseDto> obterPorId(@PathVariable UUID id) {
        return ResponseEntity.ok(mapper.toResponseDto(service.obterPorId(id)));
    }

    @GetMapping("/usuario/{usuarioId}")
    public ResponseEntity<ContratanteResponseDto> obterPorUsuarioId(@PathVariable UUID usuarioId) {
        return ResponseEntity.ok(mapper.toResponseDto(service.obterPorUsuarioId(usuarioId)));
    }
}

package com.ucb.Rapidex.controller;

import com.ucb.Rapidex.controller.dto.PrestadorRequestDto;
import com.ucb.Rapidex.controller.dto.PrestadorResponseDto;
import com.ucb.Rapidex.mapper.PrestadorMapper;
import com.ucb.Rapidex.service.PrestadorService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/prestadores")
public class PrestadorController {

    private final PrestadorService service;
    private final PrestadorMapper mapper;

    public PrestadorController(PrestadorService service, PrestadorMapper mapper) {
        this.service = service;
        this.mapper = mapper;
    }

    @PostMapping
    public ResponseEntity<PrestadorResponseDto> criar(@RequestBody PrestadorRequestDto dto) {
        var prestador = service.salvar(mapper.toEntity(dto));
        return ResponseEntity.status(HttpStatus.CREATED).body(mapper.toResponseDto(prestador));
    }

    @GetMapping("/{id}")
    public ResponseEntity<PrestadorResponseDto> obterPorId(@PathVariable UUID id) {
        return ResponseEntity.ok(mapper.toResponseDto(service.obterPorId(id)));
    }

    @GetMapping("/usuario/{usuarioId}")
    public ResponseEntity<PrestadorResponseDto> obterPorUsuarioId(@PathVariable UUID usuarioId) {
        return ResponseEntity.ok(mapper.toResponseDto(service.obterPorUsuarioId(usuarioId)));
    }
}

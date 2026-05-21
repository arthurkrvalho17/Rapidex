package com.ucb.Rapidex.controller;

import com.ucb.Rapidex.controller.dto.ChamadoResponseDto;
import com.ucb.Rapidex.controller.dto.StatusUpdateDto;
import com.ucb.Rapidex.service.ChamadoService;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/suporte/chamados")
public class SuporteChamadoApiController {

    private final ChamadoService chamadoService;

    public SuporteChamadoApiController(ChamadoService chamadoService) {
        this.chamadoService = chamadoService;
    }

    @GetMapping("/{id}")
    public ChamadoResponseDto buscarPorId(@PathVariable UUID id) {
        return chamadoService.buscarPorId(id);
    }

    @PatchMapping("/{id}/status")
    public ChamadoResponseDto atualizarStatus(@PathVariable UUID id,
                                               @Valid @RequestBody StatusUpdateDto dto) {
        return chamadoService.atualizarStatus(id, dto.status());
    }
}
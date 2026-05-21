package com.ucb.Rapidex.controller;

import com.ucb.Rapidex.controller.dto.CadastroRequestDto;
import com.ucb.Rapidex.controller.dto.CadastroResponseDto;
import com.ucb.Rapidex.service.CadastroService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/cadastro")
public class CadastroController {

    private final CadastroService service;

    public CadastroController(CadastroService service) {
        this.service = service;
    }

    @PostMapping
    public ResponseEntity<CadastroResponseDto> cadastrar(@Valid @RequestBody CadastroRequestDto dto) {
        return ResponseEntity.status(HttpStatus.CREATED).body(service.cadastrar(dto));
    }
}

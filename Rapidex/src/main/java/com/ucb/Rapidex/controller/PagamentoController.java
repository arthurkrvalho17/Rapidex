package com.ucb.Rapidex.controller;

import com.ucb.Rapidex.controller.dto.PagamentoRequestDto;
import com.ucb.Rapidex.controller.dto.PagamentoResponseDto;
import com.ucb.Rapidex.mapper.PagamentoMapper;
import com.ucb.Rapidex.service.PagamentoService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/pagamentos")
public class PagamentoController {

    private final PagamentoService service;
    private final PagamentoMapper mapper;

    public PagamentoController(PagamentoService service, PagamentoMapper mapper) {
        this.service = service;
        this.mapper = mapper;
    }

    @PostMapping
    public ResponseEntity<PagamentoResponseDto> criar(@RequestBody PagamentoRequestDto dto) {
        var pagamento = service.salvar(mapper.toEntity(dto));
        return ResponseEntity.status(HttpStatus.CREATED).body(mapper.toResponseDto(pagamento));
    }

    @GetMapping("/{id}")
    public ResponseEntity<PagamentoResponseDto> obterPorId(@PathVariable UUID id) {
        return ResponseEntity.ok(mapper.toResponseDto(service.obterPorId(id)));
    }

    @GetMapping("/pedido/{pedidoId}")
    public ResponseEntity<PagamentoResponseDto> obterPorPedido(@PathVariable UUID pedidoId) {
        return ResponseEntity.ok(mapper.toResponseDto(service.obterPorPedido(pedidoId)));
    }
}

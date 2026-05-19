package com.ucb.Rapidex.controller;

import com.ucb.Rapidex.controller.dto.AceitarPedidoDto;
import com.ucb.Rapidex.controller.dto.AgendamentoResponseDto;
import com.ucb.Rapidex.controller.dto.PedidoRequestDto;
import com.ucb.Rapidex.controller.dto.PedidoResponseDto;
import com.ucb.Rapidex.mapper.AgendamentoMapper;
import com.ucb.Rapidex.mapper.PedidoMapper;
import com.ucb.Rapidex.model.StatusPedido;
import com.ucb.Rapidex.service.PedidoService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/pedidos")
public class PedidoController {

    private final PedidoService service;
    private final PedidoMapper pedidoMapper;
    private final AgendamentoMapper agendamentoMapper;

    public PedidoController(PedidoService service, PedidoMapper pedidoMapper, AgendamentoMapper agendamentoMapper) {
        this.service = service;
        this.pedidoMapper = pedidoMapper;
        this.agendamentoMapper = agendamentoMapper;
    }

    @PostMapping
    public ResponseEntity<PedidoResponseDto> criar(@RequestBody PedidoRequestDto dto) {
        var pedido = service.salvar(pedidoMapper.toEntity(dto));
        return ResponseEntity.status(HttpStatus.CREATED).body(pedidoMapper.toResponseDto(pedido));
    }

    @GetMapping("/{id}")
    public ResponseEntity<PedidoResponseDto> obterPorId(@PathVariable UUID id) {
        return ResponseEntity.ok(pedidoMapper.toResponseDto(service.obterPorId(id)));
    }

    @GetMapping("/prestador/{prestadorId}")
    public ResponseEntity<List<PedidoResponseDto>> listarPorPrestador(@PathVariable UUID prestadorId) {
        return ResponseEntity.ok(pedidoMapper.toResponseDtoList(service.listarPorPrestador(prestadorId)));
    }

    @GetMapping("/contratante/{contratanteId}")
    public ResponseEntity<List<PedidoResponseDto>> listarPorContratante(@PathVariable UUID contratanteId) {
        return ResponseEntity.ok(pedidoMapper.toResponseDtoList(service.listarPorContratante(contratanteId)));
    }

    @GetMapping("/prestador/{prestadorId}/status/{status}")
    public ResponseEntity<List<PedidoResponseDto>> listarPorPrestadorEStatus(
            @PathVariable UUID prestadorId,
            @PathVariable StatusPedido status) {
        return ResponseEntity.ok(pedidoMapper.toResponseDtoList(service.listarPorPrestadorEStatus(prestadorId, status)));
    }

    @PatchMapping("/{id}/aceitar")
    public ResponseEntity<AgendamentoResponseDto> aceitar(@PathVariable UUID id, @RequestBody AceitarPedidoDto dto) {
        var agendamento = service.aceitar(id, dto.data(), dto.horaInicio());
        return ResponseEntity.status(HttpStatus.CREATED).body(agendamentoMapper.toResponseDto(agendamento));
    }

    @PatchMapping("/{id}/recusar")
    public ResponseEntity<Void> recusar(@PathVariable UUID id) {
        service.recusar(id);
        return ResponseEntity.noContent().build();
    }
}

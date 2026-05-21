package com.ucb.Rapidex.controller;

import com.ucb.Rapidex.controller.dto.ChamadoRequestDto;
import com.ucb.Rapidex.controller.dto.ChamadoResponseDto;
import com.ucb.Rapidex.repository.UsuarioRepository;
import com.ucb.Rapidex.service.ChamadoService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/chamados")
public class ChamadoController {

    private final ChamadoService chamadoService;
    private final UsuarioRepository usuarioRepository;

    public ChamadoController(ChamadoService chamadoService, UsuarioRepository usuarioRepository) {
        this.chamadoService = chamadoService;
        this.usuarioRepository = usuarioRepository;
    }

    @PostMapping
    public ResponseEntity<ChamadoResponseDto> criar(@AuthenticationPrincipal UserDetails userDetails,
                                                     @Valid @RequestBody ChamadoRequestDto dto) {
        var usuario = usuarioRepository.findByEmail(userDetails.getUsername())
                .orElseThrow(() -> new UsernameNotFoundException("Usuario nao encontrado"));
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(chamadoService.criar(usuario.getId(), dto));
    }

    @GetMapping("/meus")
    public List<ChamadoResponseDto> meusChamados(@AuthenticationPrincipal UserDetails userDetails) {
        var usuario = usuarioRepository.findByEmail(userDetails.getUsername())
                .orElseThrow(() -> new UsernameNotFoundException("Usuario nao encontrado"));
        return chamadoService.listarPorUsuario(usuario.getId());
    }
}
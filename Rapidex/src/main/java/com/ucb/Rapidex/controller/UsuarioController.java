package com.ucb.Rapidex.controller;

import com.ucb.Rapidex.controller.dto.UsuarioDto;
import com.ucb.Rapidex.controller.dto.UsuarioResponseDto;
import com.ucb.Rapidex.mapper.UsuarioMapper;
import com.ucb.Rapidex.model.Usuario;
import com.ucb.Rapidex.service.UsuarioService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/usuarios")
public class UsuarioController {

    private final UsuarioService service;
    private final UsuarioMapper mapper;

    public UsuarioController(UsuarioService service, UsuarioMapper mapper) {
        this.service = service;
        this.mapper = mapper;
    }

    @PostMapping
    public ResponseEntity<Void> cadastrar(@RequestBody UsuarioDto dto) {
        var usuario = new Usuario();
        usuario.setEmail(dto.email());
        usuario.setSenha_hash(dto.senha());
        usuario.setTipoUsuario(dto.tipoUsuario());
        usuario.setAtivo(true);
        service.salvar(usuario);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @GetMapping("/{id}")
    public ResponseEntity<UsuarioResponseDto> obterPorId(@PathVariable UUID id) {
        return ResponseEntity.ok(mapper.toResponseDto(service.obterPorUsuarioId(id)));
    }
}

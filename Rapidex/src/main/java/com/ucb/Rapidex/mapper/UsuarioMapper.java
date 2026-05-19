package com.ucb.Rapidex.mapper;

import com.ucb.Rapidex.controller.dto.UsuarioResponseDto;
import com.ucb.Rapidex.model.Usuario;
import org.springframework.stereotype.Component;

@Component
public class UsuarioMapper {

    public UsuarioResponseDto toResponseDto(Usuario u) {
        return new UsuarioResponseDto(
                u.getId(),
                u.getEmail(),
                u.getTipoUsuario(),
                u.isAtivo(),
                u.getCriado_em(),
                u.getAtualizado_em());
    }
}

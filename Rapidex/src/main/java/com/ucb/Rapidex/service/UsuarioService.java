package com.ucb.Rapidex.service;

import com.ucb.Rapidex.controller.dto.UsuarioDto;
import com.ucb.Rapidex.model.Usuario;
import com.ucb.Rapidex.repository.UsuarioRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.NoSuchElementException;
import java.util.UUID;

@Service
public class UsuarioService {

    private final UsuarioRepository repository;
    private final PasswordEncoder encoder;


    public UsuarioService(UsuarioRepository repository, PasswordEncoder encoder) {
        this.repository = repository;
        this.encoder = encoder;
    }

    public void salvar(Usuario usuario) {
        var senhaCriptografada = encoder.encode(usuario.getSenha_hash());
        usuario.setSenha_hash(senhaCriptografada);
        repository.save(usuario);
    }

    public Usuario obterPorUsuarioId(UUID uuid) {
        return repository.findById(uuid)
                .orElseThrow(() -> new NoSuchElementException("Usuário não encontrado: " + uuid));
    }
}

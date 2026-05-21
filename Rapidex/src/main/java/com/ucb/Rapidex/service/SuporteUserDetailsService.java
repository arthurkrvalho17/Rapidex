package com.ucb.Rapidex.service;

import com.ucb.Rapidex.repository.SuporteUsuarioRepository;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class SuporteUserDetailsService implements UserDetailsService {

    private final SuporteUsuarioRepository repository;

    public SuporteUserDetailsService(SuporteUsuarioRepository repository) {
        this.repository = repository;
    }

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        var agente = repository.findByEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException("Agente nao encontrado: " + email));
        if (!agente.isAtivo()) {
            throw new UsernameNotFoundException("Agente desativado: " + email);
        }
        return User.builder()
                .username(agente.getEmail())
                .password(agente.getSenhaHash())
                .roles("SUPORTE")
                .build();
    }
}
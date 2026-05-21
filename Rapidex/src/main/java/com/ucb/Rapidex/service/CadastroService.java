package com.ucb.Rapidex.service;

import com.ucb.Rapidex.controller.dto.CadastroRequestDto;
import com.ucb.Rapidex.controller.dto.CadastroResponseDto;
import com.ucb.Rapidex.model.*;
import com.ucb.Rapidex.repository.ContratanteRepository;
import com.ucb.Rapidex.repository.PrestadorRepository;
import com.ucb.Rapidex.repository.UsuarioRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.OffsetDateTime;
import java.util.UUID;

@Service
public class CadastroService {

    private final UsuarioRepository usuarioRepository;
    private final PrestadorRepository prestadorRepository;
    private final ContratanteRepository contratanteRepository;
    private final PasswordEncoder passwordEncoder;

    public CadastroService(UsuarioRepository usuarioRepository,
                           PrestadorRepository prestadorRepository,
                           ContratanteRepository contratanteRepository,
                           PasswordEncoder passwordEncoder) {
        this.usuarioRepository = usuarioRepository;
        this.prestadorRepository = prestadorRepository;
        this.contratanteRepository = contratanteRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Transactional
    public CadastroResponseDto cadastrar(CadastroRequestDto dto) {
        var agora = OffsetDateTime.now();

        var usuario = new Usuario();
        usuario.setEmail(dto.email());
        usuario.setSenha_hash(passwordEncoder.encode(dto.senha()));
        usuario.setTipoUsuario(dto.tipoUsuario());
        usuario.setAtivo(true);
        usuario.setCriado_em(agora);
        usuario.setAtualizado_em(agora);
        usuario = usuarioRepository.save(usuario);

        UUID prestadorId = null;
        UUID contratanteId = null;

        if (dto.tipoUsuario() == TipoUsuario.PRESTADOR || dto.tipoUsuario() == TipoUsuario.AMBOS) {
            prestadorId = criarPrestador(usuario.getId(), dto, agora);
        }

        if (dto.tipoUsuario() == TipoUsuario.CONTRATANTE || dto.tipoUsuario() == TipoUsuario.AMBOS) {
            contratanteId = criarContratante(usuario.getId(), dto, agora);
        }

        return new CadastroResponseDto(usuario.getId(), usuario.getEmail(), usuario.getTipoUsuario(), prestadorId, contratanteId);
    }

    private UUID criarPrestador(UUID usuarioId, CadastroRequestDto dto, OffsetDateTime agora) {
        if (dto.cidade() == null || dto.cidade().isBlank()) {
            throw new IllegalArgumentException("Campo 'cidade' é obrigatório para prestadores.");
        }
        if (dto.uf() == null || dto.uf().isBlank()) {
            throw new IllegalArgumentException("Campo 'uf' é obrigatório para prestadores.");
        }
        if (dto.areasAtuacao() == null || dto.areasAtuacao().isEmpty()) {
            throw new IllegalArgumentException("Informe ao menos uma área de atuação.");
        }

        var prestador = new Prestador();
        prestador.setUsuarioId(usuarioId);
        prestador.setNome(dto.nome());
        prestador.setTelefone(dto.telefone());
        prestador.setFotoUrl(dto.fotoUrl());
        prestador.setCidade(dto.cidade());
        prestador.setUf(dto.uf());
        prestador.setAreasAtuacao(dto.areasAtuacao());
        prestador.setStatus(StatusPrestador.OFFLINE);
        prestador.setAvaliacaoMedia(BigDecimal.ZERO);
        prestador.setTotalAvaliacoes(0);
        prestador.setCriadoEm(agora);
        return prestadorRepository.save(prestador).getId();
    }

    private UUID criarContratante(UUID usuarioId, CadastroRequestDto dto, OffsetDateTime agora) {
        var contratante = new Contratante();
        contratante.setUsuarioId(usuarioId);
        contratante.setNome(dto.nome());
        contratante.setTelefone(dto.telefone());
        contratante.setFotoUrl(dto.fotoUrl());
        contratante.setCriadoEm(agora);
        return contratanteRepository.save(contratante).getId();
    }
}

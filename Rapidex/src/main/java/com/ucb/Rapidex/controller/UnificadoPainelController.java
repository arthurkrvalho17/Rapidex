package com.ucb.Rapidex.controller;

import com.ucb.Rapidex.model.TipoUsuario;
import com.ucb.Rapidex.repository.ContratanteRepository;
import com.ucb.Rapidex.repository.PrestadorRepository;
import com.ucb.Rapidex.repository.UsuarioRepository;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.NoSuchElementException;

@Controller
public class UnificadoPainelController {

    private final UsuarioRepository usuarioRepository;
    private final PrestadorRepository prestadorRepository;
    private final ContratanteRepository contratanteRepository;

    public UnificadoPainelController(UsuarioRepository usuarioRepository,
                                     PrestadorRepository prestadorRepository,
                                     ContratanteRepository contratanteRepository) {
        this.usuarioRepository = usuarioRepository;
        this.prestadorRepository = prestadorRepository;
        this.contratanteRepository = contratanteRepository;
    }

    @GetMapping("/conta")
    public String conta(Model model, @AuthenticationPrincipal UserDetails userDetails) {
        var usuario = usuarioRepository.findByEmail(userDetails.getUsername())
                .orElseThrow(() -> new NoSuchElementException("Usuário não encontrado"));

        var tipoUsuario = usuario.getTipoUsuario();
        String nomeUsuario = usuario.getEmail();

        if (tipoUsuario == TipoUsuario.PRESTADOR || tipoUsuario == TipoUsuario.AMBOS) {
            var prestador = prestadorRepository.findByUsuarioId(usuario.getId());
            if (prestador.isPresent()) {
                nomeUsuario = prestador.get().getNome();
            }
        } else if (tipoUsuario == TipoUsuario.CONTRATANTE) {
            var contratante = contratanteRepository.findByUsuarioId(usuario.getId());
            if (contratante.isPresent()) {
                nomeUsuario = contratante.get().getNome();
            }
        }

        model.addAttribute("tipoUsuario", tipoUsuario);
        model.addAttribute("nomeUsuario", nomeUsuario);

        return "rapidex-unificado";
    }
}

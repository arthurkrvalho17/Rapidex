package com.ucb.Rapidex.controller;

import com.ucb.Rapidex.model.StatusChamado;
import com.ucb.Rapidex.repository.SuporteUsuarioRepository;
import com.ucb.Rapidex.service.ChamadoService;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/suporte")
public class SuportePainelController {

    private final ChamadoService chamadoService;
    private final SuporteUsuarioRepository suporteUsuarioRepository;

    public SuportePainelController(ChamadoService chamadoService,
                                   SuporteUsuarioRepository suporteUsuarioRepository) {
        this.chamadoService = chamadoService;
        this.suporteUsuarioRepository = suporteUsuarioRepository;
    }

    @GetMapping("/login")
    public String loginPage() {
        return "loginSuporte";
    }

    @GetMapping("/painel")
    public String painel(Model model, @AuthenticationPrincipal UserDetails userDetails) {
        var agente = suporteUsuarioRepository.findByEmail(userDetails.getUsername())
                .orElseThrow();
        model.addAttribute("nomeAgente", agente.getNome());
        model.addAttribute("emailAgente", agente.getEmail());
        model.addAttribute("stats", chamadoService.calcularStats());
        model.addAttribute("chamadosAberto", chamadoService.listarPorStatus(StatusChamado.ABERTO));
        model.addAttribute("chamadosEmAtendimento", chamadoService.listarPorStatus(StatusChamado.EM_ATENDIMENTO));
        model.addAttribute("chamadosRevisao", chamadoService.listarPorStatus(StatusChamado.AGUARDANDO_USUARIO));
        model.addAttribute("chamadosResolvidos", chamadoService.listarResolvidos());
        return "rapidex-suporte-kanban";
    }
}
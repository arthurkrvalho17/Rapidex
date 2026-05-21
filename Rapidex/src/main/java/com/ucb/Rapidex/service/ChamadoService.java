package com.ucb.Rapidex.service;

import com.ucb.Rapidex.controller.dto.*;
import com.ucb.Rapidex.model.*;
import com.ucb.Rapidex.repository.*;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.OffsetDateTime;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.UUID;

@Service
public class ChamadoService {

    private final ChamadoRepository chamadoRepository;
    private final UsuarioRepository usuarioRepository;
    private final PrestadorRepository prestadorRepository;
    private final ContratanteRepository contratanteRepository;
    private final SuporteUsuarioRepository suporteUsuarioRepository;

    public ChamadoService(ChamadoRepository chamadoRepository,
                          UsuarioRepository usuarioRepository,
                          PrestadorRepository prestadorRepository,
                          ContratanteRepository contratanteRepository,
                          SuporteUsuarioRepository suporteUsuarioRepository) {
        this.chamadoRepository = chamadoRepository;
        this.usuarioRepository = usuarioRepository;
        this.prestadorRepository = prestadorRepository;
        this.contratanteRepository = contratanteRepository;
        this.suporteUsuarioRepository = suporteUsuarioRepository;
    }

    @Transactional
    public ChamadoResponseDto criar(UUID usuarioId, ChamadoRequestDto dto) {
        var now = OffsetDateTime.now();
        var chamado = new Chamado();
        chamado.setUsuarioId(usuarioId);
        chamado.setTitulo(dto.titulo());
        chamado.setDescricao(dto.descricao());
        chamado.setTipoEvento(dto.tipoEvento());
        chamado.setPrioridade(calcularPrioridade(dto.tipoEvento()));
        chamado.setStatus(StatusChamado.ABERTO);
        chamado.setCriadoEm(now);
        chamado.setAtualizadoEm(now);
        return toResponseDto(chamadoRepository.save(chamado));
    }

    @Transactional
    public ChamadoResponseDto atualizarStatus(UUID id, StatusChamado novoStatus) {
        var chamado = chamadoRepository.findById(id)
                .orElseThrow(() -> new NoSuchElementException("Chamado nao encontrado: " + id));
        chamado.setStatus(novoStatus);
        if (novoStatus == StatusChamado.RESOLVIDO || novoStatus == StatusChamado.FECHADO) {
            chamado.setResolvidoEm(OffsetDateTime.now());
        }
        chamado.setAtualizadoEm(OffsetDateTime.now());
        return toResponseDto(chamadoRepository.save(chamado));
    }

    public ChamadoResponseDto buscarPorId(UUID id) {
        return chamadoRepository.findById(id)
                .map(this::toResponseDto)
                .orElseThrow(() -> new NoSuchElementException("Chamado nao encontrado: " + id));
    }

    public ChamadoStatsDto calcularStats() {
        long total = chamadoRepository.count();
        long aguardando = chamadoRepository.countByStatus(StatusChamado.ABERTO);
        long resolvidosHoje = chamadoRepository.countByStatusAndResolvidoEmAfter(
                StatusChamado.RESOLVIDO, OffsetDateTime.now().truncatedTo(ChronoUnit.DAYS));
        long altaPrioridade = chamadoRepository.countByPrioridade(PrioridadeChamado.ALTA)
                + chamadoRepository.countByPrioridade(PrioridadeChamado.URGENTE);
        return new ChamadoStatsDto(total, aguardando, resolvidosHoje, altaPrioridade);
    }

    public List<ChamadoViewDto> listarPorStatus(StatusChamado status) {
        return chamadoRepository.findByStatusOrderByCriadoEmDesc(status)
                .stream().map(this::toViewDto).toList();
    }

    public List<ChamadoViewDto> listarResolvidos() {
        return chamadoRepository.findByStatusInOrderByCriadoEmDesc(
                List.of(StatusChamado.RESOLVIDO, StatusChamado.FECHADO))
                .stream().map(this::toViewDto).toList();
    }

    public List<ChamadoResponseDto> listarPorUsuario(UUID usuarioId) {
        return chamadoRepository.findByUsuarioIdOrderByCriadoEmDesc(usuarioId)
                .stream().map(this::toResponseDto).toList();
    }

    private PrioridadeChamado calcularPrioridade(TipoEvento tipo) {
        return switch (tipo) {
            case FRAUDE -> PrioridadeChamado.URGENTE;
            case PAGAMENTO, CANCELAMENTO, SERVICO_NAO_REALIZADO -> PrioridadeChamado.ALTA;
            case REAGENDAMENTO, RECLAMACAO -> PrioridadeChamado.MEDIA;
            case DUVIDA, SUGESTAO -> PrioridadeChamado.BAIXA;
        };
    }

    private ChamadoViewDto toViewDto(Chamado c) {
        String nomeUsuario = resolverNomeUsuario(c.getUsuarioId());
        String nomeAgente = c.getAtribuidoA() != null
                ? suporteUsuarioRepository.findById(c.getAtribuidoA())
                        .map(SuporteUsuario::getNome).orElse("-")
                : "-";
        String idDisplay = "#" + c.getId().toString().replace("-", "").substring(0, 4).toUpperCase();
        String descResumida = c.getDescricao().length() > 90
                ? c.getDescricao().substring(0, 90) + "..."
                : c.getDescricao();
        return new ChamadoViewDto(
                c.getId(), idDisplay, c.getTitulo(), descResumida,
                c.getTipoEvento(), c.getPrioridade(), c.getStatus(),
                nomeUsuario, resolverIniciais(nomeUsuario), nomeAgente, c.getCriadoEm());
    }

    private String resolverNomeUsuario(UUID usuarioId) {
        return prestadorRepository.findByUsuarioId(usuarioId)
                .map(Prestador::getNome)
                .or(() -> contratanteRepository.findByUsuarioId(usuarioId).map(Contratante::getNome))
                .orElseGet(() -> usuarioRepository.findById(usuarioId)
                        .map(Usuario::getEmail).orElse("Usuario"));
    }

    private String resolverIniciais(String nome) {
        if (nome == null || nome.isBlank()) return "?";
        String[] partes = nome.trim().split("\\s+");
        if (partes.length == 1) return partes[0].substring(0, Math.min(2, partes[0].length())).toUpperCase();
        return (String.valueOf(partes[0].charAt(0)) + String.valueOf(partes[1].charAt(0))).toUpperCase();
    }

    private ChamadoResponseDto toResponseDto(Chamado c) {
        return new ChamadoResponseDto(
                c.getId(), c.getUsuarioId(), c.getTitulo(), c.getDescricao(),
                c.getTipoEvento(), c.getPrioridade(), c.getStatus(),
                c.getAtribuidoA(), c.getCriadoEm(), c.getAtualizadoEm(), c.getResolvidoEm());
    }
}
package com.ucb.Rapidex.controller;

import com.ucb.Rapidex.controller.dto.AgendamentoViewDto;
import com.ucb.Rapidex.controller.dto.PedidoViewDto;
import com.ucb.Rapidex.model.*;
import com.ucb.Rapidex.repository.*;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

@Controller
public class PrestadorPainelController {

    private final UsuarioRepository usuarioRepository;
    private final PrestadorRepository prestadorRepository;
    private final PedidoRepository pedidoRepository;
    private final ContratanteRepository contratanteRepository;
    private final ServicoOferecidoRepository servicoRepository;
    private final AgendamentoRepository agendamentoRepository;

    public PrestadorPainelController(UsuarioRepository usuarioRepository,
                                     PrestadorRepository prestadorRepository,
                                     PedidoRepository pedidoRepository,
                                     ContratanteRepository contratanteRepository,
                                     ServicoOferecidoRepository servicoRepository,
                                     AgendamentoRepository agendamentoRepository) {
        this.usuarioRepository = usuarioRepository;
        this.prestadorRepository = prestadorRepository;
        this.pedidoRepository = pedidoRepository;
        this.contratanteRepository = contratanteRepository;
        this.servicoRepository = servicoRepository;
        this.agendamentoRepository = agendamentoRepository;
    }

    @GetMapping("/painel")
    public String painel(Model model, @AuthenticationPrincipal UserDetails userDetails) {
        var usuario = usuarioRepository.findByEmail(userDetails.getUsername())
                .orElseThrow(() -> new NoSuchElementException("Usuário não encontrado"));

        var prestador = prestadorRepository.findByUsuarioId(usuario.getId())
                .orElseThrow(() -> new NoSuchElementException("Perfil de prestador não encontrado"));

        var todosPedidos = pedidoRepository.findByPrestadorId(prestador.getId());

        var contratantesMap = contratanteRepository
                .findAllById(todosPedidos.stream().map(Pedido::getContratanteId).distinct().toList())
                .stream().collect(Collectors.toMap(Contratante::getId, Contratante::getNome));

        var servicosMap = servicoRepository
                .findAllById(todosPedidos.stream()
                        .map(Pedido::getServicoId).filter(Objects::nonNull).distinct().toList())
                .stream().collect(Collectors.toMap(ServicoOferecido::getId, ServicoOferecido::getNome));

        Function<Pedido, PedidoViewDto> toPedidoDto = p -> new PedidoViewDto(
                p.getId().toString(),
                contratantesMap.getOrDefault(p.getContratanteId(), "Desconhecido"),
                servicosMap.getOrDefault(p.getServicoId(), "Serviço não especificado"),
                p.getDataSugerida() != null ? p.getDataSugerida().format(DateTimeFormatter.ofPattern("dd/MM/yyyy")) : null,
                p.getHoraSugerida() != null ? p.getHoraSugerida().format(DateTimeFormatter.ofPattern("HH:mm")) : null,
                p.getStatus().name().toLowerCase()
        );

        var pedidosPendentes = todosPedidos.stream()
                .filter(p -> p.getStatus() == StatusPedido.PENDING)
                .map(toPedidoDto).toList();

        var pedidosResolvidos = todosPedidos.stream()
                .filter(p -> p.getStatus() != StatusPedido.PENDING)
                .map(toPedidoDto).toList();

        var pedidosMap = todosPedidos.stream()
                .collect(Collectors.toMap(Pedido::getId, p -> p));

        var agendamentos = agendamentoRepository
                .findByPedidoIdIn(todosPedidos.stream().map(Pedido::getId).toList())
                .stream()
                .sorted(Comparator.comparing(Agendamento::getData).thenComparing(Agendamento::getHoraInicio))
                .map(a -> {
                    var pedido = pedidosMap.get(a.getPedidoId());
                    var clienteNome = pedido != null ? contratantesMap.getOrDefault(pedido.getContratanteId(), "Desconhecido") : "Desconhecido";
                    var servicoNome = pedido != null && pedido.getServicoId() != null ? servicosMap.getOrDefault(pedido.getServicoId(), "Serviço") : "Serviço";
                    return new AgendamentoViewDto(
                            a.getId().toString(),
                            a.getPedidoId().toString(),
                            clienteNome,
                            servicoNome,
                            a.getData().toString(),
                            a.getData().format(DateTimeFormatter.ofPattern("dd/MM/yyyy")),
                            a.getHoraInicio().format(DateTimeFormatter.ofPattern("HH:mm")),
                            a.getStatus().name().toLowerCase()
                    );
                }).toList();

        var hoje = LocalDate.now();
        var fmtDia = DateTimeFormatter.ofPattern("EEEE, dd 'de' MMMM", new Locale("pt", "BR"));
        var compromissosPorData = agendamentos.stream()
                .filter(a -> !LocalDate.parse(a.data()).isBefore(hoje))
                .limit(10)
                .collect(Collectors.groupingBy(
                        a -> {
                            var d = LocalDate.parse(a.data()).format(fmtDia);
                            return d.substring(0, 1).toUpperCase() + d.substring(1);
                        },
                        LinkedHashMap::new,
                        Collectors.toList()
                ));

        model.addAttribute("prestador", prestador);
        model.addAttribute("pedidosPendentes", pedidosPendentes);
        model.addAttribute("pedidosResolvidos", pedidosResolvidos);
        model.addAttribute("agendamentos", agendamentos);
        model.addAttribute("compromissosPorData", compromissosPorData);

        return "rapidex-prestador";
    }
}

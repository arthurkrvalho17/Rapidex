package com.ucb.Rapidex.controller.dto;

import com.ucb.Rapidex.model.AreaAtuacao;
import com.ucb.Rapidex.model.TipoUsuario;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

import java.util.Set;

public record CadastroRequestDto(

        @NotBlank(message = "E-mail é obrigatório")
        @Email(message = "E-mail inválido")
        String email,

        @NotBlank(message = "Senha é obrigatória")
        @Size(min = 6, message = "Senha deve ter ao menos 6 caracteres")
        String senha,

        @NotNull(message = "Tipo de usuário é obrigatório")
        TipoUsuario tipoUsuario,

        @NotBlank(message = "Nome é obrigatório")
        @Size(max = 150, message = "Nome deve ter no máximo 150 caracteres")
        String nome,

        @Size(max = 20, message = "Telefone deve ter no máximo 20 caracteres")
        String telefone,

        String fotoUrl,

        @Size(max = 50, message = "Cidade deve ter no máximo 50 caracteres")
        String cidade,

        @Pattern(
            regexp = "^(AC|AL|AP|AM|BA|CE|DF|ES|GO|MA|MT|MS|MG|PA|PB|PR|PE|PI|RJ|RN|RS|RO|RR|SC|SP|SE|TO)$",
            message = "UF inválida"
        )
        String uf,

        Set<AreaAtuacao> areasAtuacao) {
}

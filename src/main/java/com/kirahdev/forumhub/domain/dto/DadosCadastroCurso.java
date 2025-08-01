package com.kirahdev.forumhub.domain.dto;

import jakarta.validation.constraints.NotBlank;

public record DadosCadastroCurso(
        @NotBlank
        String nome,

        @NotBlank
        String categoria
) {
}
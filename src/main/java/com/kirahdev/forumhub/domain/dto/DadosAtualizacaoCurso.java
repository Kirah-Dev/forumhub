package com.kirahdev.forumhub.domain.dto;

// DTO com os campos opcionais para a atualização de um curso.
public record DadosAtualizacaoCurso(
        String nome,
        String categoria
) {
}
package com.kirahdev.forumhub.domain.dto;

import com.kirahdev.forumhub.domain.entity.Curso;

public record DadosDetalhamentoCurso(Long id, String nome, String categoria) {
    public DadosDetalhamentoCurso(Curso curso) {
        this(curso.getId(), curso.getNome(), curso.getCategoria());
    }
}
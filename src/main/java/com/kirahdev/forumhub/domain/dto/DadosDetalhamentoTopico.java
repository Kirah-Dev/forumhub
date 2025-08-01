package com.kirahdev.forumhub.domain.dto;

import com.kirahdev.forumhub.domain.entity.StatusTopico;
import com.kirahdev.forumhub.domain.entity.Topico;

import java.time.LocalDateTime;

public record DadosDetalhamentoTopico(
        Long id,
        String titulo,
        String mensagem,
        LocalDateTime dataCriacao,
        StatusTopico status,
        String autor,
        String curso
) {
    // Este construtor extra é um "mapeador": ele sabe como converter
    // uma entidade completa 'Topico' para este DTO mais simples.
    public DadosDetalhamentoTopico(Topico topico) {
        this(
                topico.getId(),
                topico.getTitulo(),
                topico.getMensagem(),
                topico.getDataCriacao(),
                topico.getStatus(),
                topico.getAutor().getNome(), // Aqui pegamos apenas o nome do autor
                topico.getCurso().getNome()  // E aqui apenas o nome do curso
        );
    }
}
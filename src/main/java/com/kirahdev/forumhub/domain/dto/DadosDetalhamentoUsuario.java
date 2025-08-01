package com.kirahdev.forumhub.domain.dto;

import com.kirahdev.forumhub.domain.entity.Usuario;

public record DadosDetalhamentoUsuario(Long id, String nome, String email) {
    public DadosDetalhamentoUsuario(Usuario usuario) {
        this(usuario.getId(), usuario.getNome(), usuario.getEmail());
    }
}

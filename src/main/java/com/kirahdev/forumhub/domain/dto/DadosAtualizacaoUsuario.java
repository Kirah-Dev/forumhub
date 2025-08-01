package com.kirahdev.forumhub.domain.dto;

// Campos opcionais para a atualização de um usuário.
// Poderíamos adicionar validações como @Email ou @Size se necessário.
public record DadosAtualizacaoUsuario(
        String nome,
        String senha
) {
}
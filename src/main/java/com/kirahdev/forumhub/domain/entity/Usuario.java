package com.kirahdev.forumhub.domain.entity;

import com.kirahdev.forumhub.domain.dto.DadosAtualizacaoUsuario;
import com.kirahdev.forumhub.domain.dto.DadosCadastroUsuario;
import org.springframework.security.crypto.password.PasswordEncoder;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Where;

@Table(name = "usuarios")
@Entity
@Getter
@NoArgsConstructor
//@AllArgsConstructor
@EqualsAndHashCode(of = "id")
@Where(clause = "ativo = true")
public class Usuario {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String nome;
    private String email;
    private String senha;

    private boolean ativo;

    public Usuario(DadosCadastroUsuario dados, String senhaCriptografada) {
        this.ativo = true;
        this.nome = dados.nome();
        this.email = dados.email();
        this.senha = senhaCriptografada;
    }

    public void atualizarInformacoes(DadosAtualizacaoUsuario dados, PasswordEncoder passwordEncoder) {
        if (dados.nome() != null && !dados.nome().isBlank()) {
            this.nome = dados.nome();
        }

        // Se uma nova senha foi fornecida, ela DEVE ser criptografada.
        if (dados.senha() != null && !dados.senha().isBlank()) {
            this.senha = passwordEncoder.encode(dados.senha());
        }
    }

    public void excluir() {
        this.ativo = false;
    }

}
package com.kirahdev.forumhub.domain.entity;

import com.kirahdev.forumhub.domain.dto.DadosAtualizacaoUsuario;
import com.kirahdev.forumhub.domain.dto.DadosCadastroUsuario;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Where;

import java.util.Collection;
import java.util.List;

@Table(name = "usuarios")
@Entity
@Getter
@NoArgsConstructor
//@AllArgsConstructor
@EqualsAndHashCode(of = "id")
@Where(clause = "ativo = true")
public class Usuario implements UserDetails {

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

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        // Por enquanto, todo usuário tem a role "ROLE_USER".
        // Mais tarde, poderíamos carregar isso da tabela de perfis.
        return List.of(new SimpleGrantedAuthority("ROLE_USER"));
    }

    @Override
    public String getPassword() {
        return this.senha; // Retorna a senha criptografada
    }

    @Override
    public String getUsername() {
        return this.email; // O "username" do nosso sistema é o e-mail
    }

    @Override
    public boolean isAccountNonExpired() {
        return true; // A conta nunca expira
    }

    @Override
    public boolean isAccountNonLocked() {
        return true; // A conta nunca é bloqueada
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true; // As credenciais nunca expiram
    }

    @Override
    public boolean isEnabled() {
        return this.ativo; // O usuário está habilitado se estiver "ativo"
    }

}
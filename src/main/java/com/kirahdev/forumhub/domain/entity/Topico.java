package com.kirahdev.forumhub.domain.entity;

import com.kirahdev.forumhub.domain.dto.DadosAtualizacaoTopico;
import com.kirahdev.forumhub.domain.dto.DadosCadastroTopico;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Where;
import java.time.LocalDateTime;

@Table(name = "topicos")
@Entity
@Getter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(of = "id")
@Where(clause = "ativo = true")
public class Topico {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String titulo;
    private String mensagem;

    @Column(name = "data_criacao")
    private LocalDateTime dataCriacao;

    @Enumerated(EnumType.STRING)
    private StatusTopico status;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "autor_id")
    private Usuario autor;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "curso_id")
    private Curso curso;

    private boolean ativo;

    // Construtor para criar um Tópico a partir do DTO
    public Topico(DadosCadastroTopico dados, Usuario autor, Curso curso) {
        this.ativo = true;
        this.titulo = dados.titulo();
        this.mensagem = dados.mensagem();
        this.autor = autor;
        this.curso = curso;
        this.dataCriacao = LocalDateTime.now();
        this.status = StatusTopico.NAO_RESPONDIDO; // Estado inicial padrão
    }

    public void atualizarInformacoes(DadosAtualizacaoTopico dados) {
        // Verifica se o novo título não é nulo nem está em branco antes de atualizar
        if (dados.titulo() != null && !dados.titulo().isBlank()) {
            this.titulo = dados.titulo();
        }

        // Verifica se a nova mensagem não é nula nem está em branco antes de atualizar
        if (dados.mensagem() != null && !dados.mensagem().isBlank()) {
            this.mensagem = dados.mensagem();
        }
    }

    public void excluir() {
        this.ativo = false;
    }
}
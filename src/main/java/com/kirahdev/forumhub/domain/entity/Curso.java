package com.kirahdev.forumhub.domain.entity;

import com.kirahdev.forumhub.domain.dto.DadosCadastroCurso;
import com.kirahdev.forumhub.domain.dto.DadosAtualizacaoCurso;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Where;

@Table(name = "cursos")
@Entity
@Getter
@NoArgsConstructor
//@AllArgsConstructor
@EqualsAndHashCode(of = "id")
@Where(clause = "ativo = true")
public class Curso {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String nome;
    private String categoria;

    private boolean ativo;

    // Construtor para ser usado no serviço de cadastro
    public Curso(DadosCadastroCurso dados) {
        this.ativo = true;
        this.nome = dados.nome();
        this.categoria = dados.categoria();
    }

    public void atualizarInformacoes(DadosAtualizacaoCurso dados) {
        if (dados.nome() != null && !dados.nome().isBlank()) {
            this.nome = dados.nome();
        }

        if (dados.categoria() != null && !dados.categoria().isBlank()) {
            this.categoria = dados.categoria();
        }
    }

        // Metodo para a exclusão lógica
        public void excluir() {
            this.ativo = false;
        }

}
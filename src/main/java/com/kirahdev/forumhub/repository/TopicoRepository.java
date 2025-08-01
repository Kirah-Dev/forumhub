package com.kirahdev.forumhub.repository;

import com.kirahdev.forumhub.domain.entity.Topico;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface TopicoRepository extends JpaRepository<Topico, Long> {

    // Metodo para checar se já existe um tópico com o mesmo título e mensagem
    // O Spring Data JPA entende o nome do metodo e cria a query:
    // "SELECT CASE WHEN COUNT(t) > 0 THEN TRUE ELSE FALSE END FROM Topico t WHERE t.titulo = ?1 AND t.mensagem = ?2"
    boolean existsByTituloAndMensagem(String titulo, String mensagem);
    Page<Topico> findByCursoNome(String nomeCurso, Pageable paginacao);

    // Retorna um tópico se encontrar um com o mesmo título e mensagem.
    Optional<Topico> findByTituloAndMensagem(String titulo, String mensagem);
}
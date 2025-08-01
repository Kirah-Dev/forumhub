package com.kirahdev.forumhub.service;

import com.kirahdev.forumhub.domain.dto.DadosAtualizacaoCurso;
import com.kirahdev.forumhub.domain.dto.DadosCadastroCurso;
import com.kirahdev.forumhub.domain.dto.DadosDetalhamentoCurso;
import com.kirahdev.forumhub.domain.entity.Curso;
import com.kirahdev.forumhub.repository.CursoRepository;
import jakarta.persistence.EntityNotFoundException;
import jakarta.validation.ValidationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import jakarta.transaction.Transactional;

import java.util.List;

@Service
public class CursoService {

    @Autowired
    private CursoRepository cursoRepository;

    public Curso cadastrarCurso(DadosCadastroCurso dados) {
        if (cursoRepository.findByNome(dados.nome()).isPresent()) {
            throw new ValidationException("Já existe um curso com este nome.");
        }

        var curso = new Curso(dados);
        cursoRepository.save(curso);
        return curso;
    }

    @Transactional
    public DadosDetalhamentoCurso atualizarCurso(Long id, DadosAtualizacaoCurso dados) {
        // Validação de duplicidade: não podemos alterar o nome de um curso
        // para um nome que já é usado por OUTRO curso.
        if (dados.nome() != null) {
            var cursoComMesmoNome = cursoRepository.findByNome(dados.nome());
            if (cursoComMesmoNome.isPresent() && !cursoComMesmoNome.get().getId().equals(id)) {
                throw new ValidationException("Já existe um curso com o nome '" + dados.nome() + "'.");
            }
        }

        // Busca o curso ou lança 404 se não existir.
        var curso = cursoRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Curso com id " + id + " não encontrado!"));

        // Aplica as atualizações.
        curso.atualizarInformacoes(dados);

        // Retorna os dados atualizados.
        return new DadosDetalhamentoCurso(curso);
    }

    // Metodo para LISTAR TODOS os cursos
    public List<DadosDetalhamentoCurso> listarTodos() {
        // 1. Busca todos os cursos no repositório.
        var cursos = cursoRepository.findAll();
        // 2. Converte a lista de entidades para uma lista de DTOs e a retorna.
        return cursos.stream()
                .map(DadosDetalhamentoCurso::new)
                .toList();
    }

    // Metodo para DETALHAR UM curso por ID
    public DadosDetalhamentoCurso detalharPorId(Long id) {
        // Busca o curso ou lança uma exceção 404 se não for encontrado.
        var curso = cursoRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Curso com id " + id + " não encontrado!"));

        // Converte a entidade para o DTO de resposta.
        return new DadosDetalhamentoCurso(curso);
    }

    @Transactional
    //metodo de exclusão lógica
    public void excluirCurso(Long id) {
        var curso = cursoRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Curso com id " + id + " não encontrado!"));

        curso.excluir();
    }
}
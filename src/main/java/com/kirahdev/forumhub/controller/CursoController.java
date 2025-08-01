package com.kirahdev.forumhub.controller;

import com.kirahdev.forumhub.domain.dto.DadosCadastroCurso;
import com.kirahdev.forumhub.domain.dto.DadosDetalhamentoCurso;
import com.kirahdev.forumhub.service.CursoService;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;
import com.kirahdev.forumhub.domain.dto.DadosAtualizacaoCurso;

import java.util.List;

@RestController
@RequestMapping("/cursos")
public class CursoController {

    @Autowired
    private CursoService cursoService;

    @PostMapping
    @Transactional
    public ResponseEntity<DadosDetalhamentoCurso> cadastrar(@RequestBody @Valid DadosCadastroCurso dados, UriComponentsBuilder uriBuilder) {
        var curso = cursoService.cadastrarCurso(dados);
        var uri = uriBuilder.path("/cursos/{id}").buildAndExpand(curso.getId()).toUri();
        return ResponseEntity.created(uri).body(new DadosDetalhamentoCurso(curso));
    }

    // Endpoint para LISTAR TODOS os cursos
    @GetMapping
    public ResponseEntity<List<DadosDetalhamentoCurso>> listar() {
        var listaDeCursos = cursoService.listarTodos();
        return ResponseEntity.ok(listaDeCursos);
    }

    // Endpoint para DETALHAR UM curso por ID
    @GetMapping("/{id}")
    public ResponseEntity<DadosDetalhamentoCurso> detalhar(@PathVariable Long id) {
        var cursoDTO = cursoService.detalharPorId(id);
        return ResponseEntity.ok(cursoDTO);
    }

    @PutMapping("/{id}")
    @Transactional
    public ResponseEntity<DadosDetalhamentoCurso> atualizar(@PathVariable Long id, @RequestBody DadosAtualizacaoCurso dados) {
        var cursoAtualizadoDTO = cursoService.atualizarCurso(id, dados);
        return ResponseEntity.ok(cursoAtualizadoDTO);
    }

    @DeleteMapping("/{id}")
    @Transactional
    public ResponseEntity excluirCurso(@PathVariable Long id) {
        cursoService.excluirCurso(id);
        return ResponseEntity.ok("Curso com id " + id + " excluído com sucesso!");
    }
}
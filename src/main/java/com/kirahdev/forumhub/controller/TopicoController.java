package com.kirahdev.forumhub.controller;

import com.kirahdev.forumhub.domain.dto.DadosAtualizacaoTopico;
import com.kirahdev.forumhub.domain.dto.DadosCadastroTopico;
import com.kirahdev.forumhub.domain.dto.DadosDetalhamentoTopico;
import com.kirahdev.forumhub.service.TopicoService;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.data.web.SpringDataWebProperties;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.List;

@RestController
@RequestMapping("/topicos")
public class TopicoController {

    @Autowired
    private TopicoService topicoService; // Agora injetamos o serviço, não os repositórios

    @PostMapping
    @Transactional
    public ResponseEntity cadastrar(@RequestBody @Valid DadosCadastroTopico dados, UriComponentsBuilder uriBuilder) {
        // A lógica de negócio foi delegada para o Service
        var topico = topicoService.cadastrarTopico(dados);

        // A responsabilidade do controller é montar a resposta HTTP
        var uri = uriBuilder.path("/topicos/{id}").buildAndExpand(topico.getId()).toUri();
        return ResponseEntity.created(uri).body(new DadosDetalhamentoTopico(topico));
    }

    @GetMapping
    public ResponseEntity<Page<DadosDetalhamentoTopico>> listar(
            @RequestParam(required = false) String nomeCurso, // Parâmetro opcional
            @PageableDefault(size = 10, sort = {"dataCriacao"}, direction = Sort.Direction.ASC) Pageable paginacao) {

        // Passa os parâmetros para a camada de serviço
        var page = topicoService.listarTodos(nomeCurso, paginacao);
        return ResponseEntity.ok(page);
    }

    @GetMapping("/{id}")
    public ResponseEntity<DadosDetalhamentoTopico> detalhar(@PathVariable Long id) {
        // 1. A responsabilidade é delegada para a camada de serviço
        var topicoDTO = topicoService.detalharPorId(id);

        // 2. Retorna um status 200 OK com os dados do tópico no corpo da resposta
        return ResponseEntity.ok(topicoDTO);
    }

    @PutMapping("/{id}")
    @Transactional // Necessário para que o JPA gerencie a transação
    public ResponseEntity<DadosDetalhamentoTopico> atualizar(@PathVariable Long id, @RequestBody DadosAtualizacaoTopico dados) {
        var topicoAtualizadoDTO = topicoService.atualizarTopico(id, dados);
        return ResponseEntity.ok(topicoAtualizadoDTO);
    }

    @DeleteMapping("/{id}")
    @Transactional
    public ResponseEntity excluir(@PathVariable Long id) {
        topicoService.excluirTopico(id);
        return ResponseEntity.ok("Tópico com id " + id + " excluído com sucesso!");
    }
}
package com.kirahdev.forumhub.controller;

import com.kirahdev.forumhub.domain.dto.DadosAtualizacaoUsuario;
import com.kirahdev.forumhub.domain.dto.DadosCadastroUsuario;
import com.kirahdev.forumhub.domain.dto.DadosDetalhamentoUsuario;
import com.kirahdev.forumhub.service.UsuarioService;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.List;

@RestController
@RequestMapping("/usuarios")
public class UsuarioController {

    @Autowired
    private UsuarioService usuarioService;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @PostMapping
    @Transactional
    public ResponseEntity<DadosDetalhamentoUsuario> cadastrar(@RequestBody @Valid DadosCadastroUsuario dados, UriComponentsBuilder uriBuilder) {
        var usuario = usuarioService.cadastrarUsuario(dados);
        var uri = uriBuilder.path("/usuarios/{id}").buildAndExpand(usuario.getId()).toUri();
        return ResponseEntity.created(uri).body(new DadosDetalhamentoUsuario(usuario));
    }

    // Endpoint para LISTAR TODOS os usuários
    @GetMapping
    public ResponseEntity<List<DadosDetalhamentoUsuario>> listar() {
        var listaDeUsuarios = usuarioService.listarTodos();
        return ResponseEntity.ok(listaDeUsuarios);
    }

    // Endpoint para DETALHAR UM usuário por ID
    @GetMapping("/{id}")
    public ResponseEntity<DadosDetalhamentoUsuario> detalhar(@PathVariable Long id) {
        var usuarioDTO = usuarioService.detalharPorId(id);
        return ResponseEntity.ok(usuarioDTO);
    }

    @PutMapping("/{id}")
    @Transactional
    public ResponseEntity<DadosDetalhamentoUsuario> atualizar(@PathVariable Long id, @RequestBody DadosAtualizacaoUsuario dados) {
        var usuarioAtualizadoDTO = usuarioService.atualizarUsuario(id, dados);
        return ResponseEntity.ok(usuarioAtualizadoDTO);
    }

    @DeleteMapping("/{id}")
    @Transactional
    public ResponseEntity excluirUsuario(@PathVariable Long id) {
        usuarioService.excluirUsuario(id);
        return ResponseEntity.ok("Usuário com id " + id + " excluído com sucesso!");
    }


}
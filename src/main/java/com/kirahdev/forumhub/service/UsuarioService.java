package com.kirahdev.forumhub.service;

import com.kirahdev.forumhub.domain.dto.DadosCadastroUsuario;
import com.kirahdev.forumhub.domain.dto.DadosAtualizacaoUsuario;
import com.kirahdev.forumhub.domain.dto.DadosDetalhamentoUsuario;
import com.kirahdev.forumhub.domain.entity.Usuario;
import com.kirahdev.forumhub.repository.UsuarioRepository;
import jakarta.persistence.EntityNotFoundException;
import jakarta.validation.ValidationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;

@Service
public class UsuarioService {

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private PasswordEncoder passwordEncoder; // Injeta o codificador de senhas

    public Usuario cadastrarUsuario(DadosCadastroUsuario dados) {
        if (usuarioRepository.findByEmail(dados.email()).isPresent()) {
            throw new ValidationException("Já existe um usuário com este e-mail.");
        }

        // Criptografa a senha antes de salvar
        var senhaCriptografada = passwordEncoder.encode(dados.senha());

        var usuario = new Usuario(dados, senhaCriptografada);
        usuarioRepository.save(usuario);
        return usuario;
    }

    // Metodo para LISTAR TODOS os usuários
    public List<DadosDetalhamentoUsuario> listarTodos() {
        // 1. Busca todos os usuários no repositório.
        var usuarios = usuarioRepository.findAll();
        // 2. Converte a lista de entidades para uma lista de DTOs (que omitem a senha).
        return usuarios.stream()
                .map(DadosDetalhamentoUsuario::new)
                .toList();
    }

    // Metodo para DETALHAR UM usuário por ID
    public DadosDetalhamentoUsuario detalharPorId(Long id) {
        // Busca o usuário ou lança uma exceção 404 se não for encontrado.
        var usuario = usuarioRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Usuário com id " + id + " não encontrado!"));

        // Converte a entidade para o DTO de resposta.
        return new DadosDetalhamentoUsuario(usuario);
    }

    @Transactional
    public DadosDetalhamentoUsuario atualizarUsuario(Long id, DadosAtualizacaoUsuario dados) {
        // Busca o usuário ou lança uma exceção 404 se não for encontrado.
        var usuario = usuarioRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Usuário com id " + id + " não encontrado!"));

        // Chama o metodo da entidade para aplicar as atualizações.
        // É crucial passar o passwordEncoder aqui!
        usuario.atualizarInformacoes(dados, passwordEncoder);

        // A transação do Spring/JPA se encarrega de salvar as alterações.

        return new DadosDetalhamentoUsuario(usuario);
    }

    @Transactional
    public void excluirUsuario(Long id) {
        var usuario = usuarioRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Usuário com id " + id + " não encontrado!"));

        usuario.excluir();
    }
}
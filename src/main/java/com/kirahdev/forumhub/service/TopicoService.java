package com.kirahdev.forumhub.service;

import com.kirahdev.forumhub.domain.dto.DadosAtualizacaoTopico;
import com.kirahdev.forumhub.domain.dto.DadosCadastroTopico;
import com.kirahdev.forumhub.domain.dto.DadosDetalhamentoTopico;
import com.kirahdev.forumhub.domain.entity.Topico;
import com.kirahdev.forumhub.repository.CursoRepository;
import com.kirahdev.forumhub.repository.TopicoRepository;
import com.kirahdev.forumhub.repository.UsuarioRepository;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import jakarta.validation.ValidationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service // Anotação que marca esta classe como um componente de serviço do Spring
public class TopicoService {

    @Autowired
    private TopicoRepository topicoRepository;
    @Autowired
    private UsuarioRepository usuarioRepository;
    @Autowired
    private CursoRepository cursoRepository;

    public Topico cadastrarTopico(DadosCadastroTopico dados) {
        // 1. Validação de duplicidade
        if (topicoRepository.existsByTituloAndMensagem(dados.titulo(), dados.mensagem())) {
            throw new ValidationException("Tópico duplicado! Já existe um tópico com o mesmo título e mensagem.");
        }

        // 2. Busca o autor e o curso pelo ID
        var autor = usuarioRepository.findById(dados.autorId())
                .orElseThrow(() -> new ValidationException("Autor com id " + dados.autorId() + " não encontrado!"));
        var curso = cursoRepository.findById(dados.cursoId())
                .orElseThrow(() -> new ValidationException("Curso com id " + dados.cursoId() + " não encontrado!"));

        // 3. Cria a entidade Tópico
        var topico = new Topico(dados, autor, curso);

        // 4. Salva no banco de dados
        topicoRepository.save(topico);

        return topico;
    }

    public Page<DadosDetalhamentoTopico> listarTodos(String nomeCurso, Pageable paginacao) {
        Page<Topico> pageDeTopicos;

        if (nomeCurso == null) {
            // Se não veio nome do curso, usa a busca padrão
            pageDeTopicos = topicoRepository.findAll(paginacao);
        } else {
            // Se veio nome do curso, usa o novo metodo de busca
            pageDeTopicos = topicoRepository.findByCursoNome(nomeCurso, paginacao);
        }

        return pageDeTopicos.map(DadosDetalhamentoTopico::new);
    }

    public DadosDetalhamentoTopico detalharPorId(Long id) {

        var topico = topicoRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Tópico com id " + id + " não encontrado!"));


        return new DadosDetalhamentoTopico(topico);
    }

    @Transactional // Garante que a operação seja atômica (ou tudo funciona, ou nada é alterado)
    public DadosDetalhamentoTopico atualizarTopico(Long id, DadosAtualizacaoTopico dados) {
        // 1. Validação de duplicidade (a parte mais complexa)
        // Se o usuário está tentando alterar para um título/mensagem que já existe em OUTRO tópico.
        if (dados.titulo() != null && dados.mensagem() != null) {
            var topicoDuplicado = topicoRepository.findByTituloAndMensagem(dados.titulo(), dados.mensagem());
            if (topicoDuplicado.isPresent() && !topicoDuplicado.get().getId().equals(id)) {
                throw new ValidationException("Já existe um tópico com este título e mensagem!");
            }
        }

        // 2. Busca o tópico no banco de dados. Se não encontrar, a exceção 404 será lançada.
        var topico = topicoRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Tópico com id " + id + " não encontrado!"));

        // 3. Chama o metodo da própria entidade para atualizar suas informações
        topico.atualizarInformacoes(dados);

        // 4. JPA/Hibernate detecta a alteração e salva no banco ao final da transação.
        // Não precisamos chamar o metodo save().

        // 5. Retorna um DTO com os dados atualizados.
        return new DadosDetalhamentoTopico(topico);
    }

    @Transactional
    public void excluirTopico(Long id) {
        var topico = topicoRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Tópico com id " + id + " não encontrado!"));

        topico.excluir(); // Chama o metodo de exclusão lógica da entidade

        // O @Transactional cuida de salvar a alteração no banco.
    }
}
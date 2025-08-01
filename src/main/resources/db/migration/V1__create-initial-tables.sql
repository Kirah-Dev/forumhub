-- Cria a tabela de Cursos
CREATE TABLE cursos (
    id BIGINT NOT NULL AUTO_INCREMENT,
    nome VARCHAR(100) NOT NULL UNIQUE,
    categoria VARCHAR(100) NOT NULL,
    PRIMARY KEY(id)
);

-- Cria a tabela de Usuários
CREATE TABLE usuarios (
    id BIGINT NOT NULL AUTO_INCREMENT,
    nome VARCHAR(100) NOT NULL,
    email VARCHAR(100) NOT NULL UNIQUE,
    senha VARCHAR(255) NOT NULL,
    PRIMARY KEY(id)
);

-- Cria a tabela de Tópicos, com chaves estrangeiras para curso e autor (usuário)
CREATE TABLE topicos (
    id BIGINT NOT NULL AUTO_INCREMENT,
    titulo VARCHAR(255) NOT NULL UNIQUE,
    mensagem TEXT NOT NULL,
    data_criacao DATETIME NOT NULL,
    status VARCHAR(100) NOT NULL,
    curso_id BIGINT NOT NULL,
    autor_id BIGINT NOT NULL,
    PRIMARY KEY(id),
    FOREIGN KEY(curso_id) REFERENCES cursos(id),
    FOREIGN KEY(autor_id) REFERENCES usuarios(id)
);

-- Cria a tabela de Respostas, com chaves estrangeiras para tópico e autor (usuário)
CREATE TABLE respostas (
    id BIGINT NOT NULL AUTO_INCREMENT,
    mensagem TEXT NOT NULL,
    data_criacao DATETIME NOT NULL,
    solucao BOOLEAN DEFAULT FALSE,
    topico_id BIGINT NOT NULL,
    autor_id BIGINT NOT NULL,
    PRIMARY KEY(id),
    FOREIGN KEY(topico_id) REFERENCES topicos(id),
    FOREIGN KEY(autor_id) REFERENCES usuarios(id)
);

-- Cria a tabela de Perfis (Roles)
CREATE TABLE perfis (
    id BIGINT NOT NULL AUTO_INCREMENT,
    nome VARCHAR(100) NOT NULL UNIQUE,
    PRIMARY KEY(id)
);

-- Cria a tabela de junção para o relacionamento Muitos-para-Muitos entre Usuários e Perfis
CREATE TABLE usuarios_perfis (
    usuario_id BIGINT NOT NULL,
    perfil_id BIGINT NOT NULL,
    PRIMARY KEY(usuario_id, perfil_id),
    FOREIGN KEY(usuario_id) REFERENCES usuarios(id),
    FOREIGN KEY(perfil_id) REFERENCES perfis(id)
);
# FórumHub API

![Status](https://img.shields.io/badge/status-Em%20Desenvolvimento-yellow?style=for-the-badge)
![Java](https://img.shields.io/badge/Java-21-blue?style=for-the-badge&logo=java)
![Spring Boot](https://img.shields.io/badge/Spring%20Boot-3.3.1-brightgreen?style=for-the-badge&logo=spring)
![Licença](https://img.shields.io/badge/licença-MIT-informational?style=for-the-badge)

## 📖 Sobre o Projeto

A **FórumHub API** é o back-end para uma plataforma de fórum de discussão, desenvolvida como parte do Challenge Back-End da Alura. O projeto consiste em uma API RESTful que permite aos usuários gerenciar tópicos, cursos e usuários, seguindo as melhores práticas de desenvolvimento com o ecossistema Spring.

A API foi construída para ser robusta, segura e escalável, implementando um CRUD completo para as principais entidades do sistema, além de funcionalidades avançadas como paginação, ordenação e exclusão lógica.

---

## ⚙️ Funcionalidades

A API oferece um conjunto completo de operações CRUD para as seguintes entidades:

*   **Tópicos:**
    *   `POST /topicos`: Cria um novo tópico.
    *   `GET /topicos`: Lista todos os tópicos ativos, com suporte a paginação e ordenação.
    *   `GET /topicos?nomeCurso=...`: Filtra tópicos por nome do curso.
    *   `GET /topicos/{id}`: Detalha um tópico específico.
    *   `PUT /topicos/{id}`: Atualiza um tópico existente.
    *   `DELETE /topicos/{id}`: Realiza a exclusão lógica de um tópico.

*   **Usuários:**
    *   `POST /usuarios`: Cadastra um novo usuário com senha criptografada (BCrypt).
    *   `GET /usuarios`: Lista todos os usuários ativos.
    *   `GET /usuarios/{id}`: Detalha um usuário específico.
    *   `PUT /usuarios/{id}`: Atualiza os dados de um usuário.
    *   `DELETE /usuarios/{id}`: Realiza a exclusão lógica de um usuário.

*   **Cursos:**
    *   `POST /cursos`: Cadastra um novo curso.
    *   `GET /cursos`: Lista todos os cursos ativos.
    *   `GET /cursos/{id}`: Detalha um curso específico.
    *   `PUT /cursos/{id}`: Atualiza os dados de um curso.
    *   `DELETE /cursos/{id}`: Realiza a exclusão lógica de um curso.

### ✨ Recursos Adicionais
- **Validações:** Regras de negócio aplicadas usando a API de Validação do Spring (Bean Validation).
- **Tratamento de Erros:** Respostas de erro padronizadas e códigos de status HTTP corretos para diferentes cenários.
- **Segurança:** Estrutura base para Spring Security, com senhas criptografadas.
- **Exclusão Lógica (Soft Delete):** Nenhum dado é fisicamente removido do banco, garantindo a integridade referencial.
- **Paginação e Ordenação:** A listagem de tópicos suporta paginação e ordenação via parâmetros na URL.

---

## 🛠️ Tecnologias Utilizadas

- **Linguagem:** Java 21
- **Framework:** Spring Boot 3
- **Módulos Spring:**
    - Spring Web: Para a construção de APIs REST.
    - Spring Data JPA: Para a persistência de dados.
    - Spring Security: Para a camada de segurança.
- **Banco de Dados:** MySQL 8
- **Gerenciamento de Migrações:** Flyway
- **Ferramentas de Build:** Maven
- **Utilitários:** Lombok

---

## 🚀 Como Executar o Projeto

Siga os passos abaixo para executar a API em seu ambiente local.

### Pré-requisitos
- Java JDK 21 ou superior
- Maven 4.0.0 ou superior
- MySQL 8 ou superior
- Um cliente de API como [Insomnia](https://insomnia.rest/) ou [Postman](https://www.postman.com/)

### 1. Clonar o Repositório
```bash
git clone https://github.com/seu-usuario/forumhub.git
cd forumhub
```

### 2. Configurar o Banco de Dados
1.  Acesse seu cliente MySQL e crie um banco de dados para o projeto:
    ```sql
    CREATE DATABASE forumhub_db;
    ```
2.  Abra o arquivo `src/main/resources/application.properties`.
3.  Altere as seguintes propriedades com suas credenciais do MySQL:
    ```properties
    spring.datasource.url=jdbc:mysql://localhost:3306/forumhub_db
    spring.datasource.username=seu_usuario_mysql
    spring.datasource.password=sua_senha_mysql
    ```

### 3. Executar a Aplicação
Você pode executar a aplicação de duas formas:

- **Via terminal, usando o Maven Wrapper:**
  ```bash
  ./mvnw spring-boot:run
  ```
- **Através da sua IDE:** Encontre a classe `ForumhubApplication.java` e execute-a.

A API estará disponível em `http://localhost:8080`. O Flyway executará as migrações e criará todas as tabelas na primeira inicialização.

---

## 📝 Licença

Este projeto está sob a licença MIT. Veja o arquivo [LICENSE](https://github.com/Kirah-Dev/forumhub/blob/main/README.md) para mais detalhes.

---

## 👨‍💻 Autor

Desenvolvido por **Wellen Souza (Kirah-Dev)**.

- **LinkedIn:** `https://www.linkedin.com/in/wellensouza/`
- **GitHub:** `https://github.com/kirah-dev`
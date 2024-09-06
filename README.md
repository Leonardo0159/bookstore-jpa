
# Bookstore API

Este projeto é uma API RESTful para um sistema de livraria, desenvolvida em Kotlin com o framework Spring Boot e utilizando PostgreSQL como banco de dados. A API permite gerenciar livros, publicadores, autores e reviews, suportando operações CRUD (Create, Read, Update, Delete) para cada uma dessas entidades.

## Tecnologias Utilizadas

- **Kotlin**: Linguagem de programação utilizada para o desenvolvimento do sistema.
- **Spring Boot**: Framework para criação da API e gerenciamento de dependências.
- **PostgreSQL**: Banco de dados relacional utilizado para armazenar as informações.
- **JPA (Java Persistence API)**: Utilizado para mapeamento objeto-relacional (ORM).

## Funcionalidades

A API oferece funcionalidades para gerenciar as seguintes entidades:

- **Livros (Books)**: Operações de criação, leitura, atualização e exclusão de livros. Cada livro está associado a um publicador, múltiplos autores e um review.
- **Publicadores (Publishers)**: Operações de criação, leitura, atualização e exclusão de publicadores.
- **Autores (Authors)**: Operações de criação, leitura, atualização e exclusão de autores.
- **Reviews**: Cada livro possui um review associado, e a API permite gerenciar este relacionamento.

## Estrutura do Projeto

### Modelos

As principais entidades do sistema são:

- **BookModel**: Representa um livro na livraria.
- **PublisherModel**: Representa um publicador.
- **AuthorModel**: Representa um autor.
- **ReviewModel**: Representa um review associado a um livro.

### DTOs

- **BookDTO**: Utilizado para a criação e atualização de livros, encapsulando os campos necessários como título, ID do publicador, IDs dos autores e o comentário do review.

### Relacionamentos

- Um **livro** tem um relacionamento de `ManyToOne` com um **publicador**.
- Um **livro** tem um relacionamento de `ManyToMany` com **autores**.
- Um **livro** tem um relacionamento de `OneToOne` com um **review**.

## Endpoints

Abaixo estão os principais endpoints da API:

### Livros

- `GET /books`: Retorna todos os livros.
- `GET /books/{id}`: Retorna um livro específico.
- `POST /books`: Cria um novo livro.
- `PUT /books/{id}`: Atualiza um livro existente.
- `DELETE /books/{id}`: Exclui um livro.

### Publicadores

- `GET /publishers`: Retorna todos os publicadores.
- `GET /publishers/{id}`: Retorna um publicador específico.
- `POST /publishers`: Cria um novo publicador.
- `PUT /publishers/{id}`: Atualiza um publicador existente.
- `DELETE /publishers/{id}`: Exclui um publicador.

### Autores

- `GET /authors`: Retorna todos os autores.
- `GET /authors/{id}`: Retorna um autor específico.
- `POST /authors`: Cria um novo autor.
- `PUT /authors/{id}`: Atualiza um autor existente.
- `DELETE /authors/{id}`: Exclui um autor.

## Configuração do Ambiente

### Pré-requisitos

- **JDK 21**
- **Gradle** como gerenciador de dependências.
- **PostgreSQL**: Banco de dados deve estar instalado e configurado.

### Configuração do Banco de Dados

No arquivo `application.properties` ou `application.yml`, configure as credenciais do PostgreSQL:

```properties
spring.application.name=jpa

spring.datasource.url=jdbc:postgresql://localhost:5432/bookstore-jpa
spring.datasource.username=
spring.datasource.password=
spring.jpa.hibernate.ddl-auto=update

spring.jpa.properties.hibernate.jdbc.lob.non_contextual_creation=true
spring.jpa.show-sql=true
```

### Executando a Aplicação

1. Clone o repositório:

   ```bash
   git clone https://github.com/Leonardo0159/bookstore-jpa.git
   cd bookstore-jpa
   ```

2. Compile e execute o projeto:

   - Com **Gradle**:

     ```bash
     ./gradlew build
     ./gradlew bootRun
     ```

3. A API estará disponível em `http://localhost:8080`.

### Testando a API

Você pode utilizar o **Postman** ou outra ferramenta similar para testar os endpoints. Aqui está um exemplo de corpo de requisição:

#### Publisher:
```json
{
  "name": "Tech Publishing"
}
```

#### Author:
```json
{
  "name": "Dmitry Jemerov"
}
```

#### Livro:
```json
{
  "title": "The Pragmatic Programmer",
  "publisherId": 1,
  "authorIds": [1, 2],
  "review": "Excelente livro para desenvolvedores."
}
```

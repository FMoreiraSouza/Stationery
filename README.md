# Stationery

![Spring Boot](https://img.shields.io/badge/Spring%20Boot-3.4.2-green?logo=spring-boot)
![Kotlin](https://img.shields.io/badge/Kotlin-1.9.25-blue?logo=kotlin)

---

## 📃 Descrição

O **Stationery** é uma aplicação Spring Boot desenvolvida em Kotlin para gerenciamento de uma papelaria. A aplicação permite o cadastro e gerenciamento de usuários, fornecedores e produtos, com autenticação segura baseada em JWT (JSON Web Token) e controle de acesso por papéis (`ADMIN` e `USER`). O sistema utiliza uma **arquitetura em camadas**, inspirada em **Domain-Driven Design (DDD)**, com separação clara entre as camadas de domínio (`entity`, `repository`, `service`), aplicação (`controller`, `dto`) e infraestrutura (`security`). A persistência é feita com Spring Data JPA, utilizando o banco de dados H2 em memória para desenvolvimento, mas com suporte para outros bancos relacionais. A API RESTful é protegida por autenticação e autorização, com documentação gerada automaticamente via Swagger/OpenAPI, acessível através da interface do Swagger.

---

## 💻 Tecnologias Utilizadas

- **Kotlin**: Linguagem principal para desenvolvimento.
- **Spring Boot**: Framework para construção da aplicação.
- **Spring Data JPA**: Persistência de dados com suporte a banco de dados relacional.
- **Spring Security**: Autenticação e autorização baseadas em JWT.
- **H2 Database**: Banco de dados em memória para desenvolvimento e testes.
- **Swagger/OpenAPI**: Documentação automática da API, acessível via `/swagger-ui.html`.
- **Jackson**: Serialização/deserialização JSON.
- **JWT**: Autenticação de usuários.
- **SLF4J**: Logging da aplicação.

---

## 🛎️ Funcionalidades

- **Gerenciamento de Usuários**:
  - Cadastro de novos usuários (somente usuários não-administradores podem se cadastrar diretamente).
  - Autenticação via login com email e senha, retornando um token JWT.
  - Listagem de usuários por papel (ex.: `ADMIN`, `USER`) ou todos os usuários, ordenados por nome.
  - Adição de papéis a usuários existentes (ex.: promover um usuário a `ADMIN`).
  - Exclusão de usuários.

- **Gerenciamento de Fornecedores**:
  - Cadastro de fornecedores com nome e contato.
  - Listagem de todos os fornecedores (somente para administradores).

- **Gerenciamento de Produtos**:
  - Cadastro de novos produtos com nome, descrição, preço e estoque.
  - Listagem de produtos com ordenação configurável (ASC/DESC por ID).
  - Atualização de estoque (reabastecimento).
  - Compra de produtos com validação de estoque.
  - Associação de produtos a fornecedores.
  - Exclusão de produtos.

- **Segurança**:
  - Autenticação baseada em JWT com expiração configurável (48 horas para usuários comuns, 1 hora para administradores).
  - Controle de acesso por papéis (`ADMIN` para operações administrativas, `USER` para compras).
  - Suporte a CORS para integração com frontends.
  - Configuração de um administrador padrão no bootstrap da aplicação.

---

## 📱 Execução

A aplicação utiliza o banco de dados H2 em memória para desenvolvimento, com console habilitado em `/h2` para depuração. Os endpoints da API estão disponíveis em `/api` (ex.: `/api/users`, `/api/products`). A autenticação é necessária para a maioria das operações, exceto para cadastro de usuários e login. A interface do Swagger está disponível em `http://localhost:8080/api/swagger-ui.html` para explorar e testar os endpoints da API de forma interativa.

---

## ▶️ Como Rodar o Projeto

### Pré-requisitos

- **JDK 17** ou superior.
- **Kotlin 1.9.25**.
- **Gradle 8.x** (gerenciado automaticamente pelo wrapper `gradlew`).
- **IntelliJ IDEA** (versão recomendada: 2024.2 ou mais recente) ou outra IDE compatível.

### Clone o repositório

- git clone <URL_DO_PROJETO>

### Configuração

#### Configurações do Banco de Dados:

- A aplicação utiliza o banco de dados H2 em memória por padrão, configurado em application.properties:
  ```bash
  propertiesspring.datasource.url=jdbc:h2:file:./data/testdb
  spring.datasource.driverClassName=org.h2.Driver
  spring.datasource.username=sa
  spring.datasource.password=password
  spring.jpa.hibernate.ddl-auto=update
- O console H2 está habilitado em /h2 para depuração, acessível em http://localhost:8080/api/h2.

#### Configurações de Segurança:

- As propriedades de autenticação JWT estão definidas em application.properties:
  ```bash
  propertiessecurity.token.secret=2073d061099efb74b4cace763e6ca2bcfb0eac7a
  security.token.issuer=StationeryServer
  security.token.expire-hours=48
  security.token.admin-expire-hours=1
  security.admin.name=Stationery Admin
  security.admin.password=admin125
  security.admin.email=stationery-admin@example.com
-Um usuário administrador padrão será criado automaticamente na primeira inicialização.

### Instale as dependências

- Execute o comando para sincronizar as dependências:
  ```bash
  ./gradlew build
  
### Rode o servidor

- Clique em **Run** ou abra o terminal e use o comando:
  ```bash
  ./gradlew run
- A aplicação estará disponível em https://localhost:8080/api para explorar e testar os endpoints.
- O console H2 estará acessível em https://localhost:8080/api/h2 para operações de banco de dados.

## 🎥 Apresentação do Sistema

Confira a apresentação do sistema em duas partes:  

- [Parte 1](https://youtu.be/_seTnTCh8HI)  
- [Parte 2](https://youtu.be/y9XF9KyLZvE)

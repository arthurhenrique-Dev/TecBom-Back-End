# 🚀 TecBom - E-commerce API (Clean Architecture)

![Java](https://img.shields.io/badge/Java-21-orange?style=for-the-badge&logo=java)
![Spring](https://img.shields.io/badge/Spring%20Boot-3.5-brightgreen?style=for-the-badge&logo=springboot)
![MongoDB](https://img.shields.io/badge/MongoDB-NoSQL-47A248?style=for-the-badge&logo=mongodb)
![Postgres](https://img.shields.io/badge/Postgres-SQL-336791?style=for-the-badge&logo=postgresql)
![Docker](https://img.shields.io/badge/Docker-24.0-2496ED?style=for-the-badge&logo=docker)
![Spring Security](https://img.shields.io/badge/Spring%20Security-6.4-6DB33F?style=for-the-badge&logo=springsecurity)
![JWT](https://img.shields.io/badge/JWT-Tokens-black?style=for-the-badge&logo=jsonwebtokens)
![JaCoCo](https://img.shields.io/badge/Coverage-67%25-yellow?style=for-the-badge)
![Swagger](https://img.shields.io/badge/Swagger-OpenAPI%203-85EA2D?style=for-the-badge&logo=swagger)

O **TecBom** é uma API RESTful robusta de e-commerce desenvolvida com foco em padrões de excelência de engenharia de software. O projeto foi estruturado para ser escalável, utilizando princípios de **Arquitetura Limpa**, **Hexagonal** e **DDD (Domain-Driven Design)**.

---

## 🏗️ Arquitetura e Design de Software

O projeto segue a **Clean Architecture e Arquitetura Hexagonal**, garantindo que a lógica de negócio seja o centro da aplicação:

* **Isolamento de Domínio:** As regras de negócio não dependem de frameworks ou bancos de dados.
* **Camada de Aplicação:** Casos de uso bem definidos que orquestram o fluxo de dados.
* **Ports & Adapters:** Implementação flexível de infraestrutura, permitindo o uso de múltiplos bancos de dados (SQL e NoSQL).
* **Value Objects:** Validações ricas para garantir a integridade de dados como CPF, E-mail, Preços e Nomes.

---

## 🧪 Qualidade de Código e Testes

A aplicação utiliza o **JaCoCo** para métricas de cobertura, garantindo a confiabilidade do sistema:

* **Cobertura Atual:** 67% total.
* **Foco no Core:** A camada de **Application (UseCases)** possui **100% de cobertura**.
* **Testes Unitários:** Implementados com **JUnit 5** e **Mockito** para simular comportamentos de infraestrutura e focar na lógica.

---

## 🛠️ Tecnologias e Ferramentas

* **Java 21** & **Spring Boot 3.5**
* **Spring Security + JWT** para autenticação e autorização.
* **PostgreSQL** para dados relacionais e segurança.
* **MongoDB** para catálogo de produtos (alta performance de leitura).
* **Flyway** para migrações de banco de dados SQL.
* **Docker & Docker Compose** para ambiente de desenvolvimento.

---

## 🚀 Como Executar o Projeto

Este projeto está totalmente containerizado. Você não precisa instalar Java ou bancos de dados localmente; o **Docker** gerencia todo o ambiente.

1. **Clone o repositório:**
```bash
   git clone https://github.com/arthurhenrique-Dev/TecBom-Back-End.git
   cd TecBom-Back-End
```
2. **Suba a aplicação e os bancos de dados:**
```bash
   docker-compose up -d
   
```
3. **Acesse a API:**
   A aplicação estará disponível em: http://localhost:8080

---

# 🚀 API Endpoints - TecBom E-commerce

Documentação técnica dos endpoints da API, organizada por controladores de domínio.

## 🛡️ Admin Dashboard (`AdminController`)
Gerenciamento de usuários, permissões e status de contas administrativas.

| Verbo | Endpoint | Descrição | Parâmetros |
| :--- | :--- | :--- | :--- |
| **DELETE** | `/TecBom/admin_dashboard/master` | Deleta usuário Master | `Cpf` (Body) |
| **DELETE** | `/TecBom/admin_dashboard/user/stats` | Deleta usuário por CPF | `Cpf` (Body) |
| **PUT** | `/TecBom/admin_dashboard/master` | Remove privilégios de Admin (Dismiss) | `Cpf` (Body) |
| **PUT** | `/TecBom/admin_dashboard/user` | Promove usuário a Admin (Hire) | `Cpf` (Body) |
| **POST** | `/TecBom/admin_dashboard/user/stats` | Reativa a conta de um usuário | `Cpf` (Body) |
| **GET** | `/TecBom/admin_dashboard/user` | Lista/Busca usuários comuns | `Cpf`, `Name`, `EmailVO`, `PhoneNumber` (Path) |
| **GET** | `/TecBom/admin_dashboard/admin` | Lista/Busca administradores | `Cpf`, `Name`, `EmailVO`, `PhoneNumber` (Path) |

---

## 🔑 Autenticação & Segurança (`AuthController`)
Fluxos de registro, login e validação de tokens.

| Verbo | Endpoint | Descrição | Parâmetros |
| :--- | :--- | :--- | :--- |
| **POST** | `/TecBom/auth/login` | Autenticação e geração de Token JWT | `DTOLogin` (Body) |
| **POST** | `/TecBom/auth/register/user` | Cadastro de novo usuário cliente | `DTOSaveUser` (Body) |
| **POST** | `/TecBom/auth/register/admin` | Cadastro de novo administrador | `DTOSaveUser` (Body) |
| **POST** | `/TecBom/auth/register/master` | Cadastro de usuário nível Master | `DTOSignInMaster` (Body) |
| **PUT** | `/TecBom/auth/validate/email` | Confirmação de e-mail via token | `DTOEmailValidation` (Body) |
| **PUT** | `/TecBom/auth/validate/password/token` | Validação de token para troca de senha | `DTOUpdatePasswordUser` (Body) |
| **PUT** | `/TecBom/auth/validate/password/update` | Atualização final da senha | `Cpf` (Body) |

---

## 🛒 Catálogo & Produtos (`ProductController`)
Operações de busca, estoque e gerenciamento de modelos.

| Verbo | Endpoint | Descrição | Parâmetros |
| :--- | :--- | :--- | :--- |
| **GET** | `/TecBom/Shop/admin/products` | Busca detalhada (visão admin) | `searchTerm`, `category`, `price`, `orderBy` (Path) |
| **PUT** | `/TecBom/Shop/admin/products/discount` | Aplica descontos em produtos | `DTODiscount` (Body) |
| **PUT** | `/TecBom/Shop/admin/products` | Atualiza informações de um produto | `DTOUpdateProduct` (Body) |
| **DELETE** | `/TecBom/Shop/admin/products/model` | Remove um modelo específico | `DTODeleteModel` (Body) |
| **POST** | `/TecBom/Shop/admin/products/model` | Adiciona novo modelo ao produto | `DTONewModel` (Body) |
| **POST** | `/TecBom/Shop/admin/products` | Cria um novo produto no sistema | `DTOSaveProduct` (Body) |
| **POST** | `/TecBom/Shop/admin/products/purchase` | Registra entrada de estoque/compra | `DTORecordPurchase` (Body) |
| **GET** | `/TecBom/Shop` | Busca de produtos (visão cliente) | `searchTerm`, `category`, `price`, `orderBy` (Path) |

---

## 👤 Perfil do Usuário (`UserController`)
Gerenciamento de conta pessoal e carrinho de compras.

| Verbo | Endpoint | Descrição | Parâmetros |
| :--- | :--- | :--- | :--- |
| **POST** | `/TecBom/user/cart/add` | Adiciona produto ao carrinho | `DTOAddCartItem` (Body) |
| **DELETE** | `/TecBom/user/configuration/deactivate` | Desativa a conta do usuário logado | `Cpf` (Query/Param) |
| **DELETE** | `/TecBom/user/cart/remove` | Remove produto do carrinho | `DTORemoveCartItem` (Body) |
| **PUT** | `/TecBom/user/configuration/update` | Atualiza dados de perfil | `DTOUpdateUser` (Body) |
| **GET** | `/TecBom/user/cart` | Visualiza itens do carrinho | `Cpf` (Body) |

---
*Nota: Endpoints que envolvem dados sensíveis de usuário possuem validação de `@PreAuthorize` para garantir que o CPF solicitado corresponde ao usuário autenticado.*

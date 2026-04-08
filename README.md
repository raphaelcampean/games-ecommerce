# 🎮 Games E-commerce API

API REST para gerenciamento de um e-commerce de jogos, desenvolvida com **Java 17** e **Spring Boot 3**.
O projeto foi estruturado com foco em **boas práticas de backend**, **segurança**, **integridade de dados** e **alta cobertura de testes**.

---

## 🚀 Tecnologias

* **Java 17**
* **Spring Boot 3.x**
* **Spring Security**
* **PostgreSQL** (produção/desenvolvimento)
* **H2 Database** (testes)
* **JUnit 5**
* **Mockito**
* **AssertJ**
* **Maven**
* **Docker (opcional)**

---

## 🔐 Segurança

A aplicação implementa práticas essenciais de segurança:

* **Password Hashing**

  * Senhas protegidas com `BCryptPasswordEncoder`
  * Nunca armazenadas em texto plano

* **Validação de Dados**

  * Prevenção de duplicidade (e-mail/usuário)
  * Validação de entrada com DTOs

* **Estrutura para Auditoria**

  * Preparada para logging de autenticação e falhas

---

## 🧪 Testes

O projeto segue a pirâmide de testes:

### ✔️ Testes Unitários (Service)

* Foco em regras de negócio
* Uso de Mockito para isolamento
* Validação de:

  * regras de usuário
  * integridade de dados

### ✔️ Testes de Integração (Repository)

* Uso de `@DataJpaTest`
* Banco em memória (H2)
* Testes de queries e performance (ex: `JOIN FETCH`)

---

## 📱 Testes em Dispositivo Real

A API foi testada em hardware real de baixo custo para validar:

* desempenho em redes reais
* latência de requisições
* compatibilidade com dispositivos limitados

**Ferramentas utilizadas:**

* ADB (Android Debug Bridge)
* Ambiente Linux (Pop!_OS)

---

## ⚙️ Como executar o projeto

### 📌 Pré-requisitos

* Java 17
* Maven 3+
* PostgreSQL

---

### 📥 Clonar o repositório

```bash
git clone https://github.com/seu-usuario/games-ecommerce.git
cd games-ecommerce
```

---

### ⚙️ Configurar o banco de dados

Edite o arquivo:

```
src/main/resources/application.properties
```

Exemplo:

```properties
spring.datasource.url=jdbc:postgresql://localhost:5432/gamesdb
spring.datasource.username=seu_usuario
spring.datasource.password=sua_senha

spring.jpa.hibernate.ddl-auto=update
spring.jpa.show-sql=true
```

---

### ▶️ Rodar a aplicação

```bash
./mvnw spring-boot:run
```

ou

```bash
mvn spring-boot:run
```

---

### 🧪 Rodar os testes

```bash
./mvnw test
```

ou

```bash
mvn test
```

---

## 📄 Estrutura do Projeto

```
src/
 ├── main/
 │   ├── java/
 │   │   └── ... (controllers, services, repositories)
 │   └── resources/
 └── test/
     └── java/ (testes unitários e de integração)
```

---

## 📌 Melhorias futuras

* Documentação com Swagger/OpenAPI
* Deploy em ambiente cloud
* Implementação de cache
* Monitoramento e logs estruturados

---

## 👨‍💻 Autor

Desenvolvido por Raphael Campean.

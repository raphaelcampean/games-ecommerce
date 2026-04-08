# Games E-commerce API
Esta é uma API robusta para gerenciamento de um e-commerce de jogos, desenvolvida com Java 17 e Spring Boot. O projeto foi construído com foco em segurança da informação (Cybersecurity), integridade de dados e alta cobertura de testes.

🚀 Tecnologias Utilizadas
Backend: Java 17, Spring Boot 3.x

Banco de Dados: PostgreSQL (Produção/Dev), H2 Database (Testes)

Segurança: Spring Security, BCrypt Password Encoding

Testes: JUnit 5, Mockito, AssertJ

Ferramentas: Maven, Docker, ADB (Android Debug Bridge) para testes mobile

🛡️ Funcionalidades de Cybersecurity
Diferente de sistemas comuns, esta aplicação implementa camadas extras de proteção:

Password Hashing: Uso de BCryptPasswordEncoder para garantir que senhas nunca sejam armazenadas em texto plano.

Data Integrity: Validações rigorosas no UserService para impedir duplicidade de e-mails e usuários.

Audit-Ready: Estrutura preparada para logs de tentativas de acesso e monitoramento de falhas.

🧪 Estrutura de Testes
A aplicação segue a pirâmide de testes para garantir estabilidade:

1. Testes Unitários (Service Layer)
Focados em lógica de negócio e segurança, utilizando Mockito para isolar dependências.

Validação de força de senha.

Garantia de unicidade de credenciais.

2. Testes de Integração (Repository Layer)
Utilizam @DataJpaTest e banco H2 para validar queries complexas.

Otimização de Performance: Testes de queries com JOIN FETCH para evitar o problema de N+1.

📱 Testes em Hardware Real (Samsung J4)
Um diferencial deste projeto é a validação de consumo da API em dispositivos físicos de baixo custo, garantindo que a aplicação seja leve e acessível.

Debug: Realizado via ADB no Pop!_OS.

Ambiente: Testes de latência e integração em rede local Wi-Fi.

⚙️ Como Executar
Pré-requisitos
JDK 17

Maven 3.x

PostgreSQL

Passos
Clone o repositório:

git clone https://github.com/seu-usuario/games-ecommerce.git
Configure o banco de dados no application.properties.

Execute a aplicação:

mvn spring-boot:run

Para rodar os testes:
mvn test

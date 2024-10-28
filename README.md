# PratoUrbano - Sistema de Microsserviços

## 📋 Sobre o Projeto
PratoUrbano é um sistema baseado em arquitetura de microsserviços, desenvolvido com Spring Boot, que gerencia pedidos e pagamentos. O sistema utiliza Docker para containerização, MySQL como banco de dados, Flyway para controle de migrations e Netflix Eureka para service discovery.

## 🏗️ Arquitetura

O projeto é composto pelos seguintes componentes:

- **API Gateway**
  - Gerencia o roteamento das requisições
  - Registrado no Eureka Server
  - Containerizado com Docker

- **Serviço de Pedidos**
  - Gerencia o ciclo de vida dos pedidos
  - Banco de dados MySQL próprio
  - Migrations controladas pelo Flyway
  - Registrado no Eureka Server

- **Serviço de Pagamentos**
  - Processa os pagamentos dos pedidos
  - Banco de dados MySQL próprio
  - Migrations controladas pelo Flyway
  - Registrado no Eureka Server

**Nota:** O projeto depende de um servidor Eureka externo que deve estar em execução antes de iniciar os serviços do PratoUrbano.

## 🚀 Tecnologias Utilizadas

- Java
- Spring Boot
- Spring Cloud Netflix Eureka (Client)
- Spring Cloud Gateway
- Docker
- MySQL
- Flyway
- Maven

## 📦 Pré-requisitos

- Java 17 ou superior
- Docker e Docker Compose
- Maven
- MySQL (para desenvolvimento local)
- Servidor Eureka em execução

## 🛠️ Configuração e Instalação

### 1. Garantir que o Eureka Server está em execução
Certifique-se de que o servidor Eureka esteja rodando na porta padrão (8761) antes de prosseguir.

### 2. Iniciar os Serviços via Docker

```bash
# Na raiz do projeto
docker-compose up -d
````
Este comando irá:

- Criar as redes necessárias
- Iniciar os containers MySQL para cada serviço
- Construir e iniciar os containers dos serviços (Gateway, Pedidos e Pagamentos)
- Executar as migrations do Flyway automaticamente

## 📊 Endpoints e Portas
- API Gateway: http://localhost:8080
- Serviço de Pedidos: http://localhost:8081
- Serviço de Pagamentos: http://localhost:8082

## 🗄️ Estrutura do Projeto
  ```
  prato-urbano/
├── docker-compose.yml
├── Dockerfile
├── pom.xml
├── gateway/
│   ├── src/
│   ├── Dockerfile
│   └── pom.xml
├── servico-pedidos/
│   ├── src/
│   ├── Dockerfile
│   └── pom.xml
└── servico-pagamentos/
    ├── src/
    ├── Dockerfile
    └── pom.xml
```
## 🔄 Migrations
As migrations são gerenciadas pelo Flyway e são executadas automaticamente quando os serviços são iniciados. Elas estão localizadas em:    
```
src/main/resources/db/migration/
```
## 🔍 Monitoramento
Você pode monitorar o status dos serviços através do Eureka Server em:
```
http://localhost:8761
````
Todos os serviços do PratoUrbano (Gateway, Pedidos e Pagamentos) devem aparecer como registrados no dashboard do Eureka.
## ⚙️ Configuração dos Serviços
Cada serviço possui dois arquivos de configuração:
+ <span style="color: red;">`application.yml`:</span> Configurações para ambiente de desenvolvimento local 
+ <span style="color: red;">`application-docker.yml`:</span> Configurações específicas para ambiente Docker


As configurações incluem:
- Conexão com o banco de dados MySQL
- Configurações do Eureka Client
- Portas e endpoints
- Configurações do Flyway

## 🚦 Status do Serviço
Para verificar o status de cada serviço, você pode acessar:

+ Gateway: http://localhost:8080/actuator/health
+ Serviço de Pedidos: http://localhost:8081/actuator/health
## 🛟 Suporte
Para reportar problemas ou sugerir melhorias, por favor abra uma issue no repositório.

## 📄 Licença

Este projeto está licenciado sob a Licença MIT - veja o arquivo [LICENSE.md](LICENSE.md) para detalhes.

MIT License

Copyright (c) 2024 Pinalli

Permission is hereby granted, free of charge, to any person obtaining a copy
of this software and associated documentation files (the "Software"), to deal
in the Software without restriction, including without limitation the rights
to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
copies of the Software, and to permit persons to whom the Software is
furnished to do so, subject to the following conditions:

The above copyright notice and this permission notice shall be included in all
copies or substantial portions of the Software.

THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
SOFTWARE.

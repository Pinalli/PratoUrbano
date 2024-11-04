# PratoUrbano - Sistema de Microsserviços

## 📋 Sobre o Projeto
PratoUrbano é um sistema baseado em arquitetura de microsserviços, desenvolvido com Spring Boot, que gerencia pedidos e pagamentos. O sistema utiliza Docker para containerização, MySQL como banco de dados, Flyway para controle de migrations e Netflix Eureka para service discovery.

## 🏗️ Arquitetura

# Projeto PratoUrbano

O projeto **PratoUrbano** é composto pelos seguintes componentes:

## 1. **Eureka Server**
   - **Descrição**: Serviço de descoberta que registra e monitora os microsserviços, permitindo comunicação entre eles.
   - **Detalhes**:
     - Todos os serviços, incluindo o Gateway, os serviços de Pedidos e Pagamentos, estão registrados no Eureka Server.
     - Facilita a escalabilidade e o balanceamento de carga entre as instâncias dos microsserviços.

## 2. **API Gateway**
   - **Função**: Gerencia o roteamento das requisições para os serviços internos.
   - **Detalhes**:
     - Registrado no Eureka Server, onde é possível localizá-lo e mantê-lo monitorado.
     - Containerizado com Docker para garantir portabilidade e fácil implantação.

## 3. **Serviço de Pedidos**
   - **Função**: Gerencia o ciclo de vida dos pedidos, incluindo criação, atualização e consulta de pedidos.
   - **Detalhes**:
     - Utiliza uma instância de banco de dados MySQL própria para armazenamento de dados de pedidos.
     - Controle de migrações de banco de dados feito com Flyway, garantindo consistência e versionamento dos dados.
     - Registrado no Eureka Server, tornando-o localizável e monitorado junto aos demais serviços.

## 4. **Serviço de Pagamentos**
   - **Função**: Processa os pagamentos associados aos pedidos no sistema.
   - **Detalhes**:
     - Banco de dados MySQL próprio para manter os dados de pagamento independentes.
     - Controle de migrações de banco de dados feito com Flyway para assegurar a consistência dos dados e versionamento.
     - Registrado no Eureka Server, o que facilita sua descoberta e monitoramento.

---

Essa estrutura permite a fácil comunicação entre serviços e facilita a escalabilidade do sistema. O **Eureka Server** atua como um ponto central de registro, enquanto o **Gateway** controla o tráfego de entrada, e os serviços de **Pedidos** e **Pagamentos** operam de forma independente com seus próprios bancos de dados.


## 🚀 Tecnologias Utilizadas

- Java
- Spring Boot
- Spring Cloud Netflix Eureka (Client)
- Spring Cloud Gateway
- Docker
- MySQL
- Flyway
- Maven
- Eureka

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
PratoUrbano/<br>
│
├── eureka-server/
│   ├── src/
│   ├── Dockerfile
│   └── pom.xml
│
├── gateway/
│   ├── src/
│   ├── Dockerfile
│   ├── application.yml
│   ├── application-docker.yml
│   └── pom.xml
│
├── pagamentos/
│   ├── src/
│   ├── Dockerfile
│   ├── application.yml
│   ├── application-docker.yml
│   └── pom.xml<br>
│
├── pedidos/
│   ├── src/
│   ├── Dockerfile
│   ├── application.yml
│   ├── application-docker.yml
│   └── pom.xml
│
├── docker-compose.yml
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

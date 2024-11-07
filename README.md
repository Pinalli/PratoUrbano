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

## 5. **Comunicação entre Serviços**
   - **Tecnologia**: OpenFeign com Resilience4j
   - **Detalhes**:
     - Implementação de clients declarativos para chamadas REST entre serviços
     - Circuit Breaker para proteção contra falhas em cascata
     - Configuração de janela deslizante para análise de falhas
     - Monitoramento automático do estado da comunicação
     - Fallback automático em caso de falhas
     - Integração entre os serviços de Pedidos e Pagamentos
    
### Circuit Breaker
O sistema utiliza Resilience4j como implementação de Circuit Breaker para garantir resiliência na comunicação entre serviços:

- **Configuração**:
  - Janela deslizante: 3 chamadas
  - Mínimo de chamadas: 2
  - Tempo de espera no estado aberto: 50s

- **Estados do Circuit Breaker**:
  - CLOSED: Operação normal, requisições sendo processadas
  - OPEN: Circuit breaker ativado, requisições são rejeitadas
  - HALF_OPEN: Período de teste para verificar se o serviço se recuperou

- **Monitoramento**:
  - Métricas disponíveis via Actuator
  - Endpoint de health check inclui estado do circuit breaker

## 🔍 Monitoramento

Você pode monitorar o estado do Circuit Breaker através do endpoint:
  ```
http://localhost:8080/actuator/circuitbreakers
  ```

Essa estrutura permite a fácil comunicação entre serviços e facilita a escalabilidade do sistema. O **Eureka Server** atua como um ponto central de registro, enquanto o **Gateway** controla o tráfego de entrada, e os serviços de **Pedidos** e **Pagamentos** operam de forma independente com seus próprios bancos de dados.

## 📚 Documentação da API com Swagger

O projeto **PratoUrbano** inclui a documentação da API gerada pelo **Springdoc OpenAPI** (Swagger), facilitando a exploração e o teste das APIs dos microsserviços.

### Endpoints da Documentação
Cada serviço possui sua própria documentação Swagger, acessível através das seguintes URLs:

- **API Gateway**:
- **Serviço de Pedidos**:
[Acesse a documentação do Serviço de Pedidos](http://localhost:8081/swagger-ui/index.html#/)
- **Serviço de Pagamentos**:
[Acesse a documentação do Serviço de Pagamentos](http://localhost:8080/swagger-ui/index.html#/)

### Detalhes
- A documentação inclui todas as rotas expostas por cada serviço, detalhando os métodos HTTP, parâmetros de entrada, exemplos de resposta, entre outros.
- Para personalizar a documentação, foi criada a classe `OpenApiConfig` em cada serviço, definindo as informações de título, versão e descrição de cada API.

### Benefícios
- **Exploração Simplificada**: Navegação intuitiva para explorar os endpoints disponíveis.
- **Testes Rápidos**: Permite fazer chamadas diretas para a API através da interface web do Swagger.
- **Consistência**: Documentação atualizada automaticamente com base nas mudanças nos controladores do Spring Boot.
---

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
- OpenFeign - Comunicação síncrona entre serviços
- Resilience4j - Circuit Breaker para resiliência
- Springdoc OpenAPI (Swagger), facilitando a exploração e o teste das APIs dos microsserviços

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
docker-compose build
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

+ Gateway: http://localhost:8082/actuator/health+
+ Serviço de Pagamentos: http://localhost:8080/actuator/health
+ Serviço de Pedidos: http://localhost:8081/actuator/health


  
## Acesso aos Serviços via Gateway
O projeto utiliza um Gateway Spring Cloud para centralizar o acesso aos diferentes serviços. Após subir a aplicação completa com o Docker Compose, você pode acessar os serviços através das seguintes URLs:

## Serviço de Pagamentos
Acesse o serviço de Pagamentos através da URL:
```
http://localhost:8082/pagamentos-ms/pagamentos
```
## Serviço de Pedidos
Acesse o serviço de Pedidos através da URL:
```
http://localhost:8082/pedidos-ms/pedidos
```

Todas as requisições para os serviços de **Pagamentos** e **Pedidos** devem ser feitas através do **Gateway** na porta `8082`. O Gateway é responsável por rotear as requisições para o serviço correto, além de prover funcionalidades como balanceamento de carga, circuit breaker, etc.

Essa abordagem de utilizar um Gateway centralizado traz os seguintes benefícios:

- **Desacoplamento**: Os serviços ficam independentes e podem ser atualizados ou substituídos sem afetar o resto da aplicação.
- **Roteamento e Balanceamento**: O Gateway cuida do roteamento das requisições e do balanceamento de carga entre as instâncias dos serviços.
- **Segurança e Monitoramento**: O Gateway pode ser o ponto de entrada para autenticação, autorização e monitoramento das requisições.
- **Resiliência**: Funcionalidades como circuit breaker podem ser implementadas no Gateway para lidar com falhas nos serviços.

Portanto, todas as interações com a aplicação devem ser feitas através do **Gateway** na porta `8082`, que irá encaminhar as requisições para o serviço correto.


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

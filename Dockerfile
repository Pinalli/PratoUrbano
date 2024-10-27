# Etapa de compilação (imagem temporária)
FROM openjdk:17-slim AS builder
# Etapa de compilação
FROM openjdk:17-slim AS builder

# Instalar o Maven
RUN apt-get update && apt-get install -y maven

WORKDIR /app

# Copiar os arquivos necessários
COPY . .

# Build dos módulos
RUN mvn clean package -DskipTests

# Etapa final
FROM openjdk:17-slim

WORKDIR /app

# Copiar os JARs dos serviços
COPY --from=builder /app/pagamentos/target/*.jar /app/pagamentos.jar
COPY --from=builder /app/pedidos/target/*.jar /app/pedidos.jar

# O ENTRYPOINT será definido no docker-composedidos.jar"]
# Etapa de compilação (imagem temporária)
FROM openjdk:17-slim AS builder

# Instalar o Maven
RUN apt-get update && apt-get install -y maven

WORKDIR /app

# Copiar o POM raiz
COPY pom.xml .

# Copiar os módulos
COPY ./pagamentos/pom.xml ./pagamentos/
COPY ./pagamentos/src ./pagamentos/src
COPY ./pedidos/pom.xml ./pedidos/
COPY ./pedidos/src ./pedidos/src

# Build dos módulos
RUN mvn clean package -DskipTests -pl pagamentos,pedidos

# Etapa final (imagem final)
FROM openjdk:17-slim

WORKDIR /app
COPY --from=builder /app/pagamentos/target/*.jar pagamentos.jar
COPY --from=builder /app/pedidos/target/*.jar pedidos.jar

EXPOSE 8080
EXPOSE 8081
ENTRYPOINT ["java", "-jar", "pagamentos.jar"]
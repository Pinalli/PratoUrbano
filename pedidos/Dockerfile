#PEDIDOS

FROM maven:3.8.4-openjdk-17-slim AS builder

WORKDIR /app

# Copiar primeiro o POM pai
COPY pom.xml .
RUN mvn install -N -DskipTests

# Copiar o POM do módulo pedidos
COPY pedidos/pom.xml pedidos/
RUN mvn -f pedidos/pom.xml dependency:go-offline

# Copiar o código fonte do módulo pedidos
COPY pedidos/src pedidos/src

# Construir o projeto
RUN mvn -f pedidos/pom.xml clean package -DskipTests

# Etapa final
FROM eclipse-temurin:17-jre-focal
WORKDIR /app
COPY --from=builder /app/pedidos/target/pedidos-1.0-SNAPSHOT.jar app.jar

EXPOSE 8081
ENTRYPOINT ["java", "-jar", "app.jar"]
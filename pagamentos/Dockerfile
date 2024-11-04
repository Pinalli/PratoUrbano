#PAGAMENTOS

FROM maven:3.8.4-openjdk-17-slim AS builder

WORKDIR /app

# Copiar primeiro o POM pai
COPY pom.xml .
RUN mvn install -N -DskipTests

# Copiar o POM do módulo pagamentos
COPY pagamentos/pom.xml pagamentos/
RUN mvn -f pagamentos/pom.xml dependency:go-offline

# Copiar o código fonte do módulo pagamentos
COPY pagamentos/src pagamentos/src

# Construir o projeto
RUN mvn -f pagamentos/pom.xml clean package -DskipTests

# Etapa final
FROM eclipse-temurin:17-jre-focal
WORKDIR /app
COPY --from=builder /app/pagamentos/target/pagamentos-1.0-SNAPSHOT.jar app.jar

EXPOSE 8080
ENTRYPOINT ["java", "-jar", "app.jar"]
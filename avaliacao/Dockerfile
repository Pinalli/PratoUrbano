FROM maven:3.8.4-openjdk-17-slim AS builder

WORKDIR /app

# Copiar primeiro o POM pai
COPY pom.xml .
RUN mvn install -N -DskipTests

# Copiar o POM do módulo avaliacao
COPY avaliacao/pom.xml avaliacao/
RUN mvn -f avaliacao/pom.xml dependency:go-offline

# Copiar o código fonte do módulo avaliacao
COPY avaliacao/src avaliacao/src

# Construir o projeto
RUN mvn -f avaliacao/pom.xml clean package -DskipTests

# Etapa final
FROM eclipse-temurin:17-jre-focal
WORKDIR /app
COPY --from=builder /app/avaliacao/target/avaliacao-1.0-SNAPSHOT.jar app.jar

EXPOSE 8083
ENTRYPOINT ["java", "-jar", "app.jar"]
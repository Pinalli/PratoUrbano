
#GATEWAY
FROM maven:3.8.4-openjdk-17-slim AS builder

WORKDIR /app

# Copiar primeiro o POM pai
COPY pom.xml .
RUN mvn install -N -DskipTests

# Copiar o POM do módulo gateway
COPY gateway/pom.xml gateway/
RUN mvn -f gateway/pom.xml dependency:go-offline

# Copiar o código fonte do módulo gateway
COPY gateway/src gateway/src

# Construir o projeto
RUN mvn -f gateway/pom.xml clean package -DskipTests

# Etapa final
FROM eclipse-temurin:17-jre-focal
WORKDIR /app
COPY --from=builder /app/gateway/target/gateway-1.0-SNAPSHOT.jar app.jar

EXPOSE 8082
ENTRYPOINT ["java", "-jar", "app.jar"]
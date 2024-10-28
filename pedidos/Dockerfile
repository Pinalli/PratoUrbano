#PEDIDOS
FROM maven:3.8.4-openjdk-17-slim AS builder

WORKDIR /app

# Copiar o POM raiz
COPY . .

# Construir com Maven
RUN mvn clean package -DskipTests

# Etapa final
FROM eclipse-temurin:17-jre-focal
WORKDIR /app
COPY --from=builder /app/pedidos/target/*.jar app.jar

EXPOSE 8081
ENTRYPOINT ["java", "-jar", "app.jar"]
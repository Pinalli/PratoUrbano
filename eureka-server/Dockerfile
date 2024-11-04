
#EUREKA

FROM maven:3.8.4-openjdk-17-slim AS builder

WORKDIR /app

# Copiar primeiro o POM pai
COPY pom.xml .
RUN mvn install -N -DskipTests

# Copiar o POM do módulo eureka
COPY eureka-server/pom.xml eureka-server/
RUN mvn -f eureka-server/pom.xml dependency:go-offline

# Copiar o código fonte do módulo eureka
COPY eureka-server/src eureka-server/src

# Construir o projeto
RUN mvn -f eureka-server/pom.xml clean package -DskipTests

# Etapa final
FROM eclipse-temurin:17-jre-focal
WORKDIR /app
COPY --from=builder /app/eureka-server/target/eureka-server-1.0-SNAPSHOT.jar app.jar

EXPOSE 8761
ENTRYPOINT ["java", "-jar", "app.jar"]
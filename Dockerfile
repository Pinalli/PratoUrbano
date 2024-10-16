# Use a imagem oficial do Maven com Java 17 LTS para construir o projeto
FROM maven:3.8.5-openjdk-17-slim AS build

# Define o diretório de trabalho dentro do container
WORKDIR /app

# Copia o arquivo pom.xml e baixa as dependências do Maven
COPY pom.xml .
RUN mvn dependency:go-offline -B

# Copia o código-fonte para dentro do container
COPY src ./src

# Compila o projeto Spring Boot, gerando o JAR final
RUN mvn clean package -DskipTests

# Usar a imagem do OpenJDK para rodar a aplicação
FROM openjdk:17-jdk-slim

# Diretório de trabalho
WORKDIR /app

# Copia o JAR construído no estágio anterior para o runtime
COPY --from=build /app/target/*.jar app.jar

# Expõe a porta que a aplicação Spring Boot usa
EXPOSE 8080

# Comando para iniciar a aplicação Spring Boot
ENTRYPOINT ["java", "-jar", "app.jar"]

# Use uma imagem base do Java
FROM openjdk:11-jre

# Defina o diretório de trabalho
WORKDIR /app

# Copie o arquivo JAR gerado para dentro da imagem
COPY target/eureka.jar eureka.jar

# Comando para iniciar a aplicação
ENTRYPOINT ["java", "-jar", "eureka.jar"]
FROM openjdk:11-jre-slim
VOLUME /tmp
COPY target/gateway.jar app.jar
ENTRYPOINT ["java","-jar","/app.jar"]

COPY --from=builder /app/gateway/target/*.jar /app/gateway.jar
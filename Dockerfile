# Multi-stage build not necessary here; jar is produced by CI or locally via Maven
FROM eclipse-temurin:21-jre-alpine

WORKDIR /app
COPY target/traveldna-1.0.0.jar /app/app.jar

EXPOSE 8080
ENTRYPOINT ["java","-jar","/app/app.jar"]

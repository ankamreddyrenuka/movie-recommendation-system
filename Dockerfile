# Build from source, then copy the generated Spring Boot jar into a small runtime image
FROM maven:3.9.9-eclipse-temurin-21 AS builder
WORKDIR /app

# Copy Maven files first for better layer caching
COPY pom.xml ./
COPY src ./src

RUN mvn clean package -DskipTests

FROM eclipse-temurin:21-jre-alpine
WORKDIR /app

# Copy the generated fat jar (name can vary slightly, so copy the only jar in target)
COPY --from=builder /app/target/*.jar /app/app.jar

EXPOSE 8080
ENTRYPOINT ["java","-jar","/app/app.jar"]


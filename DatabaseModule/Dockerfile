FROM gradle:8.10.2-jdk21 AS builder
WORKDIR /app
COPY build.gradle.kts settings.gradle.kts gradle/wrapper/ ./
RUN gradle dependencies
COPY . .
RUN gradle build -x test

FROM openjdk:21-jdk-slim
WORKDIR /app
COPY --from=builder /app/build/libs/DatabaseModule-*.jar app.jar
ENTRYPOINT ["java", "-jar", "app.jar"]

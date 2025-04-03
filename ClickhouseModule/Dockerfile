FROM gradle:8.10.2-jdk21 AS builder
WORKDIR /app
COPY . .
RUN gradle build --no-daemon

FROM openjdk:21-jdk-slim
WORKDIR /app
COPY --from=builder /app/build/libs/clickhouseModule-*.jar app.jar
ENTRYPOINT ["java", "-jar", "app.jar"]

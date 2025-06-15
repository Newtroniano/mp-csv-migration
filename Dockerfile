FROM maven:3.8.6-eclipse-temurin-17 AS builder

WORKDIR /app

COPY pom.xml .

RUN mvn dependency:go-offline || mvn dependency:resolve

COPY src ./src

RUN mvn package -DskipTests

FROM eclipse-temurin:17-jre-jammy

WORKDIR /app

COPY --from=builder /app/target/luis-*.jar app.jar

EXPOSE 8080
ENTRYPOINT ["java", "-jar", "app.jar"]
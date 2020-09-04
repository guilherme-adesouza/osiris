# builder
FROM maven:3.6.3-openjdk-14 as builder

WORKDIR /app

COPY pom.xml .

RUN mvn dependency:go-offline

COPY src ./src/

RUN mvn package -Dspring.flyway.enabled=false


# production
FROM openjdk:14-jdk-alpine

ARG JAR_FILE=/app/target/*.jar

COPY --from=builder ${JAR_FILE} app.jar

ENTRYPOINT ["java","-jar","/app.jar"]
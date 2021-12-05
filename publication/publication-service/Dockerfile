FROM openjdk:12-jdk-alpine

ARG JAR_FILE=target/*.jar

ENV MONGO_INITDB_ROOT_USERNAME=root
ENV MONGO_INITDB_ROOT_PASSWORD=example

EXPOSE 8090

WORKDIR /app

COPY ${JAR_FILE} app.jar

ENTRYPOINT ["java", "", "-jar", "app.jar"]

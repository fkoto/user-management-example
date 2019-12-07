FROM adoptopenjdk/openjdk11:latest

LABEL maintainer="foivos"
WORKDIR /app

COPY config /app/config
COPY target/*.jar /app/app.jar
ENTRYPOINT ["java", "-jar","app.jar"]

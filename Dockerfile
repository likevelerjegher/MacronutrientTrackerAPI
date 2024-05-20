#FROM ubuntu:latest
FROM openjdk:21

EXPOSE 8080
LABEL maintainer="likevelerjegher"
ADD target/kaloriinnhold-0.0.1-SNAPSHOT.jar kaloriinnhold.jar

ENTRYPOINT ["java", "-jar", "kaloriinnhold.jar"]
FROM openjdk:21

COPY target/kaloriinnhold-0.0.1-SNAPSHOT.jar kaloriinnhold.jar

ENTRYPOINT ["java", "-jar", "/kaloriinnhold.jar"]
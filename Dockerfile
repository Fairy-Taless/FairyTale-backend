FROM openjdk:17
COPY ./build/libs/TBD-0.0.1-SNAPSHOT.jar fairytale.jar
ENTRYPOINT ["java", "-jar", "fairytale.jar"]

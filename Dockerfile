FROM openjdk:17
COPY ./build/libs/fairytale-0.0.1-SNAPSHOT.jar fairytale.jar
ENTRYPOINT ["java", "-jar", "fairytale.jar"]

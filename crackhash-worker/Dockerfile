FROM openjdk:11
COPY build/libs/app.jar worker-app.jar
ENTRYPOINT ["java", "-jar", "worker-app.jar"]
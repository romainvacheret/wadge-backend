FROM openjdk:11
VOLUME /tmp
COPY build/libs/*.jar app.jar
COPY *.json .
ENTRYPOINT ["java", "-jar", "/app.jar"]
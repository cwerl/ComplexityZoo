FROM openjdk:17-jdk-alpine
VOLUME /tmp
COPY build/libs/complexityzoo-0.0.1-SNAPSHOT.jar build/libs/app.jar
COPY build/resources/ build/resources/
EXPOSE 8080
ENTRYPOINT ["java","-jar","build/libs/app.jar"]
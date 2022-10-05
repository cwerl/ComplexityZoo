FROM openjdk:17-jdk-alpine
VOLUME /tmp
COPY build/libs/complexityzoo-0.1.1-SNAPSHOT.jar build/libs/app.jar
COPY build/resources/ build/resources/
COPY database.mv.db database.mv.db
EXPOSE 8080
ENTRYPOINT ["java","-jar","build/libs/app.jar"]
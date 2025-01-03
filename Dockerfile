FROM openjdk:8-jdk-alpine
LABEL maintainer="dongchangzuo@qq.com"
WORKDIR /app
COPY target/hf-homework-1.0-SNAPSHOT.jar hf-homework-1.0-SNAPSHOT.jar
EXPOSE 8080
ENTRYPOINT ["java", "-jar", "hf-homework-1.0-SNAPSHOT.jar"]


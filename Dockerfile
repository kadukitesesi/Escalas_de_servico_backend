FROM ubuntu:latest AS build

WORKDIR /app

RUN apt-get update && apt-get install -y openjdk-17-jdk maven

COPY . .

RUN mvn clean install

FROM openjdk:17-jdk-slim

WORKDIR /app

EXPOSE 8081

COPY --from=build /app/target/*.jar app.jar

ENTRYPOINT [ "java", "-jar", "app.jar" ]

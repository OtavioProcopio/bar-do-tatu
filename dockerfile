FROM openjdk:21-jdk-slim AS build 

RUN apt-get update && apt-get install -y maven

WORKDIR /app 

COPY pom.xml .

RUN mvn dependency:go-offline

COPY src ./src 

RUN mvn clean package -DskipTests

FROM openjdk:21-jdk-slim

WORKDIR /app

COPY --from=build /app/target/backend-0.0.1-SNAPSHOT.jar /app/app.jar

EXPOSE 8080

ENTRYPOINT [ "java", "-jar", "app.jar" ]
<<<<<<< HEAD
FROM maven:3.8.5-openjdk-17 AS build
=======
FROM maven:3.9.1-openjdk-17 AS build
>>>>>>> a57d7d2 (Added logout)
COPY . .
RUN mvn clean package -DskitTests

FROM openjdk:17.0.1-jdk-slim
COPY --from=build /target/demo-0.0.1-SNAPSHOT.jar demo.jar
EXPOSE 8080
<<<<<<< HEAD
ENTRYPOINT ["java", "-jar", "demo.jar"]
=======
ENTRYPOINT ["java", "-jar", "demo.jar"]
>>>>>>> a57d7d2 (Added logout)
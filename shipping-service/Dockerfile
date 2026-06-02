# Build stage
FROM gradle:8.7-jdk21 AS build
WORKDIR /home/gradle/src
COPY --chown=gradle:gradle . .
RUN gradle :shipping-service:build -x test

# Package stage
FROM eclipse-temurin:21-jre-jammy
COPY --from=build /home/gradle/src/shipping-service/build/libs/*.jar app.jar
EXPOSE 8083
ENTRYPOINT ["java", "-jar", "/app.jar"]

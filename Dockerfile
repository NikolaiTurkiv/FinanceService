FROM gradle:8.0-jdk17 AS build
COPY . /home/gradle/project
WORKDIR /home/gradle/project
RUN gradle build -x test

FROM eclipse-temurin:17-jre
COPY --from=build /home/gradle/project/build/libs/*.jar app.jar
EXPOSE 8080
CMD ["java", "-jar", "app.jar"]
FROM gradle:8.0-jdk17 AS build
WORKDIR /home/gradle/project
COPY . .
RUN gradle shadowJar -x test --no-daemon

FROM eclipse-temurin:17-jre
WORKDIR /app
COPY --from=build /home/gradle/project/build/libs/*-all.jar app.jar
EXPOSE 8080
CMD ["java", "-jar", "app.jar"]
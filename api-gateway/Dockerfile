FROM gradle:jdk21 AS build

WORKDIR /app

COPY gradlew .
COPY gradle gradle/
COPY build.gradle.kts .

COPY src src/

RUN ./gradlew build -x test

FROM bellsoft/liberica-openjdk-alpine:21

WORKDIR /app

COPY --from=build /app/build/libs/*.jar /app/app.jar

EXPOSE 8080

ENTRYPOINT ["java", "-jar", "app.jar"]
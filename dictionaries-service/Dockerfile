FROM bellsoft/liberica-openjdk-alpine:21 as jdk

ENV APP_HOME=/app
WORKDIR $APP_HOME
COPY . .

RUN chmod +x ./gradlew && ./gradlew build


FROM bellsoft/liberica-openjdk-alpine:21
COPY --from=jdk /app/build/libs/dictionaries-service.jar /app.jar
CMD ["java", "-jar", "/app.jar"]
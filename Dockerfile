FROM eclipse-temurin:23-jdk

WORKDIR /app

COPY . .

RUN ./gradlew build -x test

EXPOSE 8080

CMD ["java", "-jar", "build/libs/coreJpa-0.0.1-SNAPSHOT.jar"]
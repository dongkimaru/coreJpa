FROM eclipse-temurin:21-jdk

WORKDIR /app

COPY . .

# ⭐ Windows 줄바꿈 → Linux 변환
RUN apt-get update && apt-get install -y dos2unix
RUN dos2unix gradlew

# ⭐ 실행권한
RUN chmod +x gradlew

# ⭐ build
RUN ./gradlew build -x test

EXPOSE 8080

CMD ["sh","-c","java -jar build/libs/*.jar"]
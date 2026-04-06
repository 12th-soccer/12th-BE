FROM eclipse-temurin:17-jdk

WORKDIR /app

COPY build/libs/BE-12th-0.0.1-SNAPSHOT.jar app.jar

ENTRYPOINT ["java", "-jar", "app.jar"]
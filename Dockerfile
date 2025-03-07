FROM eclipse-temurin:23-jre-alpine

COPY target/internship-0.0.1-SNAPSHOT.jar /app.jar

ENTRYPOINT ["java", "-jar", "/app.jar"]\

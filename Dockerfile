FROM ghcr.io/graalvm/graalvm-ce:latest AS build
WORKDIR /app
COPY . .
RUN gu install native-image
RUN mvn package -Pnative -DskipTests

FROM alpine:latest
WORKDIR /app
COPY --from=build /app/target/*.jar app.jar
EXPOSE 8080
ENTRYPOINT ["java", "-jar", "app.jar"]

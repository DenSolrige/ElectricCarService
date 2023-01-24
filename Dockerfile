FROM gradle:7.6.0-jdk19-alpine as build
WORKDIR /app
COPY . .
RUN gradle build -x test

FROM openjdk:19-jdk-alpine
EXPOSE 8080
COPY --from=build /app/build/libs/ElectricCarService-0.0.1-SNAPSHOT.jar /app/app.jar
WORKDIR /app
ENTRYPOINT ["java","-jar","app.jar"]
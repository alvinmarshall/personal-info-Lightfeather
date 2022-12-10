FROM maven:3.6.3-adoptopenjdk-14 AS build
COPY src /app/src
COPY pom.xml /app
RUN mvn -f /app/pom.xml -DskipTests clean package

FROM adoptopenjdk/openjdk14
EXPOSE 8080
COPY --from=build /app/target/*.jar /app.jar
ENTRYPOINT ["java","-jar","/app.jar"]

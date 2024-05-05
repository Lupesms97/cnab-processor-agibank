FROM maven:3.8.2-amazoncorretto-17 AS build
RUN mkdir -p /usr/src/app
WORKDIR /usr/src/app
ADD . /usr/src/app
RUN mvn clean package -DskipTests

FROM amazoncorretto:17
EXPOSE 8080
RUN mkdir -p /usr/src/app
WORKDIR /usr/src/app
COPY --from=build /usr/src/app/target/*.jar app.jar
CMD ["java", "-jar", "app.jar"]


FROM openjdk:17
EXPOSE 8080

ARG API_KEY
ENV OPENWEATHERMAP_API_KEY=$API_KEY

ADD target/weather-microservice.jar weather-microservice.jar
ENTRYPOINT ["java", "-jar", "/weather-microservice.jar"]

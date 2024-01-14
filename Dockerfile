FROM openjdk:8
EXPOSE 8080
ADD target/weather-microservice.jar weather-microservice.jar
ENTRYPOINT ["java","-jar","/weather-microservice.jar"]
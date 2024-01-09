package publicis.sapient.weathermicroservice.controller;


import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;
import publicis.sapient.weathermicroservice.domain.WeatherResponse;

@RestController
public interface WeatherController {
    ResponseEntity<WeatherResponse> getWeatherForecast(String cityName);
}
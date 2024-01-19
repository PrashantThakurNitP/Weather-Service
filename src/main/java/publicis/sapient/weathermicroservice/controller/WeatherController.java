package publicis.sapient.weathermicroservice.controller;


import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;
import publicis.sapient.weathermicroservice.domain.WeatherResponse;

import java.util.List;

@RestController
public interface WeatherController {
    ResponseEntity<List<WeatherResponse>> getWeatherForecast(String cityName,int days);
}
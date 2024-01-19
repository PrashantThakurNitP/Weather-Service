package publicis.sapient.weathermicroservice.controller;


import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import publicis.sapient.weathermicroservice.domain.WeatherRequest;
import publicis.sapient.weathermicroservice.domain.WeatherResponse;
import publicis.sapient.weathermicroservice.service.WeatherService;

import java.util.List;

@RestController
@RequestMapping("/v1")
@Slf4j
@CrossOrigin(origins = "*")
public class WeatherControllerImpl implements WeatherController {

    @Autowired
    private WeatherService weatherService;

    @ResponseStatus(HttpStatus.OK)
    @GetMapping(value = "/weather", produces = "application/json")
    public ResponseEntity<List<WeatherResponse>> getWeatherForecast(@RequestParam String cityName) {
        log.info("Get weather request for city : {}",cityName);
        WeatherRequest weatherRequest = new WeatherRequest();
        weatherRequest.setCity(cityName);
        return new ResponseEntity<>(weatherService.getWeatherForCity(weatherRequest), HttpStatus.OK);
    }
}
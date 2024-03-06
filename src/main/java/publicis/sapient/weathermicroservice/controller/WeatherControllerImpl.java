package publicis.sapient.weathermicroservice.controller;


//import io.swagger.annotations.ApiOperation;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import publicis.sapient.weathermicroservice.domain.WeatherRequest;
import publicis.sapient.weathermicroservice.domain.response.WeatherResponse;
import publicis.sapient.weathermicroservice.exception.InternalServerError;
import publicis.sapient.weathermicroservice.exception.NotFoundException;
import publicis.sapient.weathermicroservice.exception.UnAuthorizedException;
import publicis.sapient.weathermicroservice.service.WeatherService;
import reactor.core.publisher.Flux;

import java.util.List;

import static publicis.sapient.weathermicroservice.utils.Constants.CIRCUIT_BREAKER_NAME;

@RestController
@RequestMapping("/v1")
@Slf4j
@CrossOrigin(origins = "*")
public class WeatherControllerImpl implements WeatherController {

    @Autowired
    private WeatherService weatherService;

    @ResponseStatus(HttpStatus.OK)
    @GetMapping(value = "/weather", produces = "application/json")
    //@ApiOperation(value= "API to service Weather Forecast for a City")
    @CircuitBreaker(name = CIRCUIT_BREAKER_NAME, fallbackMethod = "fallbackGetWeatherForecast")
    public ResponseEntity<List<WeatherResponse>> getWeatherForecast(@RequestParam String cityName,@RequestParam int days) throws NotFoundException, UnAuthorizedException, InternalServerError {
        log.info("Get weather forecast  for city : {} days : {}",cityName,days);
        WeatherRequest weatherRequest = new WeatherRequest();
        weatherRequest.setCity(cityName);
        weatherRequest.setDays(days);
        return new ResponseEntity<>(weatherService.getWeatherForCity(weatherRequest), HttpStatus.OK);
    }
    public ResponseEntity<Flux<WeatherResponse>> fallbackGetWeatherForecast(String cityName, int days, Exception e) {
        log.error("Circuit breaker triggered for getWeatherForecast: {}", e.getMessage());
        // Return a default response or handle the fallback logic here
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
    }
}
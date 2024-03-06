package publicis.sapient.weathermicroservice.controller;


//import io.swagger.annotations.ApiOperation;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
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
@Tag(name = "Weather Controller")
public class WeatherControllerImpl implements WeatherController {

    @Autowired
    private WeatherService weatherService;

    @ResponseStatus(HttpStatus.OK)
    @GetMapping(value = "/weather", produces = "application/json")
    //@ApiOperation(value= "API to service Weather Forecast for a City")
    @Operation(summary= "API to service Weather Forecast for a City")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200",description = "Success"),
            @ApiResponse(responseCode = "404",description = "Not Found"),
            @ApiResponse(responseCode = "500",description = "Internal Server Error"),
            @ApiResponse(responseCode = "401",description = "UnAuthorized"),
    })
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
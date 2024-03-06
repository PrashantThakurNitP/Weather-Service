package publicis.sapient.weathermicroservice.controller;

import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
//import io.swagger.annotations.ApiOperation;
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
import publicis.sapient.weathermicroservice.service.WeatherForecastService;
import reactor.core.publisher.Flux;



@RestController
@RequestMapping("/v2")
@Slf4j
@CrossOrigin(origins = "*")
@Tag(name = "Weather Controller Reactive")
public class WeatherForecastControllerImpl implements WeatherForecastController{

    @Autowired
    private WeatherForecastService weatherService;


    @ResponseStatus(HttpStatus.OK)
    @GetMapping(value = "/weather", produces = "application/json")
    //@ApiOperation(value= "Non Blocking API to service Weather Forecast for a City")
    @Operation(summary="Non Blocking API to service Weather Forecast for a City")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200",description = "Success"),
            @ApiResponse(responseCode = "404",description = "Not Found"),
            @ApiResponse(responseCode = "500",description = "Internal Server Error"),
            @ApiResponse(responseCode = "401",description = "UnAuthorized"),
    })
    public ResponseEntity<Flux<WeatherResponse>> getWeatherForecastFlux(@RequestParam String cityName, @RequestParam int days) throws NotFoundException, UnAuthorizedException, InternalServerError {
        log.info("getting weather forecast flux for city : {} days : {} ",cityName,days);
        WeatherRequest weatherRequest = new WeatherRequest();
        weatherRequest.setCity(cityName);
           weatherRequest.setDays(days);
        return new ResponseEntity<>(weatherService.getWeatherForCityNonBlocking(weatherRequest), HttpStatus.OK);
    }
}

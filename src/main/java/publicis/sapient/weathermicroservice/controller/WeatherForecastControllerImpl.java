package publicis.sapient.weathermicroservice.controller;

import io.swagger.annotations.ApiOperation;
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
public class WeatherForecastControllerImpl implements WeatherForecastController{

    @Autowired
    private WeatherForecastService weatherService;


    @ResponseStatus(HttpStatus.OK)
    @GetMapping(value = "/weather", produces = "application/json")
    @ApiOperation(value= "Non Blocking API to service Weather Forecast for a City")
    public ResponseEntity<Flux<WeatherResponse>> getWeatherForecastFlux(@RequestParam String cityName, @RequestParam int days) throws NotFoundException, UnAuthorizedException, InternalServerError {
        log.info("getting weather forecast flux for city : {} days : {} ",cityName,days);
        WeatherRequest weatherRequest = new WeatherRequest();
        weatherRequest.setCity(cityName);
        weatherRequest.setDays(days);
        return new ResponseEntity<>(weatherService.getWeatherForCityNonBlocking(weatherRequest), HttpStatus.OK);
    }
}

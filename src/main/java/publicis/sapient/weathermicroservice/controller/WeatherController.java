package publicis.sapient.weathermicroservice.controller;


import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;
import publicis.sapient.weathermicroservice.domain.response.WeatherResponse;
import publicis.sapient.weathermicroservice.exception.InternalServerError;
import publicis.sapient.weathermicroservice.exception.NotFoundException;
import publicis.sapient.weathermicroservice.exception.UnAuthorizedException;

import java.util.List;

@RestController
public interface WeatherController {
    ResponseEntity<List<WeatherResponse>> getWeatherForecast(String cityName,int days) throws NotFoundException, UnAuthorizedException, InternalServerError;
}
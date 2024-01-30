package publicis.sapient.weathermicroservice.controller;

import org.springframework.http.ResponseEntity;
import publicis.sapient.weathermicroservice.domain.response.WeatherResponse;
import publicis.sapient.weathermicroservice.exception.InternalServerError;
import publicis.sapient.weathermicroservice.exception.NotFoundException;
import publicis.sapient.weathermicroservice.exception.UnAuthorizedException;
import reactor.core.publisher.Flux;

public interface WeatherForecastController {
    ResponseEntity<Flux<WeatherResponse>> getWeatherForecastFlux(String cityName, int days) throws NotFoundException, UnAuthorizedException, InternalServerError;
}

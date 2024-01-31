package publicis.sapient.weathermicroservice.service;

import org.springframework.stereotype.Service;
import publicis.sapient.weathermicroservice.domain.WeatherRequest;
import publicis.sapient.weathermicroservice.domain.response.WeatherResponse;
import publicis.sapient.weathermicroservice.exception.InternalServerError;
import publicis.sapient.weathermicroservice.exception.NotFoundException;
import publicis.sapient.weathermicroservice.exception.UnAuthorizedException;
import reactor.core.publisher.Flux;
@Service
public interface WeatherForecastService {
    Flux<WeatherResponse> getWeatherForCityNonBlocking(WeatherRequest weatherRequest) throws NotFoundException, UnAuthorizedException, InternalServerError;
}

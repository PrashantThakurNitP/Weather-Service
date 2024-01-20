package publicis.sapient.weathermicroservice.service;
import org.springframework.stereotype.Service;
import publicis.sapient.weathermicroservice.domain.WeatherRequest;
import publicis.sapient.weathermicroservice.domain.WeatherResponse;
import publicis.sapient.weathermicroservice.exception.InternalServerError;
import publicis.sapient.weathermicroservice.exception.NotFoundException;
import publicis.sapient.weathermicroservice.exception.UnAuthorizedException;

import java.util.List;



@Service
public interface WeatherService {
    List<WeatherResponse> getWeatherForCity(WeatherRequest weatherRequest) throws NotFoundException, UnAuthorizedException, InternalServerError;
}

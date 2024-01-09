package publicis.sapient.weathermicroservice.service;


import org.springframework.stereotype.Service;
import publicis.sapient.weathermicroservice.domain.WeatherRequest;
import publicis.sapient.weathermicroservice.domain.WeatherResponse;


@Service
public interface WeatherService {
    WeatherResponse getWeatherForCity(WeatherRequest weatherRequest);
}

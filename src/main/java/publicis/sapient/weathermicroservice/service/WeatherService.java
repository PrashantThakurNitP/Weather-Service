package publicis.sapient.weathermicroservice.service;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import publicis.sapient.weathermicroservice.domain.WeatherRequest;
import publicis.sapient.weathermicroservice.domain.WeatherResponse;

import java.util.List;



@Service
public interface WeatherService {
    List<WeatherResponse> getWeatherForCity(WeatherRequest weatherRequest);
}

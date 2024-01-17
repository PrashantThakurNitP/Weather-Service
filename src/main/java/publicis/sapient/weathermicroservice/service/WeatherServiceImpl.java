package publicis.sapient.weathermicroservice.service;


import org.springframework.stereotype.Service;
import publicis.sapient.weathermicroservice.domain.DailyWeather;
import publicis.sapient.weathermicroservice.domain.WeatherRequest;
import publicis.sapient.weathermicroservice.domain.WeatherResponse;

import java.util.List;

@Service
public class WeatherServiceImpl implements WeatherService {

    @Override
    public List<WeatherResponse> getWeatherForCity(WeatherRequest weatherRequest) {
        // return null;
        WeatherResponse weather1 = WeatherResponse.builder().dailyWeathers(DailyWeather.builder().minTemperature(15.5).maxTemperature(30.5).day("Wednesday").build()).message("Carry umbrella").build();
        WeatherResponse weather2 = WeatherResponse.builder().dailyWeathers(DailyWeather.builder().minTemperature(15.5).maxTemperature(30.5).day("Wednesday").build()).message("Carry umbrella").build();
        WeatherResponse weather3 = WeatherResponse.builder().dailyWeathers(DailyWeather.builder().minTemperature(15.5).maxTemperature(30.5).day("Wednesday").build()).message("Carry umbrella").build();
        return List.of(weather1, weather2, weather3);
    }
}

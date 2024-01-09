package publicis.sapient.weathermicroservice.service;


import org.springframework.stereotype.Service;
import publicis.sapient.weathermicroservice.domain.DailyWeather;
import publicis.sapient.weathermicroservice.domain.WeatherRequest;
import publicis.sapient.weathermicroservice.domain.WeatherResponse;

@Service
public class WeatherServiceImpl implements WeatherService {

    @Override
    public WeatherResponse getWeatherForCity(WeatherRequest weatherRequest) {
        // return null;
        DailyWeather dailyWeather1 = DailyWeather.builder().minTemperature(15.5).maxTemperature(30.5).day("Wednesday").build();
        DailyWeather dailyWeather2 = DailyWeather.builder().minTemperature(15.5).maxTemperature(30.5).day("Wednesday").build();
        DailyWeather dailyWeather3 = DailyWeather.builder().minTemperature(15.5).maxTemperature(30.5).day("Wednesday").build();
        DailyWeather[] dailyWeathers = new DailyWeather[]{dailyWeather1, dailyWeather2, dailyWeather3};
        return WeatherResponse.builder().message("Carry Umbrella").dailyWeathers(dailyWeathers).build();
    }
}

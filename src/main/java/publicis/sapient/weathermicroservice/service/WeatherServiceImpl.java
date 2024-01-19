package publicis.sapient.weathermicroservice.service;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import publicis.sapient.weathermicroservice.domain.DailyWeather;
import publicis.sapient.weathermicroservice.domain.WeatherApiResponse;
import publicis.sapient.weathermicroservice.domain.WeatherRequest;
import publicis.sapient.weathermicroservice.domain.WeatherResponse;
import publicis.sapient.weathermicroservice.utils.WeatherApiCall;

import java.util.ArrayList;
import java.util.List;

@Service
public class WeatherServiceImpl implements WeatherService {
    @Autowired
    WeatherApiCall weatherApiCall;

    @Override
    public List<WeatherResponse> getWeatherForCity(WeatherRequest weatherRequest) {
        int count = weatherRequest.getDays()*8;
        WeatherApiResponse response = weatherApiCall.getWeatherInfo(weatherRequest.getCity(),count );
        List<WeatherResponse> weatherResponses = new ArrayList<>();
        for (int i = 0; i < count; i++) {
            String message = getSuggestionForWeather(response, i);
            WeatherResponse weather = WeatherResponse.builder().dailyWeathers(DailyWeather.builder().minTemperature(response.getList().get(i).getMain().getTemp_min()-273)
                    .maxTemperature(response.getList().get(i).getMain().getTemp_max()-273)
                    .date(response.getList().get(i).getDt_txt().split(" ")[0])
                            .time(response.getList().get(i).getDt_txt().split(" ")[1]).build())
                    .message(message)
             .icon(response.getList().get(i).getWeather().get(0).getIcon())
                    .description(response.getList().get(i).getWeather().get(0).getDescription())
                    .build();
            weatherResponses.add(weather);
        }
        return weatherResponses;
    }

    private static String getSuggestionForWeather(WeatherApiResponse response, int i) {
        String message = "Nice Weather";
        if (response.getList().get(i).getMain().getTemp() > 313) {
            message = "Use sunscreen lotion";
        } else if (response.getList().get(i).getWeather().get(0).getId() >= 500 && response.getList().get(i).getWeather().get(0).getId() < 600) {
            message = "Carry umbrella";
        } else if (response.getList().get(i).getWind().getSpeed() > 10) {
            message = "It’s too windy, watch out!";
        } else if (response.getList().get(i).getWeather().get(0).getId() >= 200 && response.getList().get(i).getWeather().get(0).getId() < 300) {
            message = "Don’t step out! A Storm is brewing!";
        }
        else if (response.getList().get(i).getMain().getTemp() <283) {
            message = "Chilling Outside!";
        }
        return message;
    }
}

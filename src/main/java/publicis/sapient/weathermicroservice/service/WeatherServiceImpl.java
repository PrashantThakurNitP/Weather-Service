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
    private WeatherApiCall weatherApiCall;

    @Override
    public List<WeatherResponse> getWeatherForCity(WeatherRequest weatherRequest) {
        int count = weatherRequest.getDays() * 8;
        WeatherApiResponse response = weatherApiCall.getWeatherInfo(weatherRequest.getCity(), count);
        return mapWeatherResponse(response, count);
    }

    private List<WeatherResponse> mapWeatherResponse(WeatherApiResponse response, int count) {
        List<WeatherResponse> weatherResponses = new ArrayList<>();
        for (int i = 0; i < count; i++) {
            DailyWeather dailyWeather = createDailyWeather(response, i);
            String message = getSuggestionForWeather(response, i);
            WeatherResponse weather = WeatherResponse.builder()
                    .dailyWeathers(dailyWeather)
                    .message(message)
                    .icon(response.getList().get(i).getWeather().get(0).getIcon())
                    .description(response.getList().get(i).getWeather().get(0).getDescription())
                    .build();
            weatherResponses.add(weather);
        }
        return weatherResponses;
    }

    private DailyWeather createDailyWeather(WeatherApiResponse response, int i) {
        return DailyWeather.builder()
                .minTemperature(response.getList().get(i).getMain().getTemp_min() - 273)
                .maxTemperature(response.getList().get(i).getMain().getTemp_max() - 273)
                .date(response.getList().get(i).getDt_txt().split(" ")[0])
                .time(response.getList().get(i).getDt_txt().split(" ")[1])
                .build();
    }

    private static String getSuggestionForWeather(WeatherApiResponse response, int i) {
        double temperature = response.getList().get(i).getMain().getTemp();
        int weatherId = response.getList().get(i).getWeather().get(0).getId();
        double windSpeed = response.getList().get(i).getWind().getSpeed();

        if (temperature > 313) {
            return "Use sunscreen lotion";
        } else if (weatherId >= 500 && weatherId < 600) {
            return "Carry umbrella";
        } else if (windSpeed > 10) {
            return "It’s too windy, watch out!";
        } else if (weatherId >= 200 && weatherId < 300) {
            return "Don’t step out! A Storm is brewing!";
        } else if (temperature < 283) {
            return "Chilling Outside!";
        } else {
            return "Nice Weather";
        }
    }
}
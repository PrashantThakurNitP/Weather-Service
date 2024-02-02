package publicis.sapient.weathermicroservice.utils;

import publicis.sapient.weathermicroservice.domain.WeatherData;
import publicis.sapient.weathermicroservice.domain.response.DailyWeather;

import static publicis.sapient.weathermicroservice.utils.Constants.*;

public class Utility {
    public static String getSuggestionForWeather(WeatherData weatherData) {
        double temperature = weatherData.getMain().getTemp();
        int weatherId = !weatherData.getWeather().isEmpty() ? weatherData.getWeather().get(0).getId():-1;
        double windSpeed = weatherData.getWind().getSpeed();
        int humidity = weatherData.getMain().getHumidity();
        int visibility = weatherData.getVisibility();

        if (isSuperHot.apply(temperature)) {
            return MESSAGE_FOR_HIGH_TEMP;
        } else if (isRainy.apply(weatherId)) {
            return MESSAGE_FOR_RAIN;
        } else if (isHighWind.apply(windSpeed)) {
            return MESSAGE_FOR_HIGH_WINDS;
        } else if (isThunderstorm.apply(weatherId)) {
            return MESSAGE_FOR_THUNDERSTORM;
        } else if (isChilling.apply(temperature)) {
            return MESSAGE_FOR_LOW_TEMP;
        }
        else if (isSlightlyCold.apply(temperature)) {
            return MESSAGE_FOR_SLIGHT_LOW_TEMP;
        }
        else if (isVisibilityLow.apply(visibility)) {
            return MESSAGE_FOR_SLIGHT_LOW_VISIBILITY;
        }
        else if (isHumid.apply(humidity)) {
            return MESSAGE_FOR_HUMIDITY;
        }else {
            return MESSAGE_FOR_NICE_WEATHER;
        }
    }

    public static DailyWeather createDailyWeather(WeatherData weatherData) {
        return DailyWeather.builder()
                .minTemperature(kelvinToCelsius.apply(weatherData.getMain().getTemp_min()))
                .maxTemperature(kelvinToCelsius.apply(weatherData.getMain().getTemp_max()))
                .temperature(kelvinToCelsius.apply(weatherData.getMain().getTemp()))
                .windSpeed(weatherData.getWind().getSpeed())
                .date(extractDate.apply(weatherData))
                .time(extractTime.apply(weatherData))
                .humidity(weatherData.getMain().getHumidity())
                .pressure(weatherData.getMain().getPressure())
                .feelsLike(kelvinToCelsius.apply(weatherData.getMain().getFeels_like()))
                .visibility(weatherData.getVisibility())
                .build();
    }
}

package publicis.sapient.weathermicroservice.utils;

import publicis.sapient.weathermicroservice.domain.WeatherData;

import java.util.function.Function;

public class Constants {
    public static final String MESSAGE_FOR_HIGH_TEMP="Use sunscreen lotion";
    public static final String MESSAGE_FOR_LOW_TEMP="Chilling Outside!";
    public static final String MESSAGE_FOR_HIGH_WINDS="It’s too windy, watch out!";
    public static final String MESSAGE_FOR_THUNDERSTORM="Don’t step out! A Storm is brewing!";
    public static final String MESSAGE_FOR_RAIN="Carry umbrella";

    public static final String MESSAGE_FOR_NICE_WEATHER="Nice Weather";
    public static final String MESSAGE_FOR_SLIGHT_LOW_TEMP="Cold Outside";
    public static final String MESSAGE_FOR_SLIGHT_LOW_VISIBILITY="Low Visibility";
    public static final String MESSAGE_FOR_HUMIDITY="Humid";
    public static final String QUERY_PARAM_CITY = "q";
    public static final String QUERY_PARAM_QTY = "cnt";
    public static final String QUERY_PARAM_APP_ID = "appid";
    public static Function<Integer, Integer> kelvinToCelsius = kelvin -> kelvin - 273;
    public static Function<Integer, Boolean> isThunderstorm = weatherId -> weatherId >= 200 && weatherId < 300;
    public static Function<Integer, Boolean> isRainy = weatherId -> weatherId >= 500 && weatherId < 600;
    public static Function<Double, Boolean> isSuperHot = temperature -> temperature > 313;
    public static Function<Double, Boolean> isChilling = temperature -> temperature < 283;
    public static Function<Double, Boolean> isSlightlyCold = temperature -> temperature < 288;

    public static Function<Integer, Boolean> isHumid = humidity -> humidity >70;

    public static Function<Integer, Boolean> isVisibilityLow = visibility -> visibility < 100;

    public static Function<Double, Boolean> isHighWind = windSpeed -> windSpeed > 10;

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
}

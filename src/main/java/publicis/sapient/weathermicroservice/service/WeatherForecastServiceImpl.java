package publicis.sapient.weathermicroservice.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClientResponseException;
import publicis.sapient.weathermicroservice.domain.WeatherApiResponse;
import publicis.sapient.weathermicroservice.domain.WeatherData;
import publicis.sapient.weathermicroservice.domain.WeatherRequest;
import publicis.sapient.weathermicroservice.domain.response.DailyWeather;
import publicis.sapient.weathermicroservice.domain.response.WeatherResponse;
import publicis.sapient.weathermicroservice.exception.InternalServerError;
import publicis.sapient.weathermicroservice.exception.NotFoundException;
import publicis.sapient.weathermicroservice.exception.UnAuthorizedException;
import publicis.sapient.weathermicroservice.utils.WebClientWeatherApi;
import reactor.core.publisher.Flux;
import reactor.core.scheduler.Schedulers;


import static publicis.sapient.weathermicroservice.utils.Constants.*;
import static publicis.sapient.weathermicroservice.utils.Constants.MESSAGE_FOR_NICE_WEATHER;

@Service
@Slf4j
public class WeatherForecastServiceImpl implements WeatherForecastService{


    @Autowired
    private WebClientWeatherApi webClientWeatherApi;

    private static String getSuggestionForWeather(WeatherData weatherData) {
        double temperature = weatherData.getMain().getTemp();
        int weatherId = !weatherData.getWeather().isEmpty() ? weatherData.getWeather().get(0).getId() : -1;
        double windSpeed = weatherData.getWind().getSpeed();
        int humidity = weatherData.getMain().getHumidity();
        int visibility = weatherData.getVisibility();

        if (temperature > 313) {
            return MESSAGE_FOR_HIGH_TEMP;
        } else if (weatherId >= 500 && weatherId < 600) {
            return MESSAGE_FOR_RAIN;
        } else if (windSpeed > 10) {
            return MESSAGE_FOR_HIGH_WINDS;
        } else if (weatherId >= 200 && weatherId < 300) {
            return MESSAGE_FOR_THUNDERSTORM;
        } else if (temperature < 283) {
            return MESSAGE_FOR_LOW_TEMP;
        } else if (temperature < 288) {
            return MESSAGE_FOR_SLIGHT_LOW_TEMP;
        } else if (visibility < 100) {
            return MESSAGE_FOR_SLIGHT_LOW_VISIBILITY;
        } else if (humidity > 70) {
            return MESSAGE_FOR_HUMIDITY;
        } else {
            return MESSAGE_FOR_NICE_WEATHER;
        }
    }





    @Override
    public Flux<WeatherResponse> getWeatherForCityNonBlocking(WeatherRequest weatherRequest) throws NotFoundException, UnAuthorizedException, InternalServerError {


        return webClientWeatherApi.fetchDataFromApi(weatherRequest)
                .subscribeOn(Schedulers.boundedElastic())
                .flatMapMany(this::mapWeatherResponseNonBlocking)
                .onErrorMap(throwable -> {
                    if (throwable instanceof WebClientResponseException.NotFound) {
                        log.error("NotFound HttpClientErrorException Exception Occurred", throwable);
                        return new NotFoundException(throwable.getMessage());
                    } else if (throwable instanceof WebClientResponseException.Unauthorized) {
                        log.error("Unauthorized HttpClientErrorException Exception Occurred", throwable);
                        return new UnAuthorizedException("Unable to fetch Weather!");
                    } else {
                        log.error("Exception Occurred", throwable);
                        return new InternalServerError(throwable.getMessage());
                    }
                });


    }


    private Flux<WeatherResponse> mapWeatherResponseNonBlocking(WeatherApiResponse response) {
        return Flux.fromIterable(response.getList()).map(weatherData -> {
            DailyWeather dailyWeather = createDailyWeather(weatherData);
            String message = getSuggestionForWeather(weatherData);
            return WeatherResponse.builder().dailyWeathers(dailyWeather).message(message).weatherType(weatherData.getWeather().get(0).getMain()).icon(weatherData.getWeather().get(0).getIcon()).description(weatherData.getWeather().get(0).getDescription()).timezoneOffset(response.getCity().getTimezone()).build();
        });
    }

    private DailyWeather createDailyWeather(WeatherData weatherData) {
        return DailyWeather.builder().minTemperature(weatherData.getMain().getTemp_min() - 273).maxTemperature(weatherData.getMain().getTemp_max() - 273).temperature(weatherData.getMain().getTemp() - 273).windSpeed(weatherData.getWind().getSpeed()).date(weatherData.getDt_txt().split(" ").length > 1 ? weatherData.getDt_txt().split(" ")[0] : "").time(weatherData.getDt_txt().split(" ").length > 1 ? weatherData.getDt_txt().split(" ")[1] : "").humidity(weatherData.getMain().getHumidity()).pressure(weatherData.getMain().getPressure()).feelsLike(weatherData.getMain().getFeels_like() - 273).visibility(weatherData.getVisibility()).build();
    }

}

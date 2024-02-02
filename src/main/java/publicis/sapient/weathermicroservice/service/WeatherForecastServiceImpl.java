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

@Service
@Slf4j
public class WeatherForecastServiceImpl implements WeatherForecastService{


    @Autowired
    private WebClientWeatherApi webClientWeatherApi;

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
            return WeatherResponse.builder().dailyWeathers(dailyWeather)
                    .message(message)
                    .weatherType(weatherData.getWeather().get(0).getMain())
                    .icon(weatherData.getWeather().get(0).getIcon())
                    .description(weatherData.getWeather().get(0).getDescription())
                    .timezoneOffset(response.getCity().getTimezone())
                    .build();
        });
    }

    private DailyWeather createDailyWeather(WeatherData weatherData) {
        return DailyWeather.builder().minTemperature(kelvinToCelsius.apply(weatherData.getMain().getTemp_min())).maxTemperature(kelvinToCelsius.apply(weatherData.getMain().getTemp_max())).temperature(kelvinToCelsius.apply(weatherData.getMain().getTemp())).windSpeed(weatherData.getWind().getSpeed()).date(weatherData.getDt_txt().split(" ").length > 1 ? weatherData.getDt_txt().split(" ")[0] : "").time(weatherData.getDt_txt().split(" ").length > 1 ? weatherData.getDt_txt().split(" ")[1] : "").humidity(weatherData.getMain().getHumidity()).pressure(weatherData.getMain().getPressure()).feelsLike(kelvinToCelsius.apply(weatherData.getMain().getFeels_like())).visibility(weatherData.getVisibility()).build();
    }

}

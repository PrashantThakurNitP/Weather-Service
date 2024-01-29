package publicis.sapient.weathermicroservice.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import publicis.sapient.weathermicroservice.domain.response.DailyWeather;
import publicis.sapient.weathermicroservice.domain.WeatherApiResponse;
import publicis.sapient.weathermicroservice.domain.WeatherRequest;
import publicis.sapient.weathermicroservice.domain.response.WeatherResponse;
import publicis.sapient.weathermicroservice.exception.InternalServerError;
import publicis.sapient.weathermicroservice.exception.NotFoundException;
import publicis.sapient.weathermicroservice.exception.UnAuthorizedException;
import publicis.sapient.weathermicroservice.utils.WeatherApiCall;

import java.util.ArrayList;
import java.util.List;

import static publicis.sapient.weathermicroservice.utils.Constants.*;

@Service
@Slf4j
public class WeatherServiceImpl implements WeatherService {

    @Autowired
    private WeatherApiCall weatherApiCall;

    @Override
    public List<WeatherResponse> getWeatherForCity(WeatherRequest weatherRequest) throws NotFoundException, UnAuthorizedException, InternalServerError {

        try {
            WeatherApiResponse response = weatherApiCall.getWeatherInfo(weatherRequest);
            return mapWeatherResponse(response);
        }
        catch (HttpClientErrorException.NotFound ex){
            log.error("NotFound HttpClientErrorException Exception Occurred",ex);
            throw new NotFoundException(ex.getMessage());
        }
        catch (HttpClientErrorException.Unauthorized ex){
            log.error("Unauthorized HttpClientErrorException Exception Occurred",ex);
            throw new UnAuthorizedException("Unable to fetch Weather!");
        }
        catch (Exception ex){
            log.error("Exception Occurred",ex);
            throw new InternalServerError(ex.getMessage());
        }
    }

    private List<WeatherResponse> mapWeatherResponse(WeatherApiResponse response) {
        int count = response.getList().size();
        List<WeatherResponse> weatherResponses = new ArrayList<>();
        for (int i = 0; i < count; i++) {
            DailyWeather dailyWeather = createDailyWeather(response, i);
            String message = getSuggestionForWeather(response, i);
            WeatherResponse weather = WeatherResponse.builder()
                    .dailyWeathers(dailyWeather)
                    .message(message)
                    .weatherType(response.getList().get(i).getWeather().get(0).getMain())
                    .icon(response.getList().get(i).getWeather().get(0).getIcon())
                    .description(response.getList().get(i).getWeather().get(0).getDescription())
                    .timezoneOffset(response.getCity().getTimezone())
                    .build();
            weatherResponses.add(weather);
        }
        return weatherResponses;
    }

    private DailyWeather createDailyWeather(WeatherApiResponse response, int i) {
        return DailyWeather.builder()
                .minTemperature(response.getList().get(i).getMain().getTemp_min() - 273)
                .maxTemperature(response.getList().get(i).getMain().getTemp_max() - 273)
                .temperature(response.getList().get(i).getMain().getTemp()-273)
                .windSpeed(response.getList().get(i).getWind().getSpeed())
                .date(response.getList().get(i).getDt_txt().split(" ").length>1?response.getList().get(i).getDt_txt().split(" ")[0]:"")
                .time(response.getList().get(i).getDt_txt().split(" ").length>1?response.getList().get(i).getDt_txt().split(" ")[1]:"")
                .humidity(response.getList().get(i).getMain().getHumidity())
                .pressure(response.getList().get(i).getMain().getPressure())
                .feelsLike(response.getList().get(i).getMain().getFeels_like()-273)
                .visibility(response.getList().get(i).getVisibility())
                .build();
    }

    private static String getSuggestionForWeather(WeatherApiResponse response, int i) {
        double temperature = response.getList().get(i).getMain().getTemp();
        int weatherId = !response.getList().get(i).getWeather().isEmpty() ? response.getList().get(i).getWeather().get(0).getId():-1;
        double windSpeed = response.getList().get(i).getWind().getSpeed();
        int humidity = response.getList().get(i).getMain().getHumidity();
        int visibility = response.getList().get(i).getVisibility();

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
        }
        else if (temperature < 288) {
            return MESSAGE_FOR_SLIGHT_LOW_TEMP;
        }
        else if (visibility < 100) {
            return MESSAGE_FOR_SLIGHT_LOW_VISIBILITY;
        }
        else if (humidity >70) {
            return MESSAGE_FOR_HUMIDITY;
        }else {
            return MESSAGE_FOR_NICE_WEATHER;
        }
    }
}
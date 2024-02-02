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

import java.util.List;
import java.util.stream.Collectors;

import static publicis.sapient.weathermicroservice.utils.Utility.getSuggestionForWeather;
import static publicis.sapient.weathermicroservice.utils.Utility.createDailyWeather;

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
        return response.getList().stream().map(weatherItem->{
            DailyWeather dailyWeather = createDailyWeather(weatherItem);
            String message = getSuggestionForWeather(weatherItem);
            return WeatherResponse.builder()
                    .dailyWeathers(dailyWeather)
                    .message(message)
                    .weatherType(weatherItem.getWeather().get(0).getMain())
                    .icon(weatherItem.getWeather().get(0).getIcon())
                    .description(weatherItem.getWeather().get(0).getDescription())
                    .timezoneOffset(response.getCity().getTimezone())
                    .build();
        }).collect(Collectors.toList());
    }


}
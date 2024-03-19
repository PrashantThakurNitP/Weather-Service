package publicis.sapient.weathermicroservice.controller;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import publicis.sapient.weathermicroservice.domain.WeatherRequest;
import publicis.sapient.weathermicroservice.domain.response.DailyWeather;
import publicis.sapient.weathermicroservice.domain.response.WeatherResponse;
import publicis.sapient.weathermicroservice.exception.InternalServerError;
import publicis.sapient.weathermicroservice.exception.NotFoundException;
import publicis.sapient.weathermicroservice.exception.UnAuthorizedException;
import publicis.sapient.weathermicroservice.service.WeatherForecastServiceImpl;
import reactor.core.publisher.Flux;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;
@RunWith(SpringJUnit4ClassRunner.class)
public class WeatherForecastControllerImplTest {
    @Mock
    private WeatherForecastServiceImpl weatherService;

    @InjectMocks
    private WeatherForecastControllerImpl weatherController;

    @Test
    public void testGetWeatherForecastFlux_Success() throws NotFoundException, UnAuthorizedException, InternalServerError {
        String cityName = "TestCity";
        int days = 5;
        WeatherRequest weatherRequest = new WeatherRequest();
        weatherRequest.setCity(cityName);
        weatherRequest.setDays(days);

        WeatherResponse weatherResponse1 = WeatherResponse.builder()
                .dailyWeathers(DailyWeather.builder().maxTemperature(300).minTemperature(273)
                        .date("2024-01-20").time("15:00:00").build())
                .icon("01n").description("clear sky").message("Nice Weather").build();
        WeatherResponse weatherResponse2 = WeatherResponse.builder()
                .dailyWeathers(DailyWeather.builder().maxTemperature(310).minTemperature(283)
                        .date("2024-01-20").time("15:00:00").build())
                .icon("01n").description("Cloudy").message("Nice Weather").build();
        Flux<WeatherResponse> weatherResponseFlux = Flux.just(weatherResponse1, weatherResponse2);
        when(weatherService.getWeatherForCityNonBlocking(weatherRequest)).thenReturn(weatherResponseFlux);

        ResponseEntity<Flux<WeatherResponse>> responseEntity = weatherController.getWeatherForecastFlux(cityName, days);
        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertEquals(weatherResponseFlux, responseEntity.getBody());
        verify(weatherService, times(1)).getWeatherForCityNonBlocking(weatherRequest);
    }
}

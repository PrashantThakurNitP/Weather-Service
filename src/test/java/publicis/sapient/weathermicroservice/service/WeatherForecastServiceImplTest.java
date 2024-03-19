package publicis.sapient.weathermicroservice.service;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import publicis.sapient.weathermicroservice.domain.*;
import publicis.sapient.weathermicroservice.domain.response.WeatherResponse;
import publicis.sapient.weathermicroservice.exception.InternalServerError;
import publicis.sapient.weathermicroservice.exception.NotFoundException;
import publicis.sapient.weathermicroservice.exception.UnAuthorizedException;
import publicis.sapient.weathermicroservice.utils.WebClientWeatherApi;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import java.util.List;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class WeatherForecastServiceImplTest {

    @Mock
    private WebClientWeatherApi webClientWeatherApi;

    @InjectMocks
    private WeatherForecastServiceImpl weatherForecastService;

    @Test
    public void testGetWeatherForCityNonBlocking_Success() throws NotFoundException, UnAuthorizedException, InternalServerError {
        WeatherRequest weatherRequest = new WeatherRequest("TestCity", 1);
        WeatherApiResponse mockResponse = createMockWeatherApiResponse();
        when(webClientWeatherApi.fetchDataFromApi(weatherRequest)).thenReturn(Mono.just(mockResponse));

        Flux<WeatherResponse> weatherResponseFlux = weatherForecastService.getWeatherForCityNonBlocking(weatherRequest);

        // Assert
        StepVerifier.create(weatherResponseFlux)
                .expectNextCount(8).verifyComplete();
    }


    private WeatherApiResponse createMockWeatherApiResponse() {
        WeatherData weatherData1 = WeatherData.builder().dt_txt("21-04-1995 3:00:00").visibility(500).main(MainData.builder().temp(290).temp_min(275).temp_max(300).humidity(50).pressure(1012).feels_like(278).build()).weather(List.of(WeatherDescription.builder().description("Cloudy").icon("01").main("Hot").id(301).build())).wind(WindData.builder().speed(5.0).build()).build();//nice weather
        WeatherData weatherData2 = WeatherData.builder().dt_txt("21-04-1995 6:00:00").visibility(90).main(MainData.builder().temp(290).temp_min(275).temp_max(300).humidity(50).pressure(1012).feels_like(278).build()).weather(List.of(WeatherDescription.builder().description("Cloudy").icon("01").main("main").id(301).build())).wind(WindData.builder().speed(1.0).build()).build();//low visibility
        WeatherData weatherData3 = WeatherData.builder().dt_txt("21-04-1995 9:00:00").visibility(500).main(MainData.builder().temp(290).temp_min(275).temp_max(300).humidity(80).pressure(1012).feels_like(278).build()).weather(List.of(WeatherDescription.builder().description("Cloudy").icon("01").main("main").id(303).build())).wind(WindData.builder().speed(5.0).build()).build();//humid

        WeatherData weatherData4 = WeatherData.builder().dt_txt("21-04-1995 12:00:00").visibility(500).main(MainData.builder().temp(291).temp_min(275).temp_max(300).humidity(50).pressure(1012).feels_like(278).build()).weather(List.of(WeatherDescription.builder().description("Cloudy").icon("01").main("main").id(201).build())).wind(WindData.builder().speed(6.0).build()).build();//thunderstorm
        WeatherData weatherData5 = WeatherData.builder().dt_txt("21-04-1995 15:00:00").visibility(500).main(MainData.builder().temp(290).temp_min(275).temp_max(300).humidity(50).pressure(1012).feels_like(278).build()).weather(List.of(WeatherDescription.builder().description("Cloudy").icon("01").main("main").id(501).build())).wind(WindData.builder().speed(9.0).build()).build();//rain
        WeatherData weatherData6 = WeatherData.builder().dt_txt("21-04-1995 18:00:00").visibility(500).main(MainData.builder().temp(273).temp_min(275).temp_max(300).humidity(50).pressure(1012).feels_like(278).build()).weather(List.of(WeatherDescription.builder().description("Cloudy").icon("01").main("main").id(401).build())).wind(WindData.builder().speed(9.0).build()).build();//low temp

        WeatherData weatherData7 = WeatherData.builder().dt_txt("21-04-1995 21:00:00").visibility(500).main(MainData.builder().temp(293).temp_min(275).temp_max(300).humidity(50).pressure(1012).feels_like(278).build()).weather(List.of(WeatherDescription.builder().description("Cloudy").icon("01").main("main").build())).wind(WindData.builder().speed(20.0).build()).build();//high winds
        WeatherData weatherData8 = WeatherData.builder().dt_txt("22-04-1995 00:00:00").visibility(500).main(MainData.builder().temp(314).temp_min(275).temp_max(300).humidity(50).pressure(1012).feels_like(278).build()).weather(List.of(WeatherDescription.builder().description("Rainy").icon("01").main("main").build())).wind(WindData.builder().speed(6.0).build()).build();//high temp
        return WeatherApiResponse.builder().city(City.builder().timezone(18000).build()).list(List.of(weatherData1, weatherData2, weatherData3, weatherData4, weatherData5, weatherData6, weatherData7, weatherData8)).build();
    }
}

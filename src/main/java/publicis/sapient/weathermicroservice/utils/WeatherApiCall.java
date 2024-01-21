package publicis.sapient.weathermicroservice.utils;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import publicis.sapient.weathermicroservice.domain.WeatherApiResponse;

@Component
@Slf4j
@Configuration
public class WeatherApiCall {
    @Value("${openweathermap.api.key}")
    private String apiKey;

    @Value("${openweathermap.api.url}")
    private String apiUrl;

    private final RestTemplate restTemplate;

    @Autowired
    public WeatherApiCall(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    public WeatherApiResponse getWeatherInfo(String cityName,int cnt) {
        String url = String.format("%s/?q=%s&appid=%s&cnt=%d", apiUrl, cityName, apiKey,cnt);
        log.info("Calling Open Weather API with City :{}, Count : {} {}",cityName,cnt,url);
        return restTemplate.getForObject(url, WeatherApiResponse.class);

    }
}

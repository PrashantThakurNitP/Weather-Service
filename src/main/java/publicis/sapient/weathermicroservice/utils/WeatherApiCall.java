package publicis.sapient.weathermicroservice.utils;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import publicis.sapient.weathermicroservice.domain.WeatherApiResponse;

@Component
public class WeatherApiCall {
    @Value("${openweathermap.api.key}")
    private String apiKey;

    @Value("${openweathermap.api.url}")
    private String apiUrl;
    @Autowired
    RestTemplate restTemplate;

    public WeatherApiResponse getWeatherInfo(String cityName,int cnt) {
        String url = String.format("%s/?q=%s&appid=%s&cnt=%d", apiUrl, cityName, apiKey,cnt);
        return restTemplate.getForObject(url, WeatherApiResponse.class);

    }
}

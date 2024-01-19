package publicis.sapient.weathermicroservice.domain;

import lombok.Data;

import java.util.List;

@Data
public class WeatherApiResponse {
    private int cnt;
    private List<WeatherData> list;
}

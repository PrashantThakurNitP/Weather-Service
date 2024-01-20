package publicis.sapient.weathermicroservice.domain;

import io.swagger.annotations.ApiModel;
import lombok.Data;
import lombok.experimental.SuperBuilder;

import java.util.List;

@Data
@ApiModel
@SuperBuilder
public class WeatherApiResponse {
    private int cnt;
    private List<WeatherData> list;
}

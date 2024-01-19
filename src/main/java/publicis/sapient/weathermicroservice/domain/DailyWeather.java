package publicis.sapient.weathermicroservice.domain;

import io.swagger.annotations.ApiModel;
import lombok.Data;
import lombok.experimental.SuperBuilder;

@Data
@ApiModel
@SuperBuilder
public class DailyWeather {
    int minTemperature;
    int maxTemperature;
    String date;
    String time;
}
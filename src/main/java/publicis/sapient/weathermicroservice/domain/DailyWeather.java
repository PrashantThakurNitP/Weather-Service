package publicis.sapient.weathermicroservice.domain;

import io.swagger.annotations.ApiModel;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@Data
@ApiModel
@SuperBuilder
@NoArgsConstructor
public class DailyWeather {
    int minTemperature;
    int maxTemperature;
    int temperature;
    String date;
    String time;
    Double windSpeed;
}
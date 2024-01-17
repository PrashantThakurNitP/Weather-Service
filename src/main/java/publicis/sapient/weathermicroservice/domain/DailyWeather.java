package publicis.sapient.weathermicroservice.domain;

import io.swagger.annotations.ApiModel;
import lombok.Data;
import lombok.experimental.SuperBuilder;

@Data
@ApiModel
@SuperBuilder
public class DailyWeather {
    Double minTemperature;
    Double maxTemperature;
    String day;
}
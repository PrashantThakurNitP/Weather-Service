package publicis.sapient.weathermicroservice.domain;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.experimental.SuperBuilder;

@Data
@ApiModel
@SuperBuilder
public class WeatherResponse {

    @ApiModelProperty
    private String message;

    @ApiModelProperty
    private DailyWeather [] dailyWeathers;

}

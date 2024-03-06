package publicis.sapient.weathermicroservice.domain.response;

//import io.swagger.annotations.ApiModel;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@Data
//@ApiModel
@Schema
@SuperBuilder
@NoArgsConstructor
public class DailyWeather {
    int minTemperature;
    int maxTemperature;
    int feelsLike;
    int humidity;
    int pressure;
    int temperature;
    String date;
    String time;
    Double windSpeed;
    int visibility;
}
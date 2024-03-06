package publicis.sapient.weathermicroservice.domain;

//import io.swagger.annotations.ApiModel;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import java.util.List;

//@ApiModel
@Schema
@SuperBuilder
@NoArgsConstructor
@Data
public class WeatherApiResponse {
    private List<WeatherData> list;
    private City city;
}

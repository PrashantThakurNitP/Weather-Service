package publicis.sapient.weathermicroservice.domain;


//import io.swagger.annotations.ApiModel;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@NoArgsConstructor
@AllArgsConstructor
@Data
//@ApiModel
@Schema
@SuperBuilder
public class WeatherRequest {
    String city;
    int days;
}
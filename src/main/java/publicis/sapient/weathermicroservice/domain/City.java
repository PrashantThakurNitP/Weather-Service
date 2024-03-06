package publicis.sapient.weathermicroservice.domain;

//import io.swagger.annotations.ApiModel;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

//@ApiModel
@Schema
@SuperBuilder
@NoArgsConstructor
@Data
public class City {
    private int timezone;
}

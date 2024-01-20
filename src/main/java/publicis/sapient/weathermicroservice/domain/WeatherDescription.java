package publicis.sapient.weathermicroservice.domain;

import lombok.Data;
import lombok.experimental.SuperBuilder;

@Data
@SuperBuilder
public class WeatherDescription {
    int id;
    String main;
    String description;
    String icon;
}

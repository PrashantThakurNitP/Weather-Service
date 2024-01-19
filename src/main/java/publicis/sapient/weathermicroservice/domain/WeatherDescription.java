package publicis.sapient.weathermicroservice.domain;

import lombok.Data;

@Data
public class WeatherDescription {
    int id;
    String main;
    String description;
    String icon;
}

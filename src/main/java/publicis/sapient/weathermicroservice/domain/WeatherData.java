package publicis.sapient.weathermicroservice.domain;

import lombok.Data;
import lombok.experimental.SuperBuilder;

import java.util.List;
@Data
@SuperBuilder
public class WeatherData {

    private MainData main;
    private List<WeatherDescription> weather;
    private WindData wind;
    private String dt_txt;


}

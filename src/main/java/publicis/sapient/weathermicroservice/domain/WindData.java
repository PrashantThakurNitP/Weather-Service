package publicis.sapient.weathermicroservice.domain;

import lombok.Data;
import lombok.experimental.SuperBuilder;

@Data
@SuperBuilder
public class WindData {
    Double speed;
    Double deg;
    Double gust;
}

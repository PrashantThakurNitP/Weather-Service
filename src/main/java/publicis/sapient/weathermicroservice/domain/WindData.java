package publicis.sapient.weathermicroservice.domain;

import lombok.Data;

@Data
public class WindData {
    Double speed;
    Double deg;
    Double gust;
}

package publicis.sapient.weathermicroservice.domain;

import lombok.Data;
import lombok.experimental.SuperBuilder;

@Data
@SuperBuilder
public class MainData {
    int temp;
    int temp_min;
    int temp_max;
    Double humidity;
}

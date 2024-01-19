package publicis.sapient.weathermicroservice.domain;

import lombok.Data;

@Data
public class MainData {
    int temp;
    int temp_min;
    int temp_max;
    Double humidity;
}

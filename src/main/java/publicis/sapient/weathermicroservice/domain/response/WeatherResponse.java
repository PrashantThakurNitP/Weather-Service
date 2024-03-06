package publicis.sapient.weathermicroservice.domain.response;

//import io.swagger.annotations.ApiModel;
//import io.swagger.annotations.ApiModelProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.experimental.SuperBuilder;

@Data
//@ApiModel
@Schema
@SuperBuilder
public class WeatherResponse {

   // @ApiModelProperty
    @Schema
    private String message;

   // @ApiModelProperty
   @Schema
    private String icon;

   // @ApiModelProperty
   @Schema
    private String description;

   // @ApiModelProperty
   @Schema
    private DailyWeather dailyWeathers;

    //@ApiModelProperty
    @Schema
    private String  weatherType;
    //@ApiModelProperty
    @Schema
    private int timezoneOffset;

}

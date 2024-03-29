package publicis.sapient.weathermicroservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import publicis.sapient.weathermicroservice.config.SpringFoxConfig;

@SpringBootApplication
public class WeatherMicroserviceApplication {

	public static void main(String[] args) {
		SpringApplication.run(WeatherMicroserviceApplication.class, args);
	}



}

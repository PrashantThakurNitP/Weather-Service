package publicis.sapient.weathermicroservice.config;

//import org.springframework.context.annotation.Bean;
import io.swagger.v3.oas.models.ExternalDocumentation;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
//import org.springframework.web.bind.annotation.RestController;
//import springfox.documentation.builders.ApiInfoBuilder;
//import springfox.documentation.builders.PathSelectors;
//import springfox.documentation.builders.RequestHandlerSelectors;
//import springfox.documentation.service.ApiInfo;
//import springfox.documentation.spi.DocumentationType;
//import springfox.documentation.spring.web.plugins.Docket;
//import springfox.documentation.swagger2.annotations.EnableSwagger2;

@Configuration
//@EnableSwagger2
public class SpringFoxConfig {

    @Bean
    public OpenAPI springShopOpenAPI() {
        return new OpenAPI()
                .info(new Info().title("Weather Forecast Service API Documentation")
                        .description("Microservice to provide weather forecast")
                        .version("1.0")
                        .license(new License().name("Apache 2.0").url("http://springdoc.org")))
                .externalDocs(new ExternalDocumentation()
                        .description("SpringShop Wiki Documentation")
                        .url("https://springshop.wiki.github.org/docs"));
    }

//    @Bean
//    public Docket api(){
//        return new Docket(DocumentationType.SWAGGER_2).select()
//                .apis(RequestHandlerSelectors.withClassAnnotation(RestController.class)).paths(PathSelectors.any())
//                .build().apiInfo(apiInfo()).useDefaultResponseMessages(false);
//    }
//    @Bean
//    public  ApiInfo apiInfo() {
//        return new ApiInfoBuilder()
//                .title("Weather Forecast Service API Documentation")
//                .version("1.0")
//                .description("Microservice to provide weather forecast")
//                .build();
//    }

  //  @Bean
//    public Docket actuatorApi() {
//        return new Docket(DocumentationType.SWAGGER_2)
//                .groupName("actuator")
//                .select()
//                .apis(RequestHandlerSelectors.any())
//                .paths(PathSelectors.ant("/actuator/**"))
//                .build()
//                .apiInfo(actuatorApiInfo());
//    }
//
//    private ApiInfo actuatorApiInfo() {
//        return new ApiInfoBuilder()
//                .title("Spring Boot Actuator Endpoints")
//                .description("Monitoring and management endpoints provided by Spring Boot Actuator")
//                .version("1.0")
//                .build();
//    }
}
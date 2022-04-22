package com.doyouknowdeway.sportsequipmentrent.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;

@Configuration
public class SwaggerConfiguration {

//    http://localhost:8080/swagger-ui/
//    http://localhost:8080/swagger-ui/index.html
//    http://localhost:8080/v2/api-docs

//    Useful links:
//    https://stackoverflow.com/questions/50618350/swagger-with-spring-boot-2-0-leads-to-404-error-page

    @Bean
    public Docket api() {
        return new Docket(DocumentationType.SWAGGER_2)
                .apiInfo(apiInfo())
                .select()
                .apis(RequestHandlerSelectors.basePackage("com.doyouknowdeway.sportsequipmentrent.rest"))
                .paths(PathSelectors.any())
                .build();
    }

    private ApiInfo apiInfo() {
        return new ApiInfoBuilder()
                .title("Sports Equipment Rent API")
                .description("Description")
                .contact(new Contact("DoYouKnowDeway", "https://github.com/doyouknowdeway", ""))
                .license("Apache License Version 2.0")
                .licenseUrl("https://github.com/springfox/springfox/blob/master/LICENSE")
                .version("1.0")
                .build();
    }

}

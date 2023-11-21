package bg.boxerclub.boxerclubbgrestserver.config;

import io.swagger.v3.oas.annotations.enums.SecuritySchemeType;
import io.swagger.v3.oas.annotations.security.SecurityScheme;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@SecurityScheme(
        name = "JWT Bearer Authorization",
        type = SecuritySchemeType.HTTP,
        bearerFormat = "JWT",
        scheme = "bearer"
)
public class OpenAPIConfig {

    @Bean
    public OpenAPI openAPI() {
        return new OpenAPI().info(
                new Info().
                        title("Boxer club").
                        contact(
                                new Contact().
                                        email("magdalenal.velikova@gmail.com")
                                        .name("Magdalena Velikova")

                        ).
                        description("API for maintaining and developing healthy purebred boxers.\n Containing a tool for boxer enthusiasts")
        );
    }

}

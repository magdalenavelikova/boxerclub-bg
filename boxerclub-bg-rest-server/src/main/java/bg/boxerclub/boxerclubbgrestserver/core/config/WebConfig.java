package bg.boxerclub.boxerclubbgrestserver.core.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
@EnableWebMvc
public class WebConfig implements WebMvcConfigurer {

    //allows the header to put JWT into production
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**")
                .allowedOrigins("http://localhost:3000/")
                .allowedOrigins("https://www.boxerclub-bg.org/")
                .allowedMethods("PUT", "DELETE", "POST", "GET", "PATCH")
                .allowedHeaders("Access-Control-Allow-Origin", "Content-Type", "Accept", "Authorization")
                .exposedHeaders("Access-Control-Allow-Origin", "Content-Type", "Accept", "Authorization")
                .allowCredentials(true).maxAge(3600);
    }
}

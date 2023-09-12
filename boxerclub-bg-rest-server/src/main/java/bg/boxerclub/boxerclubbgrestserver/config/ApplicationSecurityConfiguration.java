package bg.boxerclub.boxerclubbgrestserver.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.crypto.password.Pbkdf2PasswordEncoder;

@Configuration
@EnableWebSecurity
public class ApplicationSecurityConfiguration {

    @Bean
    public PasswordEncoder passwordEncoder() {

        return Pbkdf2PasswordEncoder.defaultsForSpringSecurity_v5_8();
    }

//    @Bean
//    public SecurityFilterChain filterChain(HttpSecurity http) {
//http.authorizeHttpRequests(authorize->authorize
//        .requestMatchers("/**").permitAll()
//        .anyRequest().authenticated()
//
//    }
}

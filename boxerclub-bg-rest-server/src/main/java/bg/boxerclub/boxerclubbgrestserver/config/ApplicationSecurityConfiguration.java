package bg.boxerclub.boxerclubbgrestserver.config;

import bg.boxerclub.boxerclubbgrestserver.jwt.JwtAuthenticationFilter;
import bg.boxerclub.boxerclubbgrestserver.service.impl.user.AppUserDetailService;
import bg.boxerclub.boxerclubbgrestserver.util.IpBlackListInterceptor;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.crypto.password.Pbkdf2PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.util.Arrays;

import static org.springframework.security.config.http.SessionCreationPolicy.STATELESS;

@Configuration
@EnableWebSecurity
@EnableWebMvc
@EnableMethodSecurity
public class ApplicationSecurityConfiguration implements WebMvcConfigurer {
    private final AppUserDetailService userDetailsService;
    private final JwtAuthenticationFilter jwtAuthenticationFilter;
    private final IpBlackListInterceptor ipBlacklistInterceptor;

    public ApplicationSecurityConfiguration(AppUserDetailService userDetailsService, JwtAuthenticationFilter jwtAuthenticationFilter, IpBlackListInterceptor ipBlacklistInterceptor) {
        this.userDetailsService = userDetailsService;
        this.jwtAuthenticationFilter = jwtAuthenticationFilter;
        this.ipBlacklistInterceptor = ipBlacklistInterceptor;
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {

        http
                .csrf(AbstractHttpConfigurer::disable)
                .cors(cors -> cors.configurationSource(corsConfigurationSource()))
                .authorizeHttpRequests(request -> request.requestMatchers(
                                "/",
                                "/dogs/approved",
                                "/dogs/ownershipConfirm",
                                "/dogs/details/*",
                                "/dogs/chart/*",
                                "/users/login",
                                "/users/register",
                                "/users/registerConfirm",
                                "/users/forgotten-password",
                                "/users/forgotten-password/new-password",
                                "/links",
                                "/contacts",
                                "/events",
                                "/swagger-ui/**",
                                "/swagger-resources/**",
                                "/v3/**"
                        )
                        .permitAll().anyRequest().authenticated())
                .sessionManagement(manager -> manager.sessionCreationPolicy(STATELESS))
                .authenticationProvider(authenticationProvider())
                .exceptionHandling(configurer -> configurer.authenticationEntryPoint((request, response, exception) ->
                        response.sendError(HttpServletResponse.SC_UNAUTHORIZED,
                                exception.getMessage())))
                .addFilterBefore(
                        jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class);


        return http.build();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {

        return Pbkdf2PasswordEncoder.defaultsForSpringSecurity_v5_8();
    }

    @Bean
    public AuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider();
        authProvider.setUserDetailsService(userDetailsService);
        authProvider.setPasswordEncoder(passwordEncoder());
        return authProvider;
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration config) throws Exception {
        return config.getAuthenticationManager();
    }


    @Bean
    CorsConfigurationSource corsConfigurationSource() {

        CorsConfiguration configuration = new CorsConfiguration();
        configuration.setAllowedOrigins(Arrays.asList("http://localhost:3000", "http://84.43.202.213:8000"
                , "http://localhost:8000", "https://www.boxerclub-bg.org", "https://boxer-club.web.app"));
        configuration.setAllowedMethods(Arrays.asList("PUT", "DELETE", "POST", "GET", "PATCH"));
        configuration.setAllowedHeaders(Arrays.asList("Access-Control-Allow-Origin", "Content-Type", "Content-Disposition", "Accept", "Authorization"));
        configuration.setExposedHeaders(Arrays.asList("Access-Control-Allow-Origin", "Content-Type", "Content-Disposition", "Accept", "Authorization"));
        configuration.setAllowCredentials(true);
        configuration.setMaxAge(3600L);
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", configuration);
        return source;

    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(ipBlacklistInterceptor).addPathPatterns("/users/register");
        WebMvcConfigurer.super.addInterceptors(registry);
    }


}


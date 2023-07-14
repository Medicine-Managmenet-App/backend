package pl.zaprogramujzycie.mma.config;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.servletapi.SecurityContextHolderAwareRequestFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import pl.zaprogramujzycie.mma.filters.TokenAuthorizationFilter;

import java.util.Arrays;
import java.util.List;

@Configuration
@EnableGlobalMethodSecurity(
        securedEnabled = true
)
@EnableWebSecurity
@EnableWebMvc
@RequiredArgsConstructor
@Profile("prod")
public class ProdSecurityConfig {

    private final TokenAuthorizationFilter tokenAuthorizationFilter;

    @Bean
    public SecurityFilterChain securityFilterChain(final HttpSecurity http) throws Exception {
        return http
                .cors().configurationSource(request -> {
                    final CorsConfiguration cors = new CorsConfiguration();
                    cors.setAllowedOrigins(List.of("*"));
                    cors.setAllowedMethods(List.of("GET", "POST", "PUT", "PATCH", "HEAD", "DELETE", "OPTIONS"));
                    cors.setAllowedHeaders(List.of("api-key", "Origin", "Accept", "X-Requested-With", "Content-Type", "Access-Control-Request-Method", "Access-Control-Request-Headers", "Authorization"));
                    cors.setExposedHeaders(List.of("Access-Control-Allow-Origin", "Access-Control-Allow-Credentials", "Authorization"));
                    return cors;
                })
                .and()
                .csrf().disable()
                .authorizeHttpRequests((requests) -> requests
                        .anyRequest().authenticated()
                )
                // .sessionManagement()
                // .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .httpBasic()
                .and()
                // .addFilterBefore(tokenAuthorizationFilter, SecurityContextHolderAwareRequestFilter.class)
                .build();
    }


    @Bean
    public WebSecurityCustomizer webSecurityCustomizer() {
        return (web) -> web.ignoring().antMatchers("/users", "/v3/api-docs/**", "/v2/api-docs",
                "/configuration/ui", "/swagger-ui.html", "swagger-resources", "/swagger-resources/**", "/configuration/security",
                "/swagger-ui/**", "swagger-ui/index.html",
                "/console", "/console/**");
    }

    @Bean
    public BCryptPasswordEncoder bCryptPasswordEncoder() {
        return new BCryptPasswordEncoder(12);
    }

}

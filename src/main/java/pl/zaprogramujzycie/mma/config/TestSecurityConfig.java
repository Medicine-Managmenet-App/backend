package pl.zaprogramujzycie.mma.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.web.cors.CorsConfiguration;

import java.util.List;

@Configuration
@EnableWebSecurity
@Profile("test")
public class TestSecurityConfig {

    @Bean
    public SecurityFilterChain securityFilterChain(final HttpSecurity http) throws Exception {
        http.headers().frameOptions().disable();
        return http
                .cors().configurationSource(request -> {
                    final CorsConfiguration cors = new CorsConfiguration();
                    cors.setAllowedOrigins(List.of("*"));
                    cors.setAllowedMethods(List.of("GET", "POST", "PUT", "PATCH", "HEAD", "DELETE", "OPTIONS"));
                    cors.setAllowedHeaders(List.of("Origin", "Accept", "X-Requested-With", "Content-Type", "Access-Control-Request-Method", "Access-Control-Request-Headers", "Authorization"));
                    cors.setExposedHeaders(List.of("Access-Control-Allow-Origin", "Access-Control-Allow-Credentials", "Authorization"));
                    return cors;
                })
                .and()
                .csrf().disable()
                .authorizeHttpRequests((auth) -> auth
                                .anyRequest().authenticated())
                .httpBasic()
                .and().build();
    }
    @Bean
    public WebSecurityCustomizer webSecurityCustomizer() {
        return (web) -> web.ignoring().antMatchers("/users", "/v3/api-docs/**", "/v2/api-docs",
                "/configuration/ui", "/swagger-ui.html", "swagger-resources", "/swagger-resources/**", "/configuration/security",
                "/swagger-ui/**", "swagger-ui/index.html",
                "/console", "/console/**");
    }


    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public UserDetailsService testOnlyUsers(PasswordEncoder passwordEncoder) {
        User.UserBuilder users = User.builder();
        UserDetails user = users
                .username("login")
                .password(passwordEncoder.encode("password"))
                .roles("MEDICINE-OWNER")
                .build();
        UserDetails user1 = users
                .username("login1")
                .password(passwordEncoder.encode("password1"))
                .roles("NON-OWNER")
                .build();
        UserDetails user2 = users
                .username("login2")
                .password(passwordEncoder.encode("password2"))
                .roles("MEDICINE-OWNER")
                .build();
        UserDetails user3 = users
                .username("login3")
                .password(passwordEncoder.encode("password3"))
                .roles("MEDICINE-OWNER")
                .build();
        UserDetails user4 = users
                .username("newLogin")
                .password(passwordEncoder.encode("newPassword"))
                .roles("MEDICINE-OWNER")
                .build();
        return new InMemoryUserDetailsManager(user, user1, user2, user3, user4);
    }
}
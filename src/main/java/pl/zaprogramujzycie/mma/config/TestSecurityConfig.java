package pl.zaprogramujzycie.mma.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
// @EnableGlobalMethodSecurity(
//         securedEnabled = true
// )
@EnableWebSecurity
// @Profile("test")
public class TestSecurityConfig {

    @Bean
    public SecurityFilterChain securityFilterChain(final HttpSecurity http) throws Exception {
        http.headers().frameOptions().disable();
        return http.authorizeHttpRequests()
                .antMatchers("/medicines/**")
                .hasRole("MEDICINE-OWNER")
                .and()
                .csrf().disable()
                .httpBasic()
                .and().build();
    }



    // private static void customizeSecurity(AuthorizeHttpRequestsConfigurer<HttpSecurity>.AuthorizationManagerRequestMatcherRegistry authorizationManagerRequestMatcherRegistry) {
    //     authorizationManagerRequestMatcherRegistry.anyRequest().permitAll();
    // }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public UserDetailsService testOnlyUsers(PasswordEncoder passwordEncoder) {
        User.UserBuilder users = User.builder();
        UserDetails hundred = users
                .username("100")
                .password(passwordEncoder.encode("abc123"))
                .roles("MEDICINE-OWNER")
                .build();
        UserDetails noMedicine = users
                .username("no-medicine")
                .password(passwordEncoder.encode("qrs456"))
                .roles("NON-OWNER")
                .build();
        UserDetails hundred1 = users
                .username("101")
                .password(passwordEncoder.encode("xyz789"))
                .roles("MEDICINE-OWNER")
                .build();
        return new InMemoryUserDetailsManager(hundred, noMedicine, hundred1);
    }
}
package pl.zaprogramujzycie.mma.services;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Profile;
import org.springframework.http.HttpHeaders;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import pl.zaprogramujzycie.mma.config.ApplicationConfiguration;
import pl.zaprogramujzycie.mma.dto.request.LoginRequest;
import pl.zaprogramujzycie.mma.exceptions.LoginException;

import javax.servlet.http.HttpServletResponse;
import java.util.Date;

@Service
@RequiredArgsConstructor
@Profile("prod")
public class LoginService {

    private final UserDetailsService userDetailsService;
    private final ApplicationConfiguration configuration;
    private final PasswordEncoder passwordEncoder;

    public void login(final LoginRequest loginRequest, final HttpServletResponse httpServletResponse) {
        try{
            final UserDetails userDetails = userDetailsService.loadUserByUsername(loginRequest.username());
            if (passwordEncoder.matches(loginRequest.password(), userDetails.getPassword())) {
                final String token = "Bearer " + JWT.create()
                        .withSubject(loginRequest.username())
                        .withExpiresAt(new Date(System.currentTimeMillis() + configuration.getJwt().getExpiration()))
                        .sign(Algorithm.HMAC256(configuration.getJwt().getSignKey()));
                httpServletResponse.setHeader(HttpHeaders.AUTHORIZATION, token);
            }
        } catch (final Exception exception) {
            throw new LoginException(loginRequest.username(), loginRequest.password());
        }
    }
}

package pl.zaprogramujzycie.mma.filters;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Lazy;
import org.springframework.context.annotation.Profile;
import org.springframework.http.HttpHeaders;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import pl.zaprogramujzycie.mma.config.ApplicationConfiguration;
import pl.zaprogramujzycie.mma.utils.Constants;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Component
@RequiredArgsConstructor(onConstructor = @__(@Lazy))
@Slf4j
@Profile("prod")
public class TokenAuthorizationFilter extends OncePerRequestFilter {

    private final ApplicationConfiguration configuration;
    private final UserDetailsService userDetailsService;

    @Override
    protected void doFilterInternal(final HttpServletRequest request, final HttpServletResponse response, final FilterChain filterChain) throws ServletException, IOException {
        final String AUTHORIZATION_HEADER = request.getHeader(HttpHeaders.AUTHORIZATION);
        if (AUTHORIZATION_HEADER == null || AUTHORIZATION_HEADER.length() == 0 || !AUTHORIZATION_HEADER.contains(Constants.TOKEN_PREFIX)) {
            filterChain.doFilter(request, response);
            return;
        }
        final String userName = getName(AUTHORIZATION_HEADER);
        final UserDetails userDetails = userDetailsService.loadUserByUsername(userName);
        final UsernamePasswordAuthenticationToken token = new UsernamePasswordAuthenticationToken(userDetails.getUsername(), null, userDetails.getAuthorities());
        SecurityContextHolder.getContext().setAuthentication(token);
        filterChain.doFilter(request, response);
    }

    private String getName(final String authorizationHeader) {
        return JWT.require(Algorithm.HMAC256(configuration.getJwt().getSignKey()))
                .build()
                .verify(authorizationHeader.replace(Constants.TOKEN_PREFIX, Constants.EMPTY_STRING))
                .getSubject();
    }
}

package pl.zaprogramujzycie.mma.controllers;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Profile;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import pl.zaprogramujzycie.mma.dto.request.LoginRequest;
import pl.zaprogramujzycie.mma.services.LoginService;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

@RestController
@RequiredArgsConstructor
@Slf4j
@RequestMapping("/login")
@Profile("prod")
@CrossOrigin
public class LoginController {

    private final LoginService loginService;

    @Operation(
            summary = "Returns token to authorization in header",
            description = "Returns token to authorization in header",
            tags = "Endpoints used by anonymous user"
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Successfully returns token"),
            @ApiResponse(responseCode = "401", description = "Failed to login")
    })
    @PostMapping
    public void login(@RequestBody final LoginRequest loginRequest, final HttpServletResponse httpServletResponse) {
        log.info("login() - user = {}", loginRequest.login());
        loginService.login(loginRequest, httpServletResponse);
    }
}

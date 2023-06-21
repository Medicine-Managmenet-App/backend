package pl.zaprogramujzycie.mma.utils;

import pl.zaprogramujzycie.mma.dto.response.UserResponse;
import pl.zaprogramujzycie.mma.entity.Family;
import pl.zaprogramujzycie.mma.entity.User;
import pl.zaprogramujzycie.mma.services.UserService;

import java.security.Principal;

public class OwnerAssigner {

    private final UserService userService;

    OwnerAssigner(UserService userService) {
        this.userService = userService;
    }

    public Long assignFamilyAsOwner(final Principal principal) {
        String login = principal.getName();
        UserResponse userResponse = userService.findByLogin(login);
        return userResponse.familyId();
    }
}

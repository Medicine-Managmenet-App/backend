package pl.zaprogramujzycie.mma.utils;


import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pl.zaprogramujzycie.mma.dto.response.UserResponse;
import pl.zaprogramujzycie.mma.exceptions.NotFoundException;
import pl.zaprogramujzycie.mma.services.FamilyService;
import pl.zaprogramujzycie.mma.services.UserService;

import java.security.Principal;

@Service
public class UserToFamilyValidator {
    private final UserService userService;


    UserToFamilyValidator(UserService userService) {
        this.userService = userService;
    }
    //ToDo move to user service

    @Transactional
    public void checkUserPermissionsOnFamily(final Principal principal, final long familyId) throws NotFoundException {
        String login = principal.getName();
        UserResponse user = userService.findByLogin(login);
        long userFamilyId = user.family();
        if (userFamilyId != familyId) {throw new NotFoundException();}
    }
}

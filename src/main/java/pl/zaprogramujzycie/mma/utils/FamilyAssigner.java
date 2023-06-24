package pl.zaprogramujzycie.mma.utils;


import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pl.zaprogramujzycie.mma.dto.response.FamilyResponse;
import pl.zaprogramujzycie.mma.dto.response.UserResponse;
import pl.zaprogramujzycie.mma.exceptions.NotFoundException;
import pl.zaprogramujzycie.mma.services.FamilyService;
import pl.zaprogramujzycie.mma.services.UserService;

import java.security.Principal;

@Service
public class FamilyAssigner {
    private final UserService userService;
    private final FamilyService familyService;


    FamilyAssigner(UserService userService, FamilyService familyService) {
        this.userService = userService;
        this.familyService = familyService;
    }

    @Transactional
    public FamilyResponse assignFamilyAsOwner(final Principal principal) throws NotFoundException {
        String login = principal.getName();
        UserResponse user = userService.findByLogin(login);
        long familyId = user.family();
        return familyService.findById(familyId);
    }


}

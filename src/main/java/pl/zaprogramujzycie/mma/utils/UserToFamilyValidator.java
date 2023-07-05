package pl.zaprogramujzycie.mma.utils;


import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import pl.zaprogramujzycie.mma.entities.Family;
import pl.zaprogramujzycie.mma.exceptions.NotFoundException;
import pl.zaprogramujzycie.mma.repositories.FamilyRepository;

import java.security.Principal;
import java.util.List;
import java.util.Optional;

@Service
public class UserToFamilyValidator {
    private final FamilyRepository familyRepository;


    UserToFamilyValidator(FamilyRepository familyRepository) {
        this.familyRepository = familyRepository;
    }
    //ToDo move to user services

    @Transactional
    public void checkUserPermissionsOnFamily(final Principal principal, final long familyId) throws NotFoundException {
        String login = principal.getName();
        Optional<Family> optional = familyRepository.findById(familyId);
        List<String> authorizedUsers = optional.orElseThrow(NotFoundException::new).getLogins();
        if(!authorizedUsers.contains(login)) {throw new NotFoundException();}
    }
}

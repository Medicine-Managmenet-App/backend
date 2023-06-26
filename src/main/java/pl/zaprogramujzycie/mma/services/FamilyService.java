package pl.zaprogramujzycie.mma.services;

import lombok.extern.slf4j.Slf4j;
import org.mapstruct.factory.Mappers;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pl.zaprogramujzycie.mma.dto.response.FamilyResponse;
import pl.zaprogramujzycie.mma.dto.response.UserResponse;
import pl.zaprogramujzycie.mma.entities.Family;
import pl.zaprogramujzycie.mma.entities.FamilyMember;
import pl.zaprogramujzycie.mma.entities.Medicine;
import pl.zaprogramujzycie.mma.entities.User;
import pl.zaprogramujzycie.mma.exceptions.NotFoundException;
import pl.zaprogramujzycie.mma.repositories.FamilyRepository;
import pl.zaprogramujzycie.mma.utils.UserToFamilyValidator;
import pl.zaprogramujzycie.mma.utils.mappers.FamilyMapper;

import java.security.Principal;
import java.util.ArrayList;
import java.util.Optional;


@Slf4j
@Service
public class FamilyService {

    private final FamilyRepository repository;
    private final UserToFamilyValidator validator;

    private final FamilyMapper mapper
            = Mappers.getMapper(FamilyMapper.class);

    public FamilyService(FamilyRepository repository, UserToFamilyValidator validator) {
        this.repository = repository;
        this.validator = validator;
    }

    //This method is only used in the background during creation of User, it returns
    // Family Entity that goes into user service.
    @Transactional
    public Family create() {
        Family newFamily = new Family(null, new ArrayList<>(),
                new ArrayList<>(), new ArrayList<>());
        return repository.save(newFamily);
    }

    public FamilyResponse findById(final Principal principal, final long id) throws NotFoundException {
        System.out.println("-----service---");
        validator.checkUserPermissionsOnFamily(principal, id);
        Family response = findEntity(id);
        return mapper.mapToResponse(response);
    }

    //ToDO take care of principal
    private Family findEntity(final long id) throws NotFoundException {
        Optional<Family> response = repository.findById(id);
        return response.orElseThrow(NotFoundException:: new);
    }

    // @Transactional
    // private void checkUserPermissionsOnFamily(final Principal principal, final long familyId) throws NotFoundException {
    //     String login = principal.getName();
    //     UserResponse user = userService.findByLogin(login);
    //     long userFamilyId = user.family();
    //     if (userFamilyId != familyId) {throw new NotFoundException();}
    // }
}

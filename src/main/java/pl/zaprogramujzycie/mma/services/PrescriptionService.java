package pl.zaprogramujzycie.mma.services;

import lombok.extern.slf4j.Slf4j;
import org.mapstruct.factory.Mappers;
import org.springframework.stereotype.Service;
import pl.zaprogramujzycie.mma.dto.response.FamilyMemberResponse;
import pl.zaprogramujzycie.mma.dto.response.PrescriptionResponse;
import pl.zaprogramujzycie.mma.entities.FamilyMember;
import pl.zaprogramujzycie.mma.entities.Prescription;
import pl.zaprogramujzycie.mma.exceptions.NotFoundException;
import pl.zaprogramujzycie.mma.repositories.FamilyMemberRepository;
import pl.zaprogramujzycie.mma.repositories.PrescriptionRepository;
import pl.zaprogramujzycie.mma.utils.UserToFamilyValidator;
import pl.zaprogramujzycie.mma.utils.mappers.FamilyMemberMapper;
import pl.zaprogramujzycie.mma.utils.mappers.PrescriptionMapper;

import java.security.Principal;
import java.util.Optional;


@Slf4j
@Service
public class PrescriptionService {

    private final PrescriptionRepository repository;
    private final UserToFamilyValidator validator;

    private final PrescriptionMapper mapper
            = Mappers.getMapper(PrescriptionMapper.class);

    public PrescriptionService(PrescriptionRepository repository, UserToFamilyValidator validator) {
        this.repository = repository;
        this.validator = validator;
    }

    //This method is only used in the background during creation of User, it returns
    // Family Entity that goes into user service.
    // @Transactional
    // public Family create() {
    //     Family newFamily = new Family(null, new ArrayList<>(),
    //             new ArrayList<>(), new ArrayList<>());
    //     return repository.save(newFamily);
    // }

    public PrescriptionResponse findById(final Principal principal, final long id, final long familyId) throws NotFoundException {
        System.out.println("-----service---");
        System.out.println("principal: " + principal);
        validator.checkUserPermissionsOnFamily(principal, familyId);
        System.out.println("validated");
        Prescription response = findEntity(id, familyId);
        return mapper.mapToResponse(response);
    }

    //ToDO take care of principal
    private Prescription findEntity(final long id, final long familyId) throws NotFoundException {
        Optional<Prescription> response = repository.findByIdAndFamilyId(id, familyId);
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

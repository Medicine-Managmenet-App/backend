package pl.zaprogramujzycie.mma.services;

import lombok.extern.slf4j.Slf4j;
import org.mapstruct.factory.Mappers;
import org.springframework.data.crossstore.ChangeSetPersister;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pl.zaprogramujzycie.mma.dto.request.FamilyRequest;
import pl.zaprogramujzycie.mma.dto.request.MedicineRequest;
import pl.zaprogramujzycie.mma.dto.response.FamilyResponse;
import pl.zaprogramujzycie.mma.dto.response.UserResponse;
import pl.zaprogramujzycie.mma.entities.Family;
import pl.zaprogramujzycie.mma.entities.FamilyMember;
import pl.zaprogramujzycie.mma.entities.Medicine;
import pl.zaprogramujzycie.mma.entities.Prescription;
import pl.zaprogramujzycie.mma.entities.User;
import pl.zaprogramujzycie.mma.exceptions.NotFoundException;
import pl.zaprogramujzycie.mma.repositories.FamilyRepository;
import pl.zaprogramujzycie.mma.utils.UserToFamilyValidator;
import pl.zaprogramujzycie.mma.utils.mappers.FamilyMapper;
import pl.zaprogramujzycie.mma.utils.mappers.UserMapper;

import java.security.Principal;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;


@Slf4j
@Service
public class FamilyService {

    private final FamilyRepository repository;
    private final UserToFamilyValidator validator;

    private final UserService userService;

    private final FamilyMapper mapper
            = Mappers.getMapper(FamilyMapper.class);

    private final UserMapper userMapper
            = Mappers.getMapper(UserMapper.class);

    FamilyService(FamilyRepository repository, UserToFamilyValidator validator, UserService userService) {
        this.repository = repository;
        this.validator = validator;
        this.userService = userService;
    }

    //This method is only used in the background during creation of User, it returns Family Entity that goes into user service.
    @Transactional
    public Family create() {
        Family newFamily = new Family(null, new ArrayList<>(),
                new ArrayList<>(), new ArrayList<>());
        return repository.save(newFamily);
    }

    public FamilyResponse findById(final Principal principal, final long id) throws NotFoundException {
        validator.checkUserPermissionsOnFamily(principal, id);
        Family response = findEntity(id);
        return mapper.mapToResponse(response);
    }

    private Family findEntity(final long id) throws NotFoundException {
        Optional<Family> response = repository.findById(id);
        return response.orElseThrow(NotFoundException:: new);
    }

    @Transactional
    public void adUserToTheFamily(final long id, final FamilyRequest request, final Principal principal) throws NotFoundException {
        User user = userMapper.mapResponseToEntity(userService.findByLogin(principal.getName()));
        Family response = findEntity(id);
        response.getUsers().add(user);
        repository.save(response);
    }

    public void removeUserFromTheFamily (final long id, final long userId) throws NotFoundException {
        User user = userMapper.mapResponseToEntity(userService.findById(userId));
        Family response = findEntity(id);
        List<User> users =  response.getUsers();
        users.remove(user);
        if (users.isEmpty()) {deleteById(id);}
    }

    @Transactional
    public void deleteById(final long id) throws NotFoundException {
        Family response = findEntity(id);
        repository.deleteById(response.getId());
    }
}

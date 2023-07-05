package pl.zaprogramujzycie.mma.services;

import lombok.extern.slf4j.Slf4j;
import org.mapstruct.factory.Mappers;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pl.zaprogramujzycie.mma.dto.request.FamilyRequest;
import pl.zaprogramujzycie.mma.dto.response.FamilyResponse;
import pl.zaprogramujzycie.mma.entities.Family;
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

    private final FamilyMapper mapper
            = Mappers.getMapper(FamilyMapper.class);

    private final UserMapper userMapper
            = Mappers.getMapper(UserMapper.class);


    public FamilyService(FamilyRepository repository, UserToFamilyValidator validator) {
        this.repository = repository;
        this.validator = validator;
    }

    //This method is only used in the background during creation of User, it returns Family Entity that goes into user services.
    @Transactional
    public Family create(final String userLogin) {
        List<String> users = new ArrayList<>();
        users.add(userLogin);
        Family newFamily = new Family(null, new ArrayList<>(),
                users, new ArrayList<>());
        return repository.save(newFamily);
    }

    @Transactional
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
    public void addUserToTheFamily(final long id, final FamilyRequest request, final Principal principal) throws NotFoundException {
        Family family = findEntity(id);
        String userLogin = principal.getName();
        List<String> users = family.getLogins();
        users.add(userLogin);
        family.setLogins(users);
        repository.save(family);
    }

    @Transactional
    public void removeUserFromTheFamily (final long id, final String userId) throws NotFoundException {
        Family response = findEntity(id);
        List<String> users =  response.getLogins();
        users.remove(userId);
        if (users.isEmpty()) {deleteById(id);}
    }

    @Transactional
    public void deleteById(final long id) throws NotFoundException {
        Family response = findEntity(id);
        repository.deleteById(response.getId());
    }
}

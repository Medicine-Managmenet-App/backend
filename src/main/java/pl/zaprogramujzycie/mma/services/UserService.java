package pl.zaprogramujzycie.mma.services;


import lombok.extern.slf4j.Slf4j;
import org.mapstruct.factory.Mappers;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pl.zaprogramujzycie.mma.dto.request.ChangeFamilyRequest;
import pl.zaprogramujzycie.mma.dto.request.UserRequest;
import pl.zaprogramujzycie.mma.dto.response.UserResponse;
import pl.zaprogramujzycie.mma.entities.Family;
import pl.zaprogramujzycie.mma.entities.User;
import pl.zaprogramujzycie.mma.exceptions.NotFoundException;
import pl.zaprogramujzycie.mma.repositories.UserRepository;
import pl.zaprogramujzycie.mma.utils.mappers.UserMapper;

import java.security.Principal;
import java.util.Optional;

@Slf4j
@Service
public class UserService {

    private final UserRepository repository;
    private final FamilyService familyService;
    private final PasswordEncoder passwordEncoder;

    private final UserMapper mapper
            = Mappers.getMapper(UserMapper.class);

    public UserService(UserRepository repository, FamilyService familyService, PasswordEncoder passwordEncoder) {
        this.repository = repository;
        this.familyService = familyService;
        this.passwordEncoder = passwordEncoder;
    }

    @Transactional
    public UserResponse save(final UserRequest request) {
        Family family = familyService.create(request.login());
        User newUser = new User(null, request.login(),
                passwordEncoder.encode(request.password()), family.getId());
        repository.save(newUser);
        return mapper.mapToResponse(repository.save(newUser));
    }

    public UserResponse findById(Principal principal, final long id) throws NotFoundException {
        Optional<User> optional = repository.findByIdAndLogin(id, principal.getName());
        return mapper.mapToResponse(optional.orElseThrow(NotFoundException::new));
    }

    public UserResponse findByLogin(final String login) throws NotFoundException {
        Optional<User> response = repository.findByLogin(login);
        return mapper.mapToResponse(response.orElseThrow(NotFoundException:: new));
    }

    @Transactional
    public void deleteById(final long id, final Principal principal) throws NotFoundException {
        Optional<User> response = repository.findById(id);
        User user = response.orElseThrow(NotFoundException::new);
        user.setFamily(null);
        try {
            repository.deleteById(user.getId());
        } catch (DataIntegrityViolationException ic) {
            throw new DataIntegrityViolationException("User connected to other object/objects");
        }
        familyService.removeUserFromTheFamily(user.getFamilyId(), principal.getName());
    }

    @Transactional
    public void changeFamily(final long id, final Principal principal, final ChangeFamilyRequest family) throws NotFoundException {
        Optional<User> optional = repository.findById(id);
        User user = optional.orElseThrow(NotFoundException::new);
        familyService.removeUserFromTheFamily(user.getFamilyId(), user.getLogin());
        user.setFamilyId(family.familyId());
        repository.save(user);
    }

    @Transactional
    public void changePassword(final long id, final Principal principal, final UserRequest userRequest) throws NotFoundException {
        Optional<User> optional = repository.findById(id);
        User user = optional.orElseThrow(NotFoundException::new);
        user.setPassword(passwordEncoder.encode(userRequest.password()));
        repository.save(user);
    }
}

package pl.zaprogramujzycie.mma.services;


import lombok.extern.slf4j.Slf4j;
import org.mapstruct.factory.Mappers;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pl.zaprogramujzycie.mma.dto.request.UserRequest;
import pl.zaprogramujzycie.mma.dto.response.UserResponse;
import pl.zaprogramujzycie.mma.entities.Family;
import pl.zaprogramujzycie.mma.entities.User;
import pl.zaprogramujzycie.mma.exceptions.NotFoundException;
import pl.zaprogramujzycie.mma.repositories.UserRepository;
import pl.zaprogramujzycie.mma.utils.mappers.UserMapper;

import java.util.List;
import java.util.Optional;

@Slf4j
@Service
public class UserService {

    private final UserRepository repository;


    private final UserMapper mapper
            = Mappers.getMapper(UserMapper.class);

    public UserService(UserRepository repository) {
        this.repository = repository;
    }

    @Transactional
    public UserResponse save(final UserRequest request) {
        // Family family = familyService.create();
        User newUser = mapper.mapToEntity(request);
        // newUser.setFamily(family);
        repository.save(newUser);
        // System.out.println("----" + family.getUsers());
        // List<User> users = family.getUsers();
        // users.add(newUser);
        // family.setUsers(users);

        return mapper.mapToResponse(repository.save(newUser));
    }

    public UserResponse findById(final long id) throws NotFoundException {
        return mapper.mapToResponse(findEntity(id));
    }

    public UserResponse findByLogin(final String login) throws NotFoundException {
        Optional<User> response = repository.findByLogin(login);
        return mapper.mapToResponse(response.orElseThrow(NotFoundException:: new));
    }

    private User findEntity(final long id) throws NotFoundException {
        Optional<User> response = repository.findById(id);
        return response.orElseThrow(NotFoundException:: new);
    }

}

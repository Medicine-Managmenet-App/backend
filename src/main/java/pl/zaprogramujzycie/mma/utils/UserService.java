package pl.zaprogramujzycie.mma.utils;
import pl.zaprogramujzycie.mma.entity.User;
import pl.zaprogramujzycie.mma.repositories.UserRepository;

import java.util.Optional;


public class UserService {

    private UserRepository userRepository;

    public void partialUpdate(long id, String login, String password) {
        Optional<User> optional = userRepository.findById(id);
        if (optional.isPresent()) {
            User user = optional.get();
            if (login != null) {
                user.setLogin(login);
            }
            if (password != null) {
                user.setPassword(password);
            }
        }
    }
}

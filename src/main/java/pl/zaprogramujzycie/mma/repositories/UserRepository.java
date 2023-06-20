package pl.zaprogramujzycie.mma.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import pl.zaprogramujzycie.mma.entities.User;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByLogin(final String login); }

package pl.zaprogramujzycie.mma.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import pl.zaprogramujzycie.mma.entities.User;

public interface UserRepository extends JpaRepository<User, Long> { }

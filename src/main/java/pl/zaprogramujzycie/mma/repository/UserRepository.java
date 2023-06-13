package pl.zaprogramujzycie.mma.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import pl.zaprogramujzycie.mma.entity.User;

public interface UserRepository extends JpaRepository<User, Long> { }

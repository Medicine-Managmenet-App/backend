package pl.zaprogramujzycie.mma.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pl.zaprogramujzycie.mma.entities.Family;


@Repository
public interface FamilyRepository extends JpaRepository<Family, Long> {


}

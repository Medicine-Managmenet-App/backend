package pl.zaprogramujzycie.mma.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pl.zaprogramujzycie.mma.entities.FamilyMember;
import pl.zaprogramujzycie.mma.entities.Prescription;

import java.util.Optional;

@Repository
public interface PrescriptionRepository extends JpaRepository<Prescription, Long> {
    Optional<Prescription> findByIdAndFamilyId(final long id, final long familyId);
}

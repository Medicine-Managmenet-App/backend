package pl.zaprogramujzycie.mma.repositories;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pl.zaprogramujzycie.mma.entities.FamilyMember;

import java.util.Optional;

@Repository
public interface FamilyMemberRepository extends JpaRepository<FamilyMember, Long> {
    Optional<FamilyMember> findByIdAndFamilyId(final long id, final long familyId);
    Page<FamilyMember> findByFamilyId(final long familyId, final PageRequest page);
}
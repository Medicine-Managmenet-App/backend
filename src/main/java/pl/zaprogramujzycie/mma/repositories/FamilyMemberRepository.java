package pl.zaprogramujzycie.mma.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import pl.zaprogramujzycie.mma.entities.FamilyMember;


public interface FamilyMemberRepository extends JpaRepository<FamilyMember, Long> { }

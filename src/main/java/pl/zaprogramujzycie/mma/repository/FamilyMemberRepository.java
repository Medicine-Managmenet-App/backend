package pl.zaprogramujzycie.mma.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import pl.zaprogramujzycie.mma.entity.FamilyMember;

public interface FamilyMemberRepository extends JpaRepository<FamilyMember, Long> { }

package pl.zaprogramujzycie.mma.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import pl.zaprogramujzycie.mma.entity.Medicine;

public interface MedicineRepository extends JpaRepository<Medicine, Long> { }

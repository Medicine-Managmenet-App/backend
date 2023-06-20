package pl.zaprogramujzycie.mma.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import pl.zaprogramujzycie.mma.entities.Medicine;

public interface MedicineRepository extends JpaRepository<Medicine, Long> { }

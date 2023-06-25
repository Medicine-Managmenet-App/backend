package pl.zaprogramujzycie.mma.repositories;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pl.zaprogramujzycie.mma.entities.Family;
import pl.zaprogramujzycie.mma.entities.Medicine;
import pl.zaprogramujzycie.mma.entities.PrescribedMedicine;
import pl.zaprogramujzycie.mma.entities.Prescription;

import java.util.Optional;

@Repository
public interface PrescribedMedicineRepository extends JpaRepository<PrescribedMedicine, Long> {
    Optional<PrescribedMedicine> findByIdAndPrescriptionId(final long id, final long prescriptionId);
    Page<PrescribedMedicine> findByPrescriptionId(final long prescriptionId, final PageRequest page);
}

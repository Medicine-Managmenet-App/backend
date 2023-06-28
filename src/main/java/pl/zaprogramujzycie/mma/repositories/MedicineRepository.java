package pl.zaprogramujzycie.mma.repositories;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;
import pl.zaprogramujzycie.mma.entities.Family;
import pl.zaprogramujzycie.mma.entities.Medicine;



import java.util.Optional;

@Repository
public interface MedicineRepository extends JpaRepository<Medicine, Long>, PagingAndSortingRepository<Medicine, Long> {
    Optional<Medicine> findByIdAndFamilyId(final long id, final long familyId);
    Page<Medicine> findByFamilyId(final long familyId, final PageRequest page);
}

package pl.zaprogramujzycie.mma.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;
import pl.zaprogramujzycie.mma.entity.PrescribedMedicine;

@Repository
public interface PrescribedMedicineRepository extends JpaRepository<PrescribedMedicine, Long>, PagingAndSortingRepository<PrescribedMedicine, Long> {
    PrescribedMedicine findByIdAndOwner(long id, String owner);
    Page<PrescribedMedicine> findByOwner(String owner, PageRequest page);
    boolean existsByIdAndOwner(Long id, String owner);
}

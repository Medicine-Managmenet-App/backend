package pl.zaprogramujzycie.mma.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;
import pl.zaprogramujzycie.mma.entity.Medicine;

@Repository
public interface MedicineRepository extends JpaRepository<Medicine, Long>, PagingAndSortingRepository<Medicine, Long> {
    Medicine findByIdAndOwner(long id, String owner);
    Page<Medicine> findByOwner(String owner, PageRequest page);
    boolean existsByIdAndOwner(Long id, String owner);
}

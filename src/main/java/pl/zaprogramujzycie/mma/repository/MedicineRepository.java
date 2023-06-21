package pl.zaprogramujzycie.mma.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;
import pl.zaprogramujzycie.mma.entity.Medicine;

import java.util.Optional;

@Repository
public interface MedicineRepository extends JpaRepository<Medicine, Long>, PagingAndSortingRepository<Medicine, Long> {
    Optional<Medicine> findByIdAndOwner(long id, Long owner);
    Page<Medicine> findByOwner(Long owner, PageRequest page);
}

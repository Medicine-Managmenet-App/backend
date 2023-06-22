package pl.zaprogramujzycie.mma.services;


import lombok.extern.slf4j.Slf4j;

import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.webjars.NotFoundException;
import pl.zaprogramujzycie.mma.dto.request.MedicineRequest;
import pl.zaprogramujzycie.mma.dto.response.MedicineResponse;
import pl.zaprogramujzycie.mma.dto.response.MedicinesResponse;
import pl.zaprogramujzycie.mma.entities.Medicine;
import pl.zaprogramujzycie.mma.repositories.MedicineRepository;
import pl.zaprogramujzycie.mma.utils.OwnerAssigner;
import pl.zaprogramujzycie.mma.utils.mappers.MedicineMapper;
import java.security.Principal;
import java.util.Optional;

@Slf4j
@Service
public class MedicineService {

    private final MedicineRepository repository;

    public MedicineService(MedicineRepository repository) {
        this.repository = repository;
    }

    public MedicineResponse save(final MedicineRequest request, final Principal principal) {
        Medicine newMedicine = MedicineMapper.mapToMedicine(request);
        newMedicine.setOwner(OwnerAssigner.assignFamilyAsOwner(principal));
        return MedicineMapper.mapToMedicineDTO(repository.save(newMedicine));
    }

    public MedicineResponse findById(final long id, final Principal principal) throws NotFoundException {
        Optional<Medicine> response = findEntity(id, principal);
        Medicine medicine = response.orElseThrow(() -> new NotFoundException("Medicine not found"));
        return MedicineMapper.mapToMedicineDTO(medicine);
    }

    public MedicinesResponse findAll(final Principal principal, final Pageable pageable) {
        Page<Medicine> medicine = repository.findByOwner(OwnerAssigner.assignFamilyAsOwner(principal), PageRequest.of(
                pageable.getPageNumber(),
                pageable.getPageSize(),
                pageable.getSortOr(Sort.by(Sort.Direction.ASC, "name"))));
        Page<MedicineResponse> page = MedicineMapper.mapToResponsePage(medicine);
        return new MedicinesResponse(page.getContent());
    }

    // ToDo if(PAO != 0) change expiration Date to (request timestamp + PAO)
    @Transactional
    public void partialUpdate(final long id, final MedicineRequest request, final Principal principal) {
        Optional<Medicine> response = findEntity(id, principal);
        Medicine updatedMedicine = response.orElseThrow(() -> new NotFoundException("Medicine not found"));
        if (request.name() != null) {
            updatedMedicine.setName(request.name());
        }
        if (request.expirationDate() != null) {
            updatedMedicine.setExpirationDate(request.expirationDate());
        }
        if (request.periodAfterOpening() != 0) {
            updatedMedicine.setPeriodAfterOpening(request.periodAfterOpening());
        }
        repository.save(updatedMedicine);
    }

    private Optional<Medicine> findEntity(final long id, final Principal principal) {
        return repository.findByIdAndOwner(id, OwnerAssigner.assignFamilyAsOwner(principal));
    }

    public void deleteById(final long id, final Principal principal) {
        Optional<Medicine> response = findEntity(id, principal);
        Medicine deletedMedicine = response.orElseThrow(() -> new NotFoundException("Medicine not found"));
        try {
            repository.deleteById(deletedMedicine.getId());
        } catch (DataIntegrityViolationException ic) {
            throw new DataIntegrityViolationException("Medicine connected to other object/objects");
        }
    }
}

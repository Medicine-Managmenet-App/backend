package pl.zaprogramujzycie.mma.services;


import lombok.extern.slf4j.Slf4j;

import org.mapstruct.factory.Mappers;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pl.zaprogramujzycie.mma.dto.request.MedicineRequest;
import pl.zaprogramujzycie.mma.dto.response.MedicineResponse;
import pl.zaprogramujzycie.mma.dto.response.MedicinesResponse;
import pl.zaprogramujzycie.mma.entities.Medicine;
import pl.zaprogramujzycie.mma.exceptions.NotFoundException;
import pl.zaprogramujzycie.mma.repositories.MedicineRepository;
import pl.zaprogramujzycie.mma.utils.UserToFamilyValidator;
import pl.zaprogramujzycie.mma.utils.mappers.MedicineMapper;

import java.security.Principal;
import java.util.Optional;

@Slf4j
@Service
public class MedicineService {

    private final MedicineRepository repository;
    private final MedicineMapper mapper = Mappers.getMapper(MedicineMapper.class);

    private final UserToFamilyValidator validator;

    public MedicineService(MedicineRepository repository, UserToFamilyValidator validator) {
        this.repository = repository;
        this.validator = validator;
    }

    @Transactional
    public MedicineResponse save(final MedicineRequest request, final Principal principal, final long familyId) throws NotFoundException {
        validator.checkUserPermissionsOnFamily(principal, familyId);
        Medicine newMedicine = new Medicine(null, request.name(), request.expirationDate(), request.periodAfterOpening(), familyId);
        return mapper.mapToResponse(repository.save(newMedicine));
    }

    public MedicineResponse findById(final long id, final Principal principal, final long familyId) throws NotFoundException {
        validator.checkUserPermissionsOnFamily(principal, familyId);
        Medicine response = findEntity(id, familyId);
        return mapper.mapToResponse(response);
    }

    public MedicinesResponse findAll(final Principal principal, final Pageable pageable, final long familyId) throws NotFoundException {
        validator.checkUserPermissionsOnFamily(principal, familyId);
        Page<Medicine> medicine = repository.findByFamilyId(familyId, PageRequest.of(
                pageable.getPageNumber(),
                pageable.getPageSize(),
                pageable.getSortOr(Sort.by(Sort.Direction.ASC, "name"))));
        return mapper.mapToList(medicine);
    }

    // ToDo if(PAO != 0) change expiration Date to (request timestamp + PAO)
    @Transactional
    public void partialUpdate(final long id, final MedicineRequest request, final Principal principal, final long familyId) throws NotFoundException {
        validator.checkUserPermissionsOnFamily(principal, familyId);
        Medicine response = findEntity(id, familyId);
        if (request.name() != null) {response.setName(request.name());}
        if (request.expirationDate() != null) {response.setExpirationDate(request.expirationDate());}
        if (request.periodAfterOpening() != 0) {response.setPeriodAfterOpening(request.periodAfterOpening());}
        repository.save(response);
    }

    private Medicine findEntity(final long id, final long familyId) throws NotFoundException {
        Optional<Medicine> response = repository.findByIdAndFamilyId(id, familyId);
        return response.orElseThrow(NotFoundException:: new);
    }

    public void deleteById(final long id, final Principal principal, final long familyId) throws NotFoundException {
        validator.checkUserPermissionsOnFamily(principal, familyId);
        Medicine response = findEntity(id,  familyId);
        try {
            repository.deleteById(response.getId());
        } catch (DataIntegrityViolationException ic) {
            throw new DataIntegrityViolationException("Medicine connected to other object/objects");
        }
    }
}

package pl.zaprogramujzycie.mma.services;


import lombok.extern.slf4j.Slf4j;

import org.mapstruct.factory.Mappers;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.crossstore.ChangeSetPersister;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pl.zaprogramujzycie.mma.dto.request.MedicineRequest;
import pl.zaprogramujzycie.mma.dto.response.FamilyResponse;
import pl.zaprogramujzycie.mma.dto.response.MedicineResponse;
import pl.zaprogramujzycie.mma.dto.response.MedicinesResponse;
import pl.zaprogramujzycie.mma.entities.Medicine;
import pl.zaprogramujzycie.mma.exceptions.NotFoundException;
import pl.zaprogramujzycie.mma.repositories.MedicineRepository;
import pl.zaprogramujzycie.mma.utils.mappers.FamilyMapper;
import pl.zaprogramujzycie.mma.utils.mappers.MedicineMapper;

import java.security.Principal;
import java.util.Optional;

@Slf4j
@Service
public class MedicineService {

    private final MedicineRepository repository;
    private final FamilyService familyService;
    private final MedicineMapper mapper
            = Mappers.getMapper(MedicineMapper.class);
    private final FamilyMapper familyMapper
            = Mappers.getMapper(FamilyMapper.class);

    public MedicineService(MedicineRepository repository,
                           FamilyService familyService) {
        this.repository = repository;
        this.familyService = familyService;
    }

    @Transactional
    public MedicineResponse save(final MedicineRequest request, final Principal principal, final long familyId) throws NotFoundException {
        Medicine newMedicine = mapper.mapToEntity(request);
        FamilyResponse familyResponse = familyService.findById(principal, familyId);
        newMedicine.setFamily(familyMapper.mapResponseToEntity(familyResponse));
        return mapper.mapToResponse(repository.save(newMedicine));
    }

    public MedicineResponse findById(final long id, final Principal principal, final long familyId) throws NotFoundException {
        Medicine response = findEntity(id, principal, familyId);
        return mapper.mapToResponse(response);
    }

    public MedicinesResponse findAll(final Principal principal, final Pageable pageable, final long familyId) throws NotFoundException {
        FamilyResponse familyResponse = familyService.findById(principal, familyId);
        Page<Medicine> medicine = repository.findByFamily(familyMapper.mapResponseToEntity(familyResponse), PageRequest.of(
                pageable.getPageNumber(),
                pageable.getPageSize(),
                pageable.getSortOr(Sort.by(Sort.Direction.ASC, "name"))));
        return mapper.mapToList(medicine);
    }

    // ToDo if(PAO != 0) change expiration Date to (request timestamp + PAO)
    @Transactional
    public void partialUpdate(final long id, final MedicineRequest request, final Principal principal, final long familyId) throws ChangeSetPersister.NotFoundException {
        Medicine response = findEntity(id, principal, familyId);
        if (request.name() != null) {
            response.setName(request.name());
        }
        if (request.expirationDate() != null) {
            response.setExpirationDate(request.expirationDate());
        }
        if (request.periodAfterOpening() != 0) {
            response.setPeriodAfterOpening(request.periodAfterOpening());
        }
    }

    private Medicine findEntity(final long id, final Principal principal, final long familyId) throws NotFoundException {
        FamilyResponse familyResponse = familyService.findById(principal, familyId);
        Optional<Medicine> response = repository.findByIdAndFamily(id, familyMapper.mapResponseToEntity(familyResponse));
        return response.orElseThrow(NotFoundException:: new);
    }

    public void deleteById(final long id, final Principal principal, final long familyId) throws NotFoundException {
        familyService.findById(principal, familyId);
        Medicine response = findEntity(id, principal, familyId);
        try {
            repository.deleteById(response.getId());
        } catch (DataIntegrityViolationException ic) {
            throw new DataIntegrityViolationException("Medicine connected to other object/objects");
        }
    }
}

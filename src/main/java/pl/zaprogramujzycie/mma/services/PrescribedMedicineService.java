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
import pl.zaprogramujzycie.mma.dto.request.PrescribedMedicineRequest;
import pl.zaprogramujzycie.mma.dto.response.FamilyResponse;
import pl.zaprogramujzycie.mma.dto.response.PrescribedMedicineResponse;
import pl.zaprogramujzycie.mma.dto.response.PrescribedMedicinesResponse;
import pl.zaprogramujzycie.mma.entities.Medicine;
import pl.zaprogramujzycie.mma.entities.PrescribedMedicine;
import pl.zaprogramujzycie.mma.exceptions.NotFoundException;
import pl.zaprogramujzycie.mma.repositories.PrescribedMedicineRepository;
import pl.zaprogramujzycie.mma.utils.UserToFamilyValidator;
import pl.zaprogramujzycie.mma.utils.mappers.PrescribedMedicineMapper;
import java.security.Principal;
import java.util.Optional;

@Slf4j
@Service
public class PrescribedMedicineService {

    private final PrescribedMedicineRepository repository;
    private final MedicineService medicineService;
    private final UserToFamilyValidator validator;

    private final PrescribedMedicineMapper mapper
            = Mappers.getMapper(PrescribedMedicineMapper.class);

    PrescribedMedicineService(PrescribedMedicineRepository repository,
                              MedicineService medicineService,
                              UserToFamilyValidator validator) {
        this.repository = repository;
        this.medicineService = medicineService;
        this.validator = validator;
    }

    @Transactional
    public PrescribedMedicineResponse save(final PrescribedMedicineRequest request, final Principal principal,
                                           final long prescriptionId, final long familyId) throws NotFoundException {
        validator.checkUserPermissionsOnFamily(principal, familyId);

        PrescribedMedicine prescribedMedicine = mapper.mapToEntity(request);
        prescribedMedicine.setMedicineId(request.medicine());
        prescribedMedicine.setPrescriptionId(prescriptionId);

        return mapper.mapToResponse(repository.save(prescribedMedicine));
    }

    public PrescribedMedicineResponse findById(final long id, final long prescriptionId,
                                               final long familyId, final Principal principal) throws NotFoundException {
        validator.checkUserPermissionsOnFamily(principal, familyId);
        PrescribedMedicine response = findEntity(id, prescriptionId);
        return mapper.mapToResponse(response);
    }

    public PrescribedMedicinesResponse findAll(final Principal principal, final Pageable pageable, final long familyId, final long prescriptionId) throws NotFoundException {
        validator.checkUserPermissionsOnFamily(principal, familyId);
        Page<PrescribedMedicine> prescribedMedicine = repository.findByPrescriptionId(prescriptionId, PageRequest.of(
                pageable.getPageNumber(),
                pageable.getPageSize(),
                pageable.getSortOr(Sort.by(Sort.Direction.ASC, "id"))));
       return mapper.mapToList(prescribedMedicine);
    }

    public void partialUpdate(final long id, final PrescribedMedicineRequest request,
                                                    final Principal principal, final long prescriptionId,
                                                    final long familyId) throws NotFoundException {
        validator.checkUserPermissionsOnFamily(principal, familyId);
        PrescribedMedicine updatedEntity = findEntity(id, prescriptionId);
            if (request.medicine() != 0){updatedEntity.setMedicineId(request.medicine());}
            if (request.dosage() != 0){updatedEntity.setDosage(request.dosage());}
            if (request.numberOfDoses() != 0){updatedEntity.setNumberOfDoses(request.numberOfDoses());}
            if (request.administrationTimes() != null){updatedEntity.setAdministrationTimes(request.administrationTimes());}
            if (request.dosageInterval() != 0){updatedEntity.setDosageInterval(request.dosageInterval());}
            repository.save(updatedEntity);
    }
    @Transactional
    public void deleteById(final long id, final Principal principal,
                           final long familyId, final long prescriptionId) throws NotFoundException {
        validator.checkUserPermissionsOnFamily(principal, familyId);
        PrescribedMedicine response = findEntity(id, prescriptionId);
        repository.deleteById(response.getId());
    }

    @Transactional
    private PrescribedMedicine findEntity(final long id, final long prescriptionId) throws NotFoundException {
        Optional<PrescribedMedicine> response = repository.findByIdAndPrescriptionId(id, prescriptionId);
        return response.orElseThrow(NotFoundException:: new);
    }

}

package pl.zaprogramujzycie.mma.services;

import lombok.extern.slf4j.Slf4j;
import org.mapstruct.factory.Mappers;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
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

    private final PrescribedMedicineMapper mapper
            = Mappers.getMapper(PrescribedMedicineMapper.class);

    PrescribedMedicineService(PrescribedMedicineRepository repository, MedicineService medicineService) {
        this.repository = repository;
        this.medicineService = medicineService;
    }

    // public PrescribedMedicineResponse save(PrescribedMedicineRequest request, Principal principal) {
    //     Medicine medicine = findMatchingMedicine(request.medicineId());
    //     PrescribedMedicine prescribedMedicine = PrescribedMedicineMapper.mapToEntity(request, medicine);
    //     prescribedMedicine.setOwner(OwnerAssigner.assignFamilyAsOwner(principal));
    //     PrescribedMedicineResponse response = PrescribedMedicineMapper.mapToResponseDTO(repository.save(prescribedMedicine));
    //     return response;
    // }

@Transactional
    public PrescribedMedicineResponse findById(final long id, final long prescriptionId) throws NotFoundException {
        PrescribedMedicine response = findEntity(id, prescriptionId);
        return mapper.mapToResponse(response);
    }

    public PrescribedMedicinesResponse findAll(final Principal principal, final Pageable pageable, final long familyId, final long prescriptionId){
        Page<PrescribedMedicine> prescribedMedicine = repository.findByPrescriptionId(prescriptionId, PageRequest.of(
                pageable.getPageNumber(),
                pageable.getPageSize(),
                pageable.getSortOr(Sort.by(Sort.Direction.ASC, "id"))));
       return mapper.mapToList(prescribedMedicine);
    }



    // public PrescribedMedicinesResponse findAllById (List<PrescribedMedicine> medicines){
    //     List<Long> ids = new ArrayList<>();
    //     for(PrescribedMedicine medicine : medicines) {
    //         long id = medicine.getId();
    //         ids.add(id);
    //     }
    //     return new PrescribedMedicinesResponse(PrescribedMedicineMapper.mapToResponseDTOList(repository.findAllById(ids)));
    // }
    //
    // public PrescribedMedicineResponse partialUpdate(long id, PrescribedMedicineRequest request, Principal principal) {
    //     PrescribedMedicine updatedEntity = findEntity(id, principal);
    //     Medicine medicine = findMatchingMedicine(request.medicineId());
    //
    //     if (updatedEntity != null) {
    //         if (request.medicineId() != 0){updatedEntity.setMedicine(medicine);}
    //         if (request.dosage() != 0){updatedEntity.setDosage(request.dosage());}
    //         if (request.firstDose() != null){updatedEntity.setFirstDose(request.firstDose());}
    //         if (request.dosageInterval() != 0){updatedEntity.setDosageInterval(request.dosageInterval());}
    //         repository.save(updatedEntity);
    //         return PrescribedMedicineMapper.mapToResponseDTO(updatedEntity);
    //     }
    //     return null;
    // }
    //
    // public boolean deleteById(long id, Principal principal){
    //     if (repository.existsByIdAndOwner(id, principal.getName())) {
    //         repository.deleteById(id);
    //         return true;
    //     }
    //     return false;
    // }
    //
    private PrescribedMedicine findEntity(final long id, final long prescriptionId) throws NotFoundException {
        Optional<PrescribedMedicine> response = repository.findByIdAndPrescriptionId(id, prescriptionId);
        return response.orElseThrow(NotFoundException:: new);
    }

    //
    // private Medicine findMatchingMedicine(long medicineId){
    //     return medicineRepository.getReferenceById(medicineId);
    // }


}

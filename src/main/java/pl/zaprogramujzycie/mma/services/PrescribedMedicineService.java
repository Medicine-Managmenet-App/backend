package pl.zaprogramujzycie.mma.services;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import pl.zaprogramujzycie.mma.dto.request.PrescribedMedicineRequest;
import pl.zaprogramujzycie.mma.dto.response.PrescribedMedicineResponse;
import pl.zaprogramujzycie.mma.dto.response.PrescribedMedicinesResponse;

import pl.zaprogramujzycie.mma.entity.Medicine;
import pl.zaprogramujzycie.mma.entity.PrescribedMedicine;
import pl.zaprogramujzycie.mma.repository.MedicineRepository;
import pl.zaprogramujzycie.mma.repository.PrescribedMedicineRepository;
import pl.zaprogramujzycie.mma.utils.mappers.PrescribedMedicineMapper;

import java.security.Principal;

@Service
public class PrescribedMedicineService {

    private final PrescribedMedicineRepository repository;


    private final MedicineRepository medicineRepository;

    private PrescribedMedicineMapper mapper;

    public PrescribedMedicineService(PrescribedMedicineRepository repository, MedicineRepository medicineRepository) {
        this.repository = repository;
        this.medicineRepository = medicineRepository;
    }

    //ToDo add logic of assigning owner from Prescription id
    public PrescribedMedicineResponse save(PrescribedMedicineRequest request) {
        Medicine medicine = findMatchingMedicine(request.medicineId());
        System.out.println(request);
        System.out.println("---maching medicine----" + medicine);
        PrescribedMedicine prescribedMedicine = PrescribedMedicineMapper.mapToEntity(request, medicine);
        System.out.println("---does mapper work?----");
        PrescribedMedicineResponse response = PrescribedMedicineMapper.mapToResponseDTO(repository.save(prescribedMedicine));
        return response;
    }

    public PrescribedMedicineResponse findById(long id, Principal principal){
        PrescribedMedicine response = findEntity(id, principal);
        if (response != null) {
            return PrescribedMedicineMapper.mapToResponseDTO(response);
        } else {
            return null;
        }
    }
    //ToDo add logic for connecting principal with PrescribedMedicine through all connections
    public PrescribedMedicinesResponse findAll(Principal principal, Pageable pageable){
        Page<PrescribedMedicine> entityPage = repository.findByOwner(principal.getName(), PageRequest.of(
                pageable.getPageNumber(),
                pageable.getPageSize(),
                pageable.getSortOr(Sort.by(Sort.Direction.ASC, "id"))));
        Page<PrescribedMedicineResponse> page = PrescribedMedicineMapper.mapToResponseDTOPage(entityPage);
        PrescribedMedicinesResponse response = new PrescribedMedicinesResponse(page.getContent());
       return response;
    }

    public PrescribedMedicineResponse partialUpdate(long id, PrescribedMedicineRequest request, Principal principal) {
        System.out.println("--------service------");
        PrescribedMedicine updatedEntity = findEntity(id, principal);
        System.out.println("---updated entity: "+ updatedEntity);
        Medicine medicine = findMatchingMedicine(request.medicineId());

        if (updatedEntity != null) {
            if (request.medicineId() != 0){updatedEntity.setMedicine(medicine);}
            if (request.dosage() != 0){updatedEntity.setDosage(request.dosage());}
            if (request.firstDose() != null){updatedEntity.setFirstDose(request.firstDose());}
            if (request.dosageInterval() != 0){updatedEntity.setDosageInterval(request.dosageInterval());}
            repository.save(updatedEntity);
            return PrescribedMedicineMapper.mapToResponseDTO(updatedEntity);
        }
        return null;
    }

    public boolean deleteById(long id, Principal principal){
        if (repository.existsByIdAndOwner(id, principal.getName())) {
            repository.deleteById(id);
            return true;
        }
        return false;
    }

    private PrescribedMedicine findEntity(long id, Principal principal){
        return repository.findByIdAndOwner(id, principal.getName());
    }

    private Medicine findMatchingMedicine(long medicineId){
        return medicineRepository.getReferenceById(medicineId);
    }


}

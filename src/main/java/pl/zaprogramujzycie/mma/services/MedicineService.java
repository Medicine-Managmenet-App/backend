package pl.zaprogramujzycie.mma.services;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import pl.zaprogramujzycie.mma.dto.request.MedicineRequest;
import pl.zaprogramujzycie.mma.dto.response.MedicineResponse;
import pl.zaprogramujzycie.mma.dto.response.MedicinesResponse;
import pl.zaprogramujzycie.mma.entity.Medicine;
import pl.zaprogramujzycie.mma.repository.MedicineRepository;
import pl.zaprogramujzycie.mma.utils.mappers.MedicineMapper;

import java.security.Principal;

@Service
public class MedicineService {

    private final MedicineRepository repository;

    private MedicineMapper mapper;

    public MedicineService(MedicineRepository repository) {
        this.repository = repository;
    }
    //ToDo add logic of assigning owner form Family id
    public MedicineResponse save(MedicineRequest request) {
        MedicineResponse response = MedicineMapper.mapToMedicineDTO(repository.save(MedicineMapper.mapToMedicine(request)));
        return response;
    }

    public MedicineResponse findById(long id, Principal principal){
        Medicine response = findEntity(id, principal);
        if (response != null) {
            return MedicineMapper.mapToMedicineDTO(response);
        } else {
            return null;
        }
    }

    public MedicinesResponse findAll(Principal principal, Pageable pageable){
        Page<Medicine> medicine = repository.findByOwner(principal.getName(), PageRequest.of(
                pageable.getPageNumber(),
                pageable.getPageSize(),
                pageable.getSortOr(Sort.by(Sort.Direction.ASC, "name"))));
        Page<MedicineResponse> page = MedicineMapper.mapToResponsePage(medicine);
        MedicinesResponse response = new MedicinesResponse(page.getContent());
       return response;
    }

//ToDo if(PAO != 0) change expiration Date to (request timestamp + PAO)
    public MedicineResponse partialUpdate(long id, MedicineRequest request, Principal principal) {
        Medicine updatedMedicine = findEntity(id, principal);
        if (updatedMedicine != null) {
            if (request.name() != null){updatedMedicine.setName(request.name());}
            if (request.expirationDate() != null){updatedMedicine.setExpirationDate(request.expirationDate());}
            if (request.periodAfterOpening() != 0){updatedMedicine.setPeriodAfterOpening(request.periodAfterOpening());}
            repository.save(updatedMedicine);
            return MedicineMapper.mapToMedicineDTO(updatedMedicine);
        }
        return null;
    }

    private Medicine findEntity(long id, Principal principal){
        return repository.findByIdAndOwner(id, principal.getName());
    }
//ToDo create logic that takes care of error while deleting medicine connected to prescribed medicine
    public boolean deleteById(long id, Principal principal){
        if (repository.existsByIdAndOwner(id, principal.getName())) {
            repository.deleteById(id);
            return true;
        }
        return false;
    }


}

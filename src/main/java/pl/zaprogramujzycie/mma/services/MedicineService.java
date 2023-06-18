package pl.zaprogramujzycie.mma.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import org.springframework.web.util.UriComponentsBuilder;
import pl.zaprogramujzycie.mma.dto.request.MedicineRequest;
import pl.zaprogramujzycie.mma.dto.response.MedicineResponse;
import pl.zaprogramujzycie.mma.dto.response.MedicinesResponse;
import pl.zaprogramujzycie.mma.entity.Medicine;
import pl.zaprogramujzycie.mma.repository.MedicineRepository;
import pl.zaprogramujzycie.mma.utils.mappers.MedicineMapper;

import java.net.URI;
import java.security.Principal;
import java.time.OffsetDateTime;
import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class MedicineService {

    @Autowired
    private MedicineRepository repository;

    private MedicineMapper mapper;

    public ResponseEntity<MedicineResponse> save(MedicineRequest request) {
        MedicineResponse response = mapper.mapToMedicineDTO(repository.save(mapper.mapToMedicine(request)));
        return ResponseEntity.created(URI.create("/medicines/" + response.id())).body(response);
    }

    public ResponseEntity<MedicineResponse> findById(long id, Principal principal){
        Medicine response = findEntity(id, principal);
        if (response != null) {
            return ResponseEntity.ok(mapper.mapToMedicineDTO(response));
        } else {
            return ResponseEntity.notFound().build();
        }
    }



    public MedicinesResponse findAll(Principal principal, Pageable pageable){
        Page<Medicine> medicine = repository.findByOwner(principal.getName(), PageRequest.of(
                pageable.getPageNumber(),
                pageable.getPageSize(),
                pageable.getSortOr(Sort.by(Sort.Direction.ASC, "name"))));
        Page<MedicineResponse> page = mapper.mapToResponsePage(medicine);
        MedicinesResponse response = new MedicinesResponse(page.getContent());
       return response;
    }

//ToDo if(PAO != 0) change expiration Date to (request timestamp + PAO)
    public ResponseEntity<MedicineResponse> partialUpdate(long id, MedicineRequest request, Principal principal) {
        Medicine updatedMedicine = findEntity(id, principal);
        if (updatedMedicine != null) {
            if (request.name() != null){updatedMedicine.setName(request.name());}
            if (request.expirationDate() != null){updatedMedicine.setExpirationDate(request.expirationDate());}
            if (request.periodAfterOpening() != 0){updatedMedicine.setPeriodAfterOpening(request.periodAfterOpening());}
            repository.save(updatedMedicine);
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.notFound().build();
    }

    private Medicine findEntity(long id, Principal principal){
        return repository.findByIdAndOwner(id, principal.getName());
    }

    public ResponseEntity<Void> deleteById(long id, Principal principal){
        if (repository.existsByIdAndOwner(id, principal.getName())) {
            repository.deleteById(id);
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.notFound().build();
    }


}

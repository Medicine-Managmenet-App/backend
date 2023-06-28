package pl.zaprogramujzycie.mma.utils.mappers;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.springframework.data.domain.Page;
import pl.zaprogramujzycie.mma.dto.request.MedicineRequest;
import pl.zaprogramujzycie.mma.dto.response.MedicineResponse;
import pl.zaprogramujzycie.mma.dto.response.MedicinesResponse;
import pl.zaprogramujzycie.mma.entities.Medicine;

import java.util.ArrayList;
import java.util.List;


@Mapper(componentModel = "medicine")
public interface MedicineMapper {
    @Mapping(target="familyId", source="medicine.family.id")
    MedicineResponse mapToResponse (final Medicine medicine);
    Medicine mapToEntity (final MedicineRequest request);

    default MedicinesResponse mapToList (final Page<Medicine> page){
        List<MedicineResponse> medicinesList = new ArrayList<>();
        for (Medicine medicine: page) {
            medicinesList.add(mapToResponse(medicine));
        }
        return new MedicinesResponse(medicinesList);
    }

}

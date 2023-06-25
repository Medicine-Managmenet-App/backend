package pl.zaprogramujzycie.mma.utils.mappers;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.springframework.data.domain.Page;
import pl.zaprogramujzycie.mma.dto.request.MedicineRequest;
import pl.zaprogramujzycie.mma.dto.request.PrescribedMedicineRequest;
import pl.zaprogramujzycie.mma.dto.response.MedicineResponse;
import pl.zaprogramujzycie.mma.dto.response.PrescribedMedicineResponse;
import pl.zaprogramujzycie.mma.dto.response.PrescribedMedicinesResponse;
import pl.zaprogramujzycie.mma.entities.Medicine;
import pl.zaprogramujzycie.mma.entities.PrescribedMedicine;

import java.util.ArrayList;
import java.util.List;


@Mapper(componentModel = "medicine")
public interface PrescribedMedicineMapper {

    PrescribedMedicineResponse mapToResponse (final PrescribedMedicine prescribedMedicine);

    default PrescribedMedicine mapToEntity (final PrescribedMedicineRequest request){
        return new PrescribedMedicine(null, medicineEntityMapper(request.medicine()),
                request.dosage(), request.numberOfDoses(), request.dosageInterval(),
                request.administrationTimes());
    }


    default PrescribedMedicinesResponse mapToList (final Page<PrescribedMedicine> page){
        List<PrescribedMedicineResponse> medicinesList = new ArrayList<>();
        for (PrescribedMedicine medicine: page) {
            medicinesList.add(mapToResponse(medicine));
        }
        return new PrescribedMedicinesResponse(medicinesList);
    }

    @Mapping(target = "id", source = "medicine" )
    Medicine medicineEntityMapper (final Long medicine);

}

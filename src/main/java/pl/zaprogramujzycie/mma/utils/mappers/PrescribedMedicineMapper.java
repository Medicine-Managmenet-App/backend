package pl.zaprogramujzycie.mma.utils.mappers;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.springframework.data.domain.Page;
import pl.zaprogramujzycie.mma.dto.request.PrescribedMedicineRequest;
import pl.zaprogramujzycie.mma.dto.response.PrescribedMedicineResponse;
import pl.zaprogramujzycie.mma.dto.response.PrescribedMedicinesResponse;
import pl.zaprogramujzycie.mma.entities.Medicine;
import pl.zaprogramujzycie.mma.entities.PrescribedMedicine;

import java.util.ArrayList;
import java.util.List;


@Mapper(componentModel = "medicine")
public interface PrescribedMedicineMapper {


    @Mapping(target = "medicine", source = "medicineId")
    PrescribedMedicineResponse mapToResponse (final PrescribedMedicine prescribedMedicine);

    @Mapping(target = "medicineId", source = "medicine")
    default PrescribedMedicine mapToEntity (final PrescribedMedicineRequest request){
        return new PrescribedMedicine(null, medicineEntityMapper(request.medicine()),
                request.dosage(), request.numberOfDoses(), request.dosageInterval(),
                request.administrationTimes());
    }

    default PrescribedMedicine mapResponseToEntity (final PrescribedMedicineResponse response) {
        return new PrescribedMedicine(null, medicineEntityMapper(response.medicine()),
                response.dosage(), response.numberOfDoses(), response.dosageInterval(),
                response.administrationTimes());
    }

    default PrescribedMedicinesResponse mapToList (final Page<PrescribedMedicine> page){
        List<PrescribedMedicineResponse> medicinesList = new ArrayList<>();
        for (PrescribedMedicine medicine: page) {
            medicinesList.add(mapToResponse(medicine));
        }
        return new PrescribedMedicinesResponse(medicinesList);
    }

    default List<PrescribedMedicine> mapToEntityList (final List<PrescribedMedicineResponse> responseList) {
        List<PrescribedMedicine> entityList = new ArrayList<>();
        for (PrescribedMedicineResponse response : responseList) {
            entityList.add(mapResponseToEntity(response));
        }
        return entityList;
    }


    @Mapping(target = "id", source = "medicine" )
    Medicine medicineEntityMapper (final Long medicine);

}

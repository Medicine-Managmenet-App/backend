package pl.zaprogramujzycie.mma.utils.mappers;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import pl.zaprogramujzycie.mma.dto.request.MedicineRequest;
import pl.zaprogramujzycie.mma.dto.response.MedicineResponse;
import pl.zaprogramujzycie.mma.dto.response.MedicinesResponse;
import pl.zaprogramujzycie.mma.entity.Medicine;

import java.util.ArrayList;
import java.util.List;

public class MedicineMapper {

    public static MedicineResponse mapToMedicineDTO(Medicine medicine) {
        MedicineResponse medicineDTO = new MedicineResponse(
                medicine.getId(),
                medicine.getName(),
                medicine.getExpirationDate(),
                medicine.getPeriodAfterOpening(),
                medicine.getOwner()
        );
        return medicineDTO;
    }

    public static Medicine mapToMedicine(MedicineRequest medicineDTO){
        Medicine medicine = new Medicine(
                null,
                medicineDTO.name(),
                medicineDTO.expirationDate(),
                medicineDTO.periodAfterOpening(),
                medicineDTO.owner()
        );
        return medicine;
    }

    public static Page<MedicineResponse> mapToResponsePage (Page<Medicine> medicineList){
        List<MedicineResponse> listDTO = new ArrayList<>();
        for (Medicine medicine : medicineList){
            if(medicine != null){listDTO.add(mapToMedicineDTO(medicine));}
        }
        MedicinesResponse response = new MedicinesResponse(listDTO);
        Page<MedicineResponse> page = new PageImpl<>(listDTO) {
        };

        return page;
    }

    public static List<MedicineResponse> mapToMedicineDTOList (List<Medicine> medicineList){
        List<MedicineResponse> listDTO = new ArrayList<>();
        for (Medicine medicine : medicineList){
            listDTO.add(mapToMedicineDTO(medicine));
        }

        return listDTO;
    }
}

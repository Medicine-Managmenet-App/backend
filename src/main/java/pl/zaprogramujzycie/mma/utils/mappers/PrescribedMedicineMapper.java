package pl.zaprogramujzycie.mma.utils.mappers;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import pl.zaprogramujzycie.mma.dto.request.PrescribedMedicineRequest;
import pl.zaprogramujzycie.mma.dto.response.PrescribedMedicineResponse;
import pl.zaprogramujzycie.mma.dto.response.PrescribedMedicinesResponse;
import pl.zaprogramujzycie.mma.entity.Medicine;
import pl.zaprogramujzycie.mma.entity.PrescribedMedicine;

import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

public class PrescribedMedicineMapper {

    public static PrescribedMedicineResponse mapToResponseDTO(PrescribedMedicine entity) {
        List<LocalTime> administrationTimes = prepareAdministrationTimesList(entity.getFirstDose(), entity.getDosageInterval());
        PrescribedMedicineResponse dto = new PrescribedMedicineResponse(
                entity.getId(),
                entity.getMedicine().getId(),
                entity.getDosage(),
                administrationTimes,
                entity.getOwner()
        );
        System.out.println("-----new dto----" + dto);
        return dto;
    }

    public static PrescribedMedicine mapToEntity(PrescribedMedicineRequest dto, Medicine medicine){

        PrescribedMedicine entity = new PrescribedMedicine(
                null,
                medicine,
                dto.dosage(),
                dto.firstDose(),
                dto.dosageInterval(),
                dto.owner()
        );
        return entity;
    }

    public static Page<PrescribedMedicineResponse> mapToResponseDTOPage (Page<PrescribedMedicine> list) {
        List<PrescribedMedicineResponse> listDTO = new ArrayList<>();
        for (PrescribedMedicine item : list) {
            if (item != null) {
                listDTO.add(mapToResponseDTO(item));}
            }
            PrescribedMedicinesResponse response = new PrescribedMedicinesResponse(listDTO);
            Page<PrescribedMedicineResponse> page = new PageImpl<>(listDTO){};
            return page;
    }

    public static List<PrescribedMedicineResponse> mapToResponseDTOList (List<PrescribedMedicine> list){
        List<PrescribedMedicineResponse> listDTO = new ArrayList<>();
        for (PrescribedMedicine item : list){
            listDTO.add(mapToResponseDTO(item));
        }

        return listDTO;
    }

    private static List<LocalTime> prepareAdministrationTimesList(LocalTime firstDose, int interval) {
        LocalTime first = firstDose;
        int iner = interval;
        List<LocalTime> administrationTimes = new ArrayList<>();

        int numberOfDoses = 24/interval;
        for (int i = 0; i< numberOfDoses; i++) {
            administrationTimes.add(firstDose.plusHours((long) interval *i));
        }
        return administrationTimes;
    }

}

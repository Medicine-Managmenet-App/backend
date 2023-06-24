package pl.zaprogramujzycie.mma.utils.mappers;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import pl.zaprogramujzycie.mma.dto.request.PrescriptionRequest;
import pl.zaprogramujzycie.mma.dto.response.PrescriptionResponse;
import pl.zaprogramujzycie.mma.entities.PrescribedMedicine;
import pl.zaprogramujzycie.mma.entities.Prescription;

import java.util.ArrayList;
import java.util.List;


@Mapper(componentModel = "family")
public interface PrescriptionMapper {

    default PrescriptionResponse mapToResponse (Prescription prescription) {
        List<Long> medicinesIds = new ArrayList<>();
        List<PrescribedMedicine> medicines = prescription.getPrescribedMedicines();
        for (PrescribedMedicine pM : medicines) {
            medicinesIds.add(pM.getId());
        }
        return new PrescriptionResponse(prescription.getId(),medicinesIds);
    }

    default Prescription mapToEntity (PrescriptionRequest request) {
        List<Long> medicinesIds = request.medicines();
        Prescription prescription = new Prescription();
        List<PrescribedMedicine> medicines = new ArrayList<>();
        for(long id : medicinesIds) {
            medicines.add(mapToPrescribedMedicine(id));
        }
        prescription.setPrescribedMedicines(medicines);
        return prescription;
    }

    default Prescription mapToEntity (PrescriptionResponse request) {
        List<Long> medicinesIds = request.prescribedMedicines();
        Prescription prescription = new Prescription();
        List<PrescribedMedicine> medicines = new ArrayList<>();
        for(long id : medicinesIds) {
            medicines.add(mapToPrescribedMedicine(id));
        }
        prescription.setPrescribedMedicines(medicines);
        return prescription;
    }

    @Mapping(target = "id", source = "id")
    PrescribedMedicine mapToPrescribedMedicine (Long id);


}

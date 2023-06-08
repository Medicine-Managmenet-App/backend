package pl.zaprogramujzycie.mma.utils;

import pl.zaprogramujzycie.mma.entity.Medicine;
import pl.zaprogramujzycie.mma.repositories.MedicineRepository;

import java.time.ZonedDateTime;
import java.util.Optional;

public class MedicineService {

    private MedicineRepository medicineRepository;

    public void partialUpdate(long id, String name, ZonedDateTime date, long periodAfterOpening) {
        Optional<Medicine> optional = medicineRepository.findById(id);
        if (optional.isPresent()) {
            Medicine medicine = optional.get();
            if (name != null) {
                medicine.setName(name);
            }
            if (date != null) {
                medicine.setExpirationDate(date);
            }
            if (periodAfterOpening != 0) {
                medicine.setPeriodAfterOpening(Long.valueOf(periodAfterOpening));
            }
        }
    }
}

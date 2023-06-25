package pl.zaprogramujzycie.mma.entities;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalTime;
import java.time.OffsetDateTime;
import java.util.List;
import java.util.Objects;

@Entity
@Table(name = "prescribed_medicines")
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
public class PrescribedMedicine {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @ManyToOne
    @JoinColumn(referencedColumnName = "id")
    private Medicine medicine;
    private double dosage;
    private int numberOfDoses;
    private int dosageInterval;
    @ElementCollection
    private List<LocalTime> administrationTimes;

    @ManyToOne
    @JoinColumn(name = "prescription_id", insertable = false, updatable = false)
    private Prescription prescription;

    @Column(name = "prescription_id")
    private long prescriptionId;

    public PrescribedMedicine(Long id, Medicine medicine, double dosage, int numberOfDoses, int dosageInterval, List<LocalTime> administrationTimes) {
        this.id = id;
        this.medicine = medicine;
        this.dosage = dosage;
        this.numberOfDoses = numberOfDoses;
        this.dosageInterval = dosageInterval;
        this.administrationTimes = administrationTimes;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        final PrescribedMedicine that = (PrescribedMedicine) o;
        return Double.compare(that.dosage, dosage) == 0 && numberOfDoses == that.numberOfDoses && dosageInterval == that.dosageInterval && Objects.equals(id, that.id) && Objects.equals(medicine, that.medicine) && Objects.equals(administrationTimes, that.administrationTimes) && Objects.equals(prescription, that.prescription);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, medicine, dosage, numberOfDoses, dosageInterval, administrationTimes, prescription);
    }
}

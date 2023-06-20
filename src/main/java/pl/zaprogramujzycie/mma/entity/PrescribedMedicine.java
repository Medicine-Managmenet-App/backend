package pl.zaprogramujzycie.mma.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalTime;
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
    private LocalTime firstDose;
    private int dosageInterval;
    private String owner;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        final PrescribedMedicine that = (PrescribedMedicine) o;
        return Double.compare(that.dosage, dosage) == 0 && dosageInterval == that.dosageInterval && Objects.equals(id, that.id) && Objects.equals(medicine, that.medicine) && Objects.equals(firstDose, that.firstDose) && Objects.equals(owner, that.owner);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, medicine, dosage, firstDose, dosageInterval, owner);
    }
}

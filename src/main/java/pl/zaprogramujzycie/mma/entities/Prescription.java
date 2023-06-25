package pl.zaprogramujzycie.mma.entities;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import pl.zaprogramujzycie.mma.entities.PrescribedMedicine;

import javax.persistence.*;
import java.util.List;
import java.util.Objects;

@Entity
@Table(name = "prescriptions")
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
public class Prescription {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "prescription")
    private List<PrescribedMedicine> prescribedMedicines;

    private Long familyId;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        final Prescription that = (Prescription) o;
        return Objects.equals(id, that.id) && Objects.equals(prescribedMedicines, that.prescribedMedicines) && Objects.equals(familyId, that.familyId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, prescribedMedicines, familyId);
    }
}

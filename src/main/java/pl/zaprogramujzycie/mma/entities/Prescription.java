package pl.zaprogramujzycie.mma.entities;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.Table;
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
    @OneToMany
    @JoinColumn(referencedColumnName = "id")
    private List<Medicine> medicines;
    @OneToMany
    @JoinColumn(referencedColumnName = "id")
    private List<PrescribedMedicine> prescribedMedicines;

    @Override
    public boolean equals(final Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        final Prescription that = (Prescription) o;
        return Objects.equals(id, that.id) && Objects.equals(medicines, that.medicines) && Objects.equals(prescribedMedicines, that.prescribedMedicines);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, medicines, prescribedMedicines);
    }
}

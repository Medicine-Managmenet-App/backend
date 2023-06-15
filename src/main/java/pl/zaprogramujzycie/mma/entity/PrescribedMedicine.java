package pl.zaprogramujzycie.mma.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
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
    private List<OffsetDateTime> administrationTimes;

    @Override
    public boolean equals(final Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        final PrescribedMedicine that = (PrescribedMedicine) o;
        return Double.compare(that.dosage, dosage) == 0 && Objects.equals(id, that.id) && Objects.equals(medicine, that.medicine) && Objects.equals(administrationTimes, that.administrationTimes);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, medicine, dosage, administrationTimes);
    }
}

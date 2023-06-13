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
import javax.persistence.OneToMany;
import javax.persistence.Table;
import java.util.List;
import java.util.Objects;

@Entity
@Table(name = "prescribed_medicines")
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
public class PrescribedMedicines {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToMany
    @JoinColumn(referencedColumnName = "id")
    private List<Medicine> medicines;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        PrescribedMedicines that = (PrescribedMedicines) o;
        return Objects.equals(id, that.id) && Objects.equals(medicines, that.medicines);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, medicines);
    }
}

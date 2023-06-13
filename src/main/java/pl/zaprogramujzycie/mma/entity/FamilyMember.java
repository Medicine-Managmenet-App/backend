package pl.zaprogramujzycie.mma.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import java.util.Objects;

@Entity
@Table(name = "family_members")
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
public class FamilyMember {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(unique = true)
    private String name;
    private boolean isChild;
    @OneToOne
    private PrescribedMedicines prescribedMedicines;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        FamilyMember that = (FamilyMember) o;
        return isChild == that.isChild && Objects.equals(id, that.id) && Objects.equals(name, that.name) && Objects.equals(prescribedMedicines, that.prescribedMedicines);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, isChild, prescribedMedicines);
    }
}

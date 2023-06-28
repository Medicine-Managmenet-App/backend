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

    @OneToMany(mappedBy = "prescription", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<PrescribedMedicine> prescribedMedicines;

    @OneToOne
    @JoinColumn(name = "family_member_id", insertable = false, updatable = false, referencedColumnName = "id")
    private FamilyMember familyMember;

    @Column(name = "family_member_id")
    private Long familyMemberId;


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        final Prescription that = (Prescription) o;
        return Objects.equals(id, that.id) && Objects.equals(prescribedMedicines, that.prescribedMedicines) && Objects.equals(familyMember, that.familyMember) && Objects.equals(familyMemberId, that.familyMemberId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, prescribedMedicines, familyMember, familyMemberId);
    }
}

package pl.zaprogramujzycie.mma.entities;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
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
    @Column(unique = true, nullable = false)
    private String name;
    @Column(nullable = false)
    private boolean isChild;
    @OneToOne
    private Prescription prescription;
    @ManyToOne
    private Family family;

    @Override
    public boolean equals(final Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        final FamilyMember that = (FamilyMember) o;
        return isChild == that.isChild && Objects.equals(id, that.id) && Objects.equals(name, that.name) && Objects.equals(prescription, that.prescription) && Objects.equals(family, that.family);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, isChild, prescription, family);
    }
}

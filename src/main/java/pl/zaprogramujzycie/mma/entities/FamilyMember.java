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
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
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
    @Column(name = "id")
    private Long id;
    @Column(unique = true, nullable = false)
    private String name;
    @Column(name = "is_child")
    private int isChild;

    private int age;

    private double weight;

    @ManyToOne
    @JoinColumn(name = "family_id", insertable = false, updatable = false)
    private Family family;

    @Column(name = "family_id")
    private Long familyId;

    public FamilyMember(Long id, String name, int isChild, int age, double weight, Long familyId) {
        this.id = id;
        this.name = name;
        this.isChild = isChild;
        this.age = age;
        this.weight = weight;
        this.familyId = familyId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        final FamilyMember that = (FamilyMember) o;
        return isChild == that.isChild && age == that.age && Double.compare(that.weight, weight) == 0 && Objects.equals(id, that.id) && Objects.equals(name, that.name) && Objects.equals(family, that.family) && Objects.equals(familyId, that.familyId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, isChild, age, weight, family, familyId);
    }
}

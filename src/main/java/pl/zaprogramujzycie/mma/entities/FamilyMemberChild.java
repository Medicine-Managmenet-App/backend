package pl.zaprogramujzycie.mma.entities;

import lombok.Data;

import javax.persistence.*;
import java.util.Objects;

@Entity
@Data
@Table(name = "family_members_child")
public class FamilyMemberChild extends FamilyMember {

    @Column
    private int age;
    @Column
    private float weight;

    @Override
    public boolean equals(final Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        final FamilyMemberChild that = (FamilyMemberChild) o;
        return age == that.age && Float.compare(that.weight, weight) == 0;
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), age, weight);
    }
}

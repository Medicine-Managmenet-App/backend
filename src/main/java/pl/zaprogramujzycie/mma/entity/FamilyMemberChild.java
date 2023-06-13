package pl.zaprogramujzycie.mma.entity;

import lombok.Data;

import javax.persistence.Entity;
import java.util.Objects;

@Entity
@Data
public class FamilyMemberChild extends FamilyMember {

    private int age;
    private float weight;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        FamilyMemberChild that = (FamilyMemberChild) o;
        return age == that.age && Float.compare(that.weight, weight) == 0;
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), age, weight);
    }
}

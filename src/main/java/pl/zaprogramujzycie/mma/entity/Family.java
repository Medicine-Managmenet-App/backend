package pl.zaprogramujzycie.mma.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import java.util.List;
import java.util.Objects;

@Entity
@Table(name = "families")
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
public class Family {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @OneToMany(mappedBy = "family")
    private List<FamilyMember> members;
    @OneToMany(mappedBy = "family")
    private List<User> users;

    @Override
    public boolean equals(final Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        final Family family = (Family) o;
        return Objects.equals(id, family.id) && Objects.equals(members, family.members) && Objects.equals(users, family.users);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, members, users);
    }
}

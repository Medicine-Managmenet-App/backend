package pl.zaprogramujzycie.mma.entities;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
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

    @ElementCollection(fetch = FetchType.EAGER)
    private List<String> logins;

    @OneToMany(mappedBy = "family")
    private List<Medicine> medicines;


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        final Family family = (Family) o;
        return Objects.equals(id, family.id) && Objects.equals(members, family.members) && Objects.equals(logins, family.logins) && Objects.equals(medicines, family.medicines);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, members, logins, medicines);
    }
}

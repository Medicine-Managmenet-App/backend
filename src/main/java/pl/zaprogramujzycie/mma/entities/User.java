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
import javax.persistence.Table;
import java.util.Objects;

@Entity
@Table(name = "users")
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(unique = true)
    private String login;
    private String password;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name= "family_id", insertable = false, updatable = false)
    private Family family;

    @Column(name = "family_id")
    private Long familyId;

    public User(Long id, String login, String password, Long familyId) {
        this.id = id;
        this.login = login;
        this.password = password;
        this.familyId = familyId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        final User user = (User) o;
        return Objects.equals(id, user.id) && Objects.equals(login, user.login) && Objects.equals(password, user.password) && Objects.equals(family, user.family);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, login, password, family);
    }
}

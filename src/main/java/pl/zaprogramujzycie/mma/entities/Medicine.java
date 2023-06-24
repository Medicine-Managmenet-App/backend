package pl.zaprogramujzycie.mma.entities;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import javax.persistence.*;
import java.time.OffsetDateTime;
import java.util.Objects;

@Entity
@Table(name = "medicines")
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
public class Medicine {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(unique = true, nullable = false)
    private String name;
    @Column(nullable = false)
    private OffsetDateTime expirationDate;
    private int periodAfterOpening;
    @ManyToOne
    private Family family;


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        final Medicine medicine = (Medicine) o;
        return periodAfterOpening == medicine.periodAfterOpening && Objects.equals(id, medicine.id) && Objects.equals(name, medicine.name) && Objects.equals(expirationDate, medicine.expirationDate) && Objects.equals(family, medicine.family);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, expirationDate, periodAfterOpening, family);
    }
}

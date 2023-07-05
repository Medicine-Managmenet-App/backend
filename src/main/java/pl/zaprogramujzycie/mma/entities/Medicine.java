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
import javax.persistence.Table;
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
    @JoinColumn(name = "family_id", insertable = false, updatable = false)
    private Family family;

    @Column(name = "family_id")
    private Long familyId;

    public Medicine(Long id, String name, OffsetDateTime expirationDate, int periodAfterOpening, Long familyId) {
        this.id = id;
        this.name = name;
        this.expirationDate = expirationDate;
        this.periodAfterOpening = periodAfterOpening;
        this.familyId = familyId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        final Medicine medicine = (Medicine) o;
        return periodAfterOpening == medicine.periodAfterOpening && Objects.equals(id, medicine.id) && Objects.equals(name, medicine.name) && Objects.equals(expirationDate, medicine.expirationDate) && Objects.equals(family, medicine.family) && Objects.equals(familyId, medicine.familyId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, expirationDate, periodAfterOpening, family, familyId);
    }
}

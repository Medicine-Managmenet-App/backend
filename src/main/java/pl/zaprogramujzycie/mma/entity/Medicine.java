package pl.zaprogramujzycie.mma.entity;

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
import java.util.List;
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

    String owner;

    @Override
    public boolean equals(final Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        final Medicine medicine = (Medicine) o;
        return periodAfterOpening == medicine.periodAfterOpening && Objects.equals(id, medicine.id) && Objects.equals(name, medicine.name) && Objects.equals(expirationDate, medicine.expirationDate);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, expirationDate, periodAfterOpening);
    }
}

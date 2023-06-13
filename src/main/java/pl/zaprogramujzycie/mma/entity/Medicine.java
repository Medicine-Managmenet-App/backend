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
    @Column(unique = true)
    private String name;
    private OffsetDateTime expirationDate;
    private int periodAfterOpening;
    private double dosage;
    private OffsetDateTime administrationTime;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Medicine medicine = (Medicine) o;
        return periodAfterOpening == medicine.periodAfterOpening && Double.compare(medicine.dosage, dosage) == 0 && Objects.equals(id, medicine.id) && Objects.equals(name, medicine.name) && Objects.equals(expirationDate, medicine.expirationDate) && Objects.equals(administrationTime, medicine.administrationTime);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, expirationDate, periodAfterOpening, dosage, administrationTime);
    }
}

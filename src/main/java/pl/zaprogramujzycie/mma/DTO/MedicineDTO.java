package pl.zaprogramujzycie.mma.DTO;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.format.annotation.DateTimeFormat;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import java.time.OffsetDateTime;
import java.util.List;
import java.util.Objects;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class MedicineDTO {

    private Long id;

    @NotNull
    @Pattern(regexp="^[a-zA-Z,0-9- ]*$")
    @Size(min = 3, max = 32)
    private String name;

    @NotNull
    private OffsetDateTime expirationDate;

    @Pattern(regexp="^[0-9]*$")
    private int periodAfterOpening;

    @Pattern(regexp="^[0-9.]*$")
    private double dosage;

    @Pattern(regexp="^[0-9-]*$")
    @DateTimeFormat(pattern = "hh-mm")
    private List<OffsetDateTime> administrationTime;

    public MedicineDTO(String name, OffsetDateTime expirationDate, int periodAfterOpening) {
        this.name = name;
        this.expirationDate = expirationDate;
        this.periodAfterOpening = periodAfterOpening;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        final MedicineDTO that = (MedicineDTO) o;
        return Objects.equals(id, that.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}

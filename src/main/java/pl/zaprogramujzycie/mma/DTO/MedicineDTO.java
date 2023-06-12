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
    private Long periodAfterOpening;

    @Pattern(regexp="^[0-9.]*$")
    private double dosage;

    @Pattern(regexp="^[0-9-]*$")
    @DateTimeFormat(pattern = "hh-mm")
    private OffsetDateTime administrationTime;

}

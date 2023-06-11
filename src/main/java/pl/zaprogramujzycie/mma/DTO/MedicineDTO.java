package pl.zaprogramujzycie.mma.DTO;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import java.time.ZonedDateTime;
import java.util.List;

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
    private ZonedDateTime expirationDate;

    @Pattern(regexp="^[0-9]*$")
    private Long periodAfterOpening;

}

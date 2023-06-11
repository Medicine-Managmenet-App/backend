package pl.zaprogramujzycie.mma.DTO;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.format.annotation.DateTimeFormat;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class PrescribedMedicineDTO extends MedicineDTO{

    private Long id;

    @NotNull
    @Pattern(regexp="^[0-9.]*$")
    private double dosage;

    @NotNull
    @Pattern(regexp="^[0-9-]*$")
    @DateTimeFormat(pattern = "mm-yyyy")
    private String administrationTime;

}

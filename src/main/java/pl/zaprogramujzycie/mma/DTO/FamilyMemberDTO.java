package pl.zaprogramujzycie.mma.DTO;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class FamilyMemberDTO {

    private Long id;

    @NotNull
    @Pattern(regexp="^[a-zA-Z- ]*$")
    @Size(min = 3, max = 20)
    private String name;

    @NotNull
    private boolean isChild;

    private PrescriptionDTO prescription;

}

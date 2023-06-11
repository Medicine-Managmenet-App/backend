package pl.zaprogramujzycie.mma.DTO;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class ChildDTO extends FamilyMemberDTO{


    @NotNull
    @Pattern(regexp="^[0-9]*$")
    private int age;

    @NotNull
    @Pattern(regexp="^[0-9.,]*$")
    private double weight;

}

package pl.zaprogramujzycie.mma.DTO;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import java.util.Objects;

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

    private PrescribedMedicinesDTO prescribedMedicines;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        final FamilyMemberDTO that = (FamilyMemberDTO) o;
        return Objects.equals(id, that.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}

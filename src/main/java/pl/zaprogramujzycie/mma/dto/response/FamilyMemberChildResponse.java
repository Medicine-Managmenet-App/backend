package pl.zaprogramujzycie.mma.dto.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class FamilyMemberChildResponse extends FamilyMemberResponse {
    private long id;
    private String name;
    private boolean isChild;
    int age;
    double weight;

    @Override
    public int getAge(){
        return age;
    }

    @Override
    public double getWeight(){
        return weight;
    }
}

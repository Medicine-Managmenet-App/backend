package pl.zaprogramujzycie.mma.dto.request;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class FamilyMemberChildRequest extends FamilyMemberRequest {
    private int age;
    private int weight;

    public FamilyMemberChildRequest(String name, boolean isChild, int age, int weight) {
        super(name, isChild);
        this.age = age;
        this.weight = weight;
    }

    @Override
    public int getAge(){
        return age;
    }

    @Override
    public double getWeight(){
        return weight;
    }
}

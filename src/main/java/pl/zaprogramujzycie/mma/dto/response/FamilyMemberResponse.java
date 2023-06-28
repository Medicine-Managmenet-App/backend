package pl.zaprogramujzycie.mma.dto.response;

import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Getter
@Setter
@JsonTypeInfo(use = JsonTypeInfo.Id.NAME, property = "isChild")
@JsonSubTypes({
        @JsonSubTypes.Type(value = FamilyMemberResponse.class, name = "false"),
        @JsonSubTypes.Type(value = FamilyMemberChildResponse.class, name = "true"),
})
public class FamilyMemberResponse {

    private long id;
    private String name;
    private boolean isChild;

    public FamilyMemberResponse(long id, String name, boolean isChild) {
        this.id = id;
        this.name = name;
        this.isChild = isChild;
    }
    public int getAge(){
        return 0;
    }

    public double getWeight(){
        return 0;
    }
}

package pl.zaprogramujzycie.mma.dto.request;

import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@JsonTypeInfo(use = JsonTypeInfo.Id.NAME, property = "isChild", visible = true)
@JsonSubTypes({
        @JsonSubTypes.Type(value = FamilyMemberRequest.class, name = "false"),
        @JsonSubTypes.Type(value = FamilyMemberChildRequest.class, name = "true")
})
@Getter
@Setter
@NoArgsConstructor
public class FamilyMemberRequest {

        @NotBlank
        private String name;
        @NotNull
        private boolean isChild;

        public FamilyMemberRequest(String name, boolean isChild) {
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

package pl.zaprogramujzycie.mma.DTO;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import java.time.ZonedDateTime;

@NoArgsConstructor
public class UserDTO {

    @Getter
    private Long id;

    @Getter
    @Setter
    @NotNull(message = "{constraint.notnull}")
    @Size(min = 8, max = 32, message = "{constraint.string.length.notinrange}")
    @Pattern(regexp = "^[_a-zA-Z0-9-]*$", message = "{constraint.string.incorrectchar}")
    private String login;

    @Getter
    @Setter
    @NotNull(message = "{constraint.notnull}")
    @Size(min = 8, max = 32, message = "{constraint.string.length.notinrange}")
    @Pattern(regexp = "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[@$!%*?&_])[A-Za-z\\d@$!%*?&_]*$", message = "{constraint.string.incorrectcharPassword}")
    private String password;




    public UserDTO(String login, String password) {
        this.login = login;
        this.password = password;
    }
    //ToDo: add confirmation method
}

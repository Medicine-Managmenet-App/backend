package pl.zaprogramujzycie.mma.DTO;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.ZonedDateTime;

@NoArgsConstructor
public class MedicineDTO {

    @Getter
    private Long id;

    @Getter
    @Setter
    private String name;

    @Getter
    @Setter
    private ZonedDateTime expirationDate;

    @Getter
    @Setter
    private Long periodAfterOpening;


    public MedicineDTO(String name, ZonedDateTime expirationDate, Long PAO) {
        this.name = name;
        this.expirationDate = expirationDate;
        this.periodAfterOpening = PAO;
    }
}

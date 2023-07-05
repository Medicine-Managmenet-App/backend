package pl.zaprogramujzycie.mma.controllers;

import org.assertj.core.util.Arrays;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.json.JsonTest;
import org.springframework.boot.test.json.JacksonTester;
import pl.zaprogramujzycie.mma.dto.response.MedicineResponse;

import java.io.IOException;
import java.time.OffsetDateTime;

import static org.assertj.core.api.Assertions.assertThat;


@JsonTest
public class MedicineJsonTest {

    @Autowired
    private JacksonTester<MedicineResponse> json;

    @Autowired
    private JacksonTester<MedicineResponse[]> jsonList;

    private MedicineResponse[] medicineDTOs;



    @BeforeEach
    void setUp() {
        medicineDTOs = Arrays.array(
                new MedicineResponse(100L, "med1", OffsetDateTime.parse("2023-06-15T21:27:17.289601+02:00"), 6, 100),
                new MedicineResponse(101L, "med2", OffsetDateTime.parse("2023-06-15T21:27:17.289601+02:00"), 6, 100),
                new MedicineResponse(102L, "med3", OffsetDateTime.parse("2023-06-15T21:27:17.289601+02:00"), 6, 100));

    }

    @Test
    public void medicineDTOSerializationTest() throws IOException {
        MedicineResponse medicineDto = medicineDTOs[0];
        assertThat(json.write(medicineDto)).isStrictlyEqualToJson("single.json");
        assertThat(json.write(medicineDto)).hasJsonPathNumberValue("@.id");
        assertThat(json.write(medicineDto)).extractingJsonPathNumberValue("@.id")
                .isEqualTo(100);
        assertThat(json.write(medicineDto)).hasJsonPathStringValue("@.name");
        assertThat(json.write(medicineDto)).extractingJsonPathStringValue("@.name")
                .isEqualTo("med1");
        assertThat(json.write(medicineDto)).hasJsonPathStringValue("@.expirationDate");
        assertThat(json.write(medicineDto)).extractingJsonPathStringValue("@.expirationDate")
                .isEqualTo("2023-06-15T21:27:17.289601+02:00");
        assertThat(json.write(medicineDto)).hasJsonPathNumberValue("@.familyId");
        assertThat(json.write(medicineDto)).extractingJsonPathNumberValue("@.familyId")
                .isEqualTo(100);
    }

    @Disabled("TODO: Needs custom deserializer")
    @Test
    public void medicineDeserializationTest() throws IOException {
        String expected = """
                [
                    {"id": 100, "name": "med1", "expirationDate": "2023-06-15T21:27:17.289601+02:00",  "periodAfterOpening": 6, "familyId": 100},
                    {"id": 101, "name": "med2", "expirationDate": "2023-06-15T21:27:17.289601+02:00",  "periodAfterOpening": 6, "familyId": 100},
                    {"id": 102, "name": "med3", "expirationDate": "2023-06-15T21:27:17.289601+02:00",  "periodAfterOpening": 6, "familyId": 100}
                ]
                """;
        assertThat(jsonList.parse(expected)).isEqualTo(medicineDTOs);
    }
}

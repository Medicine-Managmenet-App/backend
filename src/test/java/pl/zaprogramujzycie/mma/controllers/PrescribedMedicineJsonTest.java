package pl.zaprogramujzycie.mma.controllers;

import org.assertj.core.util.Arrays;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.json.JsonTest;
import org.springframework.boot.test.json.JacksonTester;
import pl.zaprogramujzycie.mma.dto.response.PrescribedMedicineResponse;

import java.io.IOException;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;


@JsonTest
public class PrescribedMedicineJsonTest {

    @Autowired
    private JacksonTester<PrescribedMedicineResponse> json;

    @Autowired
    private JacksonTester<PrescribedMedicineResponse[]> jsonList;

    private PrescribedMedicineResponse[] medicineDTOs;

    final LocalTime[] times  = new LocalTime[3];
    final List<LocalTime> timesList = new ArrayList<>();


    @BeforeEach
    void setUp() {

        String timeColonPattern = "HH:mm:ss";
        DateTimeFormatter timeColonFormatter = DateTimeFormatter.ofPattern("HH:mm:ss");

        times[0] = LocalTime.of(8, 00, 00);
        times[1] = LocalTime.of(16, 00, 00);
        times[2] = LocalTime.of(00, 00, 00);

        timesList.add(LocalTime.of(8, 00, 00));
        timesList.add(LocalTime.of(16, 00, 00));
        timesList.add(LocalTime.of(00, 00, 00));

        medicineDTOs = Arrays.array(
                new PrescribedMedicineResponse(100L, 100L, 1.0, timesList, "100"),
                new PrescribedMedicineResponse(101L, 101L, 1.0, timesList, "100"),
                new PrescribedMedicineResponse(102L, 102L, 1.0, timesList, "102"));
    }

    @Test
    public void medicineDTOSerializationTest() throws IOException {
        PrescribedMedicineResponse medicineDto = medicineDTOs[0];
        assertThat(json.write(medicineDto)).isStrictlyEqualToJson("prescribedSingle.json");
        assertThat(json.write(medicineDto)).hasJsonPathNumberValue("@.id");
        assertThat(json.write(medicineDto)).extractingJsonPathNumberValue("@.id")
                .isEqualTo(100);
        assertThat(json.write(medicineDto)).hasJsonPathNumberValue("@.medicineId");
        assertThat(json.write(medicineDto)).extractingJsonPathNumberValue("@.medicineId")
                .isEqualTo(100);
        assertThat(json.write(medicineDto)).hasJsonPathArrayValue("@.administrationTimes");
        System.out.println(json.write(medicineDto).toString());
        assertThat(json.write(medicineDto)).extractingJsonPathArrayValue("@.administrationTimes")
                .isEqualTo(timesList);
        assertThat(json.write(medicineDto)).hasJsonPathNumberValue("@.dosage");
        assertThat(json.write(medicineDto)).extractingJsonPathNumberValue("@.dosage")
                .isEqualTo(1.0);
    }

    @Test
    public void medicineDeserializationTest() throws IOException {
        String expected = """
                [
                    {"id": 100, "medicineId": 100, "dosage": 1.0, "administrationTimes":  ["08:00:00", "16:00:00", "00:00:00"],"owner":  "100"},
                    {"id": 101, "medicineId": 101, "dosage": 1.0, "administrationTimes":  ["08:00:00", "16:00:00", "00:00:00"], "owner":  "100"},
                    {"id": 102, "medicineId": 102, "dosage": 1.0, "administrationTimes":  ["08:00:00", "16:00:00", "00:00:00"], "owner":  "101"}
                ]
                """;
        assertThat(jsonList.parse(expected)).isEqualTo(medicineDTOs);
    }
}

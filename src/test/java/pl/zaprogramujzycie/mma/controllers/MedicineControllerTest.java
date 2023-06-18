package pl.zaprogramujzycie.mma.controllers;

import com.jayway.jsonpath.DocumentContext;
import com.jayway.jsonpath.JsonPath;
import net.minidev.json.JSONArray;
import org.flywaydb.core.Flyway;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.*;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.web.util.UriComponentsBuilder;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.annotation.DirtiesContext;
import pl.zaprogramujzycie.mma.dto.request.MedicineRequest;
import pl.zaprogramujzycie.mma.dto.response.MedicineResponse;
import pl.zaprogramujzycie.mma.dto.response.MedicinesResponse;

import java.net.URI;
import java.time.OffsetDateTime;
import java.util.ArrayList;
import java.util.List;
import static org.assertj.core.api.Assertions.assertThat;


@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
public class MedicineControllerTest {

    @Autowired
    TestRestTemplate restTemplate;


    private MedicineResponse medicineDTO;

    private List<MedicineResponse> medicineDTOs = new ArrayList<>();


    @BeforeEach
    public void setup()
    {
        var medDTO1 = new MedicineResponse(100L, "Med1", OffsetDateTime.parse("2023-06-15T21:27:17.289601+02"), 6, "100");
        var medDTO2 = new MedicineResponse(101L, "Med2", OffsetDateTime.parse("2023-06-15T21:27:17.289601+02"), 6, "100");
        var medDTO3 = new MedicineResponse(102L, "Med3", OffsetDateTime.parse("2023-06-15T21:27:17.289601+02"), 6, "100");
        this.medicineDTO = medDTO1;
        medicineDTOs.add(medDTO1);
        medicineDTOs.add(medDTO2);
        medicineDTOs.add(medDTO3);
    }

    @BeforeEach
    void clearDatabase(@Autowired Flyway flyway) {
        flyway.clean();
        flyway.migrate();
    }

    @Test
    void shouldReturnAMedicineWhenDataIsSaved() {
        ResponseEntity<String> response = restTemplate
                .withBasicAuth("100", "abc123")
                .getForEntity("/medicines/100", String.class);
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);

        DocumentContext documentContext = JsonPath.parse(response.getBody());
        Number id = documentContext.read("$.id");
        assertThat(id).isEqualTo(100);

        String name = documentContext.read("$.name");
        assertThat(name).isEqualTo("med1");
    }

    @Test
    @DirtiesContext
    void shouldCreateANewMedicine() {
        MedicineResponse newMedicine = new MedicineResponse(100L, "med1",  OffsetDateTime.parse("2023-06-15T21:27:17.289601+02"), 6, "100");
        ResponseEntity<Void> createResponse = restTemplate.withBasicAuth("100", "abc123")
                .postForEntity("/medicines", newMedicine, Void.class);
        assertThat(createResponse.getStatusCode()).isEqualTo(HttpStatus.CREATED);

        URI locationOfNewMedicine = createResponse.getHeaders().getLocation();
        ResponseEntity<String> getResponse = restTemplate.withBasicAuth("100", "abc123")
                .getForEntity(locationOfNewMedicine, String.class);
        assertThat(getResponse.getStatusCode()).isEqualTo(HttpStatus.OK);

        DocumentContext documentContext = JsonPath.parse(getResponse.getBody());
        Number id = documentContext.read("$.id");
        String name = documentContext.read("$.name");
        OffsetDateTime expirationDate = OffsetDateTime.parse(documentContext.read("$.expirationDate"));
        int PAO = documentContext.read("$.periodAfterOpening");
        String owner = documentContext.read("$.owner");

        assertThat(id).isNotNull();
        assertThat(name).isEqualTo("med1");
        assertThat(expirationDate).isEqualTo(OffsetDateTime.parse("2023-06-15T21:27:17.289601+02:00"));
        assertThat(PAO).isEqualTo(6);
        assertThat(owner).isEqualTo("100");
    }


    @Test
    void shouldNotReturnAMedicineWithAnUnknownId() {
        ResponseEntity<String> response = restTemplate
                .withBasicAuth("100", "abc123")
                .getForEntity("/medicines/1000", String.class);
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.NOT_FOUND);
        assertThat(response.getBody()).isBlank();
    }

    @Test
    void shouldReturnAllMedicinesWhenListIsRequested() {
        ResponseEntity<String> response = restTemplate
                .withBasicAuth("100", "abc123")
                .getForEntity("/medicines", String.class);
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);

        DocumentContext documentContext = JsonPath.parse(response.getBody());
        int medicineCount = documentContext.read("$.medicines.length()");
        assertThat(medicineCount).isEqualTo(2);

        JSONArray ids = documentContext.read("$..id");
        assertThat(ids).containsExactlyInAnyOrder(100, 101);

        JSONArray names = documentContext.read("$..name");
        assertThat(names).containsExactlyInAnyOrder("med1", "med2");
    }

    @Test
    void shouldReturnAPageOfMedicines() {
        ResponseEntity<String> response = restTemplate
                .withBasicAuth("100", "abc123")
                .getForEntity("/medicines?page=0&size=1", String.class);
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);

        DocumentContext documentContext = JsonPath.parse(response.getBody());
        int medicineCount = documentContext.read("$.medicines.length()");
        assertThat(medicineCount).isEqualTo(1);
    }


    @Test
    @DirtiesContext
    void shouldDeleteAnExistingMedicine() {
        ResponseEntity<Void> response = restTemplate
                .withBasicAuth("100", "abc123")
                .exchange("/medicines/100", HttpMethod.DELETE, null, Void.class);
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.NO_CONTENT);

        ResponseEntity<String> getResponse = restTemplate
                .withBasicAuth("100", "abc123")
                .getForEntity("/medicines/1", String.class);
        assertThat(getResponse.getStatusCode()).isEqualTo(HttpStatus.NOT_FOUND);
    }


    @Test
    void shouldNotDeleteAMedicineThatDoesNotExist() {
        ResponseEntity<Void> deleteResponse = restTemplate.withBasicAuth("100", "abc123")
                .exchange("/medicines/99999", HttpMethod.DELETE, null, Void.class);
        assertThat(deleteResponse.getStatusCode()).isEqualTo(HttpStatus.NOT_FOUND);
    }

    @Test
    void shouldNotAllowDeletionOfMedicineTheyDoNotOwn() {
        ResponseEntity<Void> deleteResponse = restTemplate
                .withBasicAuth("100", "abc123")
                .exchange("/medicines/102", HttpMethod.DELETE, null, Void.class);
        assertThat(deleteResponse.getStatusCode()).isEqualTo(HttpStatus.NOT_FOUND);

        ResponseEntity<String> getResponse = restTemplate
                .withBasicAuth("101", "xyz789")
                .getForEntity("/medicines/102", String.class);
        assertThat(getResponse.getStatusCode()).isEqualTo(HttpStatus.OK);
    }

    @Test
    void shouldNotReturnAMedicineWhenUsingBadCredentials() {
        ResponseEntity<String> response = restTemplate
                .withBasicAuth("BAD-USER", "abc123")
                .getForEntity("/medicines/100", String.class);
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.UNAUTHORIZED);

        response = restTemplate
                .withBasicAuth("100", "BAD-PASSWORD")
                .getForEntity("/medicines/100", String.class);
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.UNAUTHORIZED);
    }

    @Test
    void shouldRejectUsersWhoAreNotMedicineOwners() {
        ResponseEntity<String> response = restTemplate
                .withBasicAuth("no-medicine", "qrs456")
                .getForEntity("/medicines/100", String.class);
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.FORBIDDEN);
    }

    @Test
    void shouldNotAllowAccessToMedicinesTheyDoNotOwn() {
        ResponseEntity<String> response = restTemplate
                .withBasicAuth("100", "abc123")
                .getForEntity("/medicines/104", String.class);
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.NOT_FOUND);
    }

    @Test
    @DirtiesContext
    void shouldUpdateAnExistingMedicine() {
        MedicineRequest medicineUpdate = new MedicineRequest("newName",null, 0, "100");
        HttpEntity<MedicineRequest> request = new HttpEntity<>(medicineUpdate);
        System.out.println("------checkpoint-------1---------");
        ResponseEntity<Void> response = restTemplate
                .withBasicAuth("100", "abc123")
                .exchange("/medicines/100", HttpMethod.PUT, request, Void.class);
        System.out.println("------checkpoint-------2---------");

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.NO_CONTENT);
        System.out.println("------checkpoint-------3---------");
        ResponseEntity<String> getResponse = restTemplate
                .withBasicAuth("100", "abc123")
                .getForEntity("/medicines/100", String.class);
        assertThat(getResponse.getStatusCode()).isEqualTo(HttpStatus.OK);
        System.out.println("------checkpoint-------4---------");
        DocumentContext documentContext = JsonPath.parse(getResponse.getBody());
        Number id = documentContext.read("$.id");
        String name = documentContext.read("$.name");
        OffsetDateTime expirationDate = OffsetDateTime.parse(documentContext.read("$.expirationDate"));
        int PAO = documentContext.read("$.periodAfterOpening");
        String owner = documentContext.read("$.owner");

        assertThat(id).isNotNull();
        assertThat(name).isEqualTo("newName");
        assertThat(expirationDate).isEqualTo(OffsetDateTime.parse("2023-06-15T21:27:17.289601+02:00"));
        assertThat(PAO).isEqualTo(6);
        assertThat(owner).isEqualTo("100");
    }

    @Test
    void shouldNotUpdateAMedicineThatDoesNotExist() {
        MedicineRequest unknownMedicine = new MedicineRequest("nonexistingMedicine", null, 0, null);
        HttpEntity<MedicineRequest> request = new HttpEntity<>(unknownMedicine);
        ResponseEntity<Void> response = restTemplate
                .withBasicAuth("100", "abc123")
                .exchange("/medicines/99999", HttpMethod.PUT, request, Void.class);
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.NOT_FOUND);
    }
}


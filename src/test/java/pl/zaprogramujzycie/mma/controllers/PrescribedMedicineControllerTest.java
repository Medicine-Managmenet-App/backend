package pl.zaprogramujzycie.mma.controllers;

import com.jayway.jsonpath.DocumentContext;
import com.jayway.jsonpath.JsonPath;
import net.minidev.json.JSONArray;
import org.flywaydb.core.Flyway;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.annotation.DirtiesContext;
import pl.zaprogramujzycie.mma.dto.request.MedicineRequest;
import pl.zaprogramujzycie.mma.dto.request.PrescribedMedicineRequest;

import java.net.URI;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;


@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
public class PrescribedMedicineControllerTest {

    @Autowired
    TestRestTemplate restTemplate;

    final List<LocalTime> timesList = new ArrayList<>();
    final List<String> stringList = new ArrayList<>();



    @BeforeEach
    void clearDatabase(@Autowired Flyway flyway) {
        flyway.clean();
        flyway.migrate();
    }

    @BeforeEach
    void setUp() {

        DateTimeFormatter formater = DateTimeFormatter.ofPattern("HH:mm:ss");

        stringList.add(formater.format(LocalTime.of(8, 00, 00)));
        stringList.add(formater.format(LocalTime.of(16, 00, 00)));
        stringList.add(formater.format(LocalTime.of(00, 00, 00)));
        timesList.add(LocalTime.of(8, 00, 00));
        timesList.add(LocalTime.of(16, 00, 00));
        timesList.add(LocalTime.of(00, 00, 00));
    }



    @Test
    void shouldReturnAPrescribedMedicineWhenDataIsSaved() {
        ResponseEntity<String> response = restTemplate
                .withBasicAuth("100", "abc123")
                .getForEntity("/prescribedMedicines/100", String.class);
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        System.out.println("------------got medicine------------");
        DocumentContext documentContext = JsonPath.parse(response.getBody());
        Number id = documentContext.read("$.id");
        assertThat(id).isEqualTo(100);

        Number medicineId  = documentContext.read("$.medicineId");
        assertThat(medicineId).isEqualTo(100);

        Number dosage = documentContext.read("$.dosage");
        assertThat(dosage).isEqualTo(1.0);

        List<LocalTime> administrationTimes = documentContext.read("$.administrationTimes");
        assertThat(administrationTimes).isEqualTo(stringList);

    }

    @Test
    @DirtiesContext
    void shouldCreateANewPrescribedMedicine() {
        PrescribedMedicineRequest newPrescribedMedicine = new PrescribedMedicineRequest(100L, 1.0, LocalTime.of(8, 00, 00), 8, "100");
        ResponseEntity<Void> createResponse = restTemplate.withBasicAuth("100", "abc123")
                .postForEntity("/prescribedMedicines", newPrescribedMedicine, Void.class);
        assertThat(createResponse.getStatusCode()).isEqualTo(HttpStatus.CREATED);

        URI locationOfNewMedicine = createResponse.getHeaders().getLocation();
        ResponseEntity<String> getResponse = restTemplate.withBasicAuth("100", "abc123")
                .getForEntity(locationOfNewMedicine, String.class);
        assertThat(getResponse.getStatusCode()).isEqualTo(HttpStatus.OK);

        DocumentContext documentContext = JsonPath.parse(getResponse.getBody());
        Number id = documentContext.read("$.id");
        Number medicineId = documentContext.read("$.medicineId");
        Number dosage = documentContext.read("$.dosage");
        List<String> administrationTimes = documentContext.read("$.administrationTimes");
        String owner = documentContext.read("$.owner");

        assertThat(id).isNotNull();
        assertThat(medicineId).isEqualTo(100);
        assertThat(dosage).isEqualTo(1.0);
        assertThat(administrationTimes).isEqualTo(stringList);
        assertThat(owner).isEqualTo("100");
    }


    @Test
    void shouldNotReturnAPrescribedMedicineWithAnUnknownId() {
        ResponseEntity<String> response = restTemplate
                .withBasicAuth("100", "abc123")
                .getForEntity("/prescribedMedicines/1000", String.class);
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.NOT_FOUND);
        assertThat(response.getBody()).isBlank();
    }

    @Test
    void shouldReturnAllMedicinesWhenListIsRequested() {
        ResponseEntity<String> response = restTemplate
                .withBasicAuth("100", "abc123")
                .getForEntity("/prescribedMedicines", String.class);
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);

        DocumentContext documentContext = JsonPath.parse(response.getBody());
        int medicineCount = documentContext.read("$.prescribedMedicines.length()");
        assertThat(medicineCount).isEqualTo(2);

        JSONArray ids = documentContext.read("$..id");
        assertThat(ids).containsExactlyInAnyOrder(100, 101);

        JSONArray medicineIds = documentContext.read("$..medicineId");
        assertThat(ids).containsExactlyInAnyOrder(100, 101);

        JSONArray dosages = documentContext.read("$..dosage");
        assertThat(dosages).containsExactlyInAnyOrder(1.0, 1.0);

        JSONArray administrationTimes = documentContext.read("$..administrationTimes");
        assertThat(administrationTimes).containsExactlyInAnyOrder(stringList, stringList);
    }

    @Test
    void shouldReturnAPageOfPrescribedMedicines() {
        ResponseEntity<String> response = restTemplate
                .withBasicAuth("100", "abc123")
                .getForEntity("/prescribedMedicines?page=0&size=1", String.class);
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);

        DocumentContext documentContext = JsonPath.parse(response.getBody());
        int medicineCount = documentContext.read("$.prescribedMedicines.length()");
        assertThat(medicineCount).isEqualTo(1);
    }


    @Test
    @DirtiesContext
    void shouldDeleteAnExistingMedicine() {
        ResponseEntity<Void> response = restTemplate
                .withBasicAuth("100", "abc123")
                .exchange("/prescribedMedicines/100", HttpMethod.DELETE, null, Void.class);
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.NO_CONTENT);

        ResponseEntity<String> getResponse = restTemplate
                .withBasicAuth("100", "abc123")
                .getForEntity("/prescribedMedicines/100", String.class);
        assertThat(getResponse.getStatusCode()).isEqualTo(HttpStatus.NOT_FOUND);
    }


    @Test
    void shouldNotDeleteAPrescribedMedicineThatDoesNotExist() {
        ResponseEntity<Void> deleteResponse = restTemplate.withBasicAuth("100", "abc123")
                .exchange("/prescribedMedicines/99999", HttpMethod.DELETE, null, Void.class);
        assertThat(deleteResponse.getStatusCode()).isEqualTo(HttpStatus.NOT_FOUND);
    }

    @Test
    void shouldNotAllowDeletionOfPrescribedMedicineTheyDoNotOwn() {
        ResponseEntity<Void> deleteResponse = restTemplate
                .withBasicAuth("100", "abc123")
                .exchange("/prescribedMedicines/102", HttpMethod.DELETE, null, Void.class);
        assertThat(deleteResponse.getStatusCode()).isEqualTo(HttpStatus.NOT_FOUND);

        ResponseEntity<String> getResponse = restTemplate
                .withBasicAuth("101", "xyz789")
                .getForEntity("/prescribedMedicines/102", String.class);
        assertThat(getResponse.getStatusCode()).isEqualTo(HttpStatus.OK);
    }

    @Test
    void shouldNotReturnAPrescribedMedicineWhenUsingBadCredentials() {
        ResponseEntity<String> response = restTemplate
                .withBasicAuth("BAD-USER", "abc123")
                .getForEntity("/prescribedMedicines/100", String.class);
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.UNAUTHORIZED);

        response = restTemplate
                .withBasicAuth("100", "BAD-PASSWORD")
                .getForEntity("/prescribedMedicines/100", String.class);
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.UNAUTHORIZED);
    }

    @Test
    void shouldRejectUsersWhoAreNotPrescribedMedicineOwners() {
        ResponseEntity<String> response = restTemplate
                .withBasicAuth("no-medicine", "qrs456")
                .getForEntity("/prescribedMedicines/100", String.class);
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.FORBIDDEN);
    }

    @Test
    void shouldNotAllowAccessToPrescribedMedicinesTheyDoNotOwn() {
        ResponseEntity<String> response = restTemplate
                .withBasicAuth("100", "abc123")
                .getForEntity("/prescribedMedicines/104", String.class);
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.NOT_FOUND);
    }

    @Test
    @DirtiesContext
    void shouldUpdateAnExistingPrescribedMedicine() {
        PrescribedMedicineRequest prescribedMedicineUpdate = new PrescribedMedicineRequest(100L,2.0, LocalTime.of(8, 00, 00), 8, "100");
        HttpEntity<PrescribedMedicineRequest> request = new HttpEntity<>(prescribedMedicineUpdate);
        ResponseEntity<Void> response = restTemplate
                .withBasicAuth("100", "abc123")
                .exchange("/prescribedMedicines/100", HttpMethod.PUT, request, Void.class);

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.NO_CONTENT);
        ResponseEntity<String> getResponse = restTemplate
                .withBasicAuth("100", "abc123")
                .getForEntity("/prescribedMedicines/100", String.class);
        assertThat(getResponse.getStatusCode()).isEqualTo(HttpStatus.OK);

        DocumentContext documentContext = JsonPath.parse(getResponse.getBody());
        Number id = documentContext.read("$.id");
        Number medicineId = documentContext.read("$.medicineId");
        Number dosage = documentContext.read("$.dosage");
        List<String> administrationTimes = documentContext.read("$.administrationTimes");
        String owner = documentContext.read("$.owner");

        assertThat(id).isNotNull();
        assertThat(medicineId).isEqualTo(100);
        assertThat(dosage).isEqualTo(2.0);
        assertThat(administrationTimes).isEqualTo(stringList);
        assertThat(owner).isEqualTo("100");
    }

    @Test
    void shouldNotUpdateAMedicineThatDoesNotExist() {
        MedicineRequest unknownMedicine = new MedicineRequest("nonexistingMedicine", null, 0, null);
        HttpEntity<MedicineRequest> request = new HttpEntity<>(unknownMedicine);
        ResponseEntity<Void> response = restTemplate
                .withBasicAuth("100", "abc123")
                .exchange("/prescribedMedicines/99999", HttpMethod.PUT, request, Void.class);
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.NOT_FOUND);
    }
}


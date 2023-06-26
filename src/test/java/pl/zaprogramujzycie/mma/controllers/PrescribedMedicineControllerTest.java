package pl.zaprogramujzycie.mma.controllers;

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
import pl.zaprogramujzycie.mma.dto.response.MedicineResponse;
import pl.zaprogramujzycie.mma.dto.response.MedicinesResponse;
import pl.zaprogramujzycie.mma.dto.response.PrescribedMedicineResponse;
import pl.zaprogramujzycie.mma.dto.response.PrescribedMedicinesResponse;

import java.net.URI;
import java.time.LocalTime;
import java.time.OffsetDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

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
        timesList.add(LocalTime.of(00, 00, 00));
        timesList.add(LocalTime.of(8, 00, 00));
        timesList.add(LocalTime.of(16, 00, 00));

    }



    @Test
    void shouldReturnAPrescribedMedicineWhenDataIsSaved() {
        ResponseEntity<PrescribedMedicineResponse> getResponse = restTemplate
                .withBasicAuth("login", "password")
                .getForEntity("/families/100/familyMembers/100/prescriptions/100/prescribedMedicines/100", PrescribedMedicineResponse.class);

        PrescribedMedicineResponse response = getResponse.getBody();

        assertThat(getResponse.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response.id()).isEqualTo(100);
        assertThat(response.medicine()).isEqualTo(100);
        assertThat(response.dosage()).isEqualTo(1.0);
        assertThat(response.numberOfDoses()).isEqualTo(3);
        assertThat(response.administrationTimes()).isEqualTo(timesList);
        assertThat(response.dosageInterval()).isEqualTo(24);
    }

    @Test
    @DirtiesContext
    void shouldCreateANewPrescribedMedicine() {
        PrescribedMedicineRequest newPrescribedMedicine = new PrescribedMedicineRequest(100L, 1.0, 3, 24, timesList);
        ResponseEntity<PrescribedMedicineResponse> createResponse = restTemplate.
                withBasicAuth("login", "password")
                .postForEntity("/families/100/familyMembers/100/prescriptions/100/prescribedMedicines",
                        newPrescribedMedicine, PrescribedMedicineResponse.class);

        URI locationOfNewMedicine = createResponse.getHeaders().getLocation();
        ResponseEntity<PrescribedMedicineResponse> getResponse = restTemplate.
                withBasicAuth("login", "password")
                .getForEntity(locationOfNewMedicine, PrescribedMedicineResponse.class);

        PrescribedMedicineResponse response = getResponse.getBody();
        assertThat(createResponse.getStatusCode()).isEqualTo(HttpStatus.CREATED);
        assertThat(getResponse.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response.id()).isNotNull();
        assertThat(response.medicine()).isEqualTo(100);
        assertThat(response.dosage()).isEqualTo(1.0);
        assertThat(response.numberOfDoses()).isEqualTo(3);
        assertThat(response.administrationTimes()).isEqualTo(timesList);
        assertThat(response.dosageInterval()).isEqualTo(24);
    }

    @Test
    void shouldNotReturnAPrescribedMedicineWithAnUnknownId() {
        ResponseEntity<PrescribedMedicineResponse> response = restTemplate
                .withBasicAuth("login", "password")
                .getForEntity("/families/100/familyMembers/100/prescriptions/100/prescribedMedicines/1000", PrescribedMedicineResponse.class);
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.NOT_FOUND);
    }
    @Test
    void shouldNotReturnAPrescribedMedicineWhenUsingBadCredentials() {
        ResponseEntity<String> response = restTemplate
                .withBasicAuth("BAD-USER", "password")
                .getForEntity("/families/100/familyMembers/100/prescriptions/100/prescribedMedicines/100", String.class);
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.UNAUTHORIZED);

        response = restTemplate
                .withBasicAuth("login", "BAD-PASSWORD")
                .getForEntity("/families/100/familyMembers/100/prescriptions/100/prescribedMedicines/100", String.class);
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.UNAUTHORIZED);
    }

    @Test
    void shouldRejectUsersWhoAreNotPrescribedMedicineOwners() {
        ResponseEntity<String> response = restTemplate
                .withBasicAuth("login1", "password1")
                .getForEntity("/families/100/familyMembers/100/prescriptions/100/prescribedMedicines/100", String.class);
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.FORBIDDEN);
    }

    @Test
    void shouldNotAllowAccessToPrescribedMedicinesTheyDoNotOwn() {
        ResponseEntity<String> response = restTemplate
                .withBasicAuth("login", "password")
                .getForEntity("/families/101/familyMembers/103/prescriptions/103/prescribedMedicines/103", String.class);
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.NOT_FOUND);
    }

    @Test
    void shouldReturnAllPrescribedMedicinesWhenListIsRequested() {
        ResponseEntity<PrescribedMedicinesResponse> getResponse = restTemplate
                .withBasicAuth("login", "password")
                .getForEntity("/families/100/familyMembers/100/prescriptions/100/prescribedMedicines", PrescribedMedicinesResponse.class);

        PrescribedMedicinesResponse response = getResponse.getBody();
        List<PrescribedMedicineResponse> list= response.prescribedMedicines();

        assertThat(getResponse.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response.prescribedMedicines().size()).isEqualTo(2);
        assertThat(list.stream().map(PrescribedMedicineResponse::id)).containsExactlyInAnyOrder(100L, 101L);
    }

    @Test
    void shouldReturnAPageOfPrescribedMedicines() {
        ResponseEntity<PrescribedMedicinesResponse> getResponse = restTemplate
                .withBasicAuth("login", "password")
                .getForEntity("/families/100/familyMembers/100/prescriptions/100/prescribedMedicines?page=0&size=1", PrescribedMedicinesResponse.class);

        PrescribedMedicinesResponse response = getResponse.getBody();

        assertThat(getResponse.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response.prescribedMedicines().size()).isEqualTo(1);
    }


    @Test
    @DirtiesContext
    void shouldDeleteAnExistingMedicine() {
        ResponseEntity<Void> response = restTemplate
                .withBasicAuth("login", "password")
                .exchange("/families/100/familyMembers/100/prescriptions/100/prescribedMedicines/100",
                        HttpMethod.DELETE, null, Void.class);


        ResponseEntity<String> getResponse = restTemplate
                .withBasicAuth("login", "password")
                .getForEntity("/families/100/familyMembers/100/prescriptions/100/prescribedMedicines/100",
                        String.class);

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.NO_CONTENT);
        assertThat(getResponse.getStatusCode()).isEqualTo(HttpStatus.NOT_FOUND);
    }


    @Test
    void shouldNotDeleteAPrescribedMedicineThatDoesNotExist() {
        ResponseEntity<Void> deleteResponse = restTemplate.
                withBasicAuth("login", "password")
                .exchange("/families/100/familyMembers/100/prescriptions/100/prescribedMedicines/99999",
                        HttpMethod.DELETE, null, Void.class);
        assertThat(deleteResponse.getStatusCode()).isEqualTo(HttpStatus.NOT_FOUND);
    }

    @Test
    void shouldNotAllowDeletionOfPrescribedMedicineTheyDoNotOwn() {
        ResponseEntity<Void> deleteResponse = restTemplate
                .withBasicAuth("login", "password")
                .exchange("/families/101/familyMembers/103/prescriptions/103/prescribedMedicines/103", HttpMethod.DELETE, null, Void.class);
        assertThat(deleteResponse.getStatusCode()).isEqualTo(HttpStatus.NOT_FOUND);

        ResponseEntity<String> getResponse = restTemplate
                .withBasicAuth("login3", "password3")
                .getForEntity("/families/101/familyMembers/103/prescriptions/103/prescribedMedicines/103", String.class);
        assertThat(getResponse.getStatusCode()).isEqualTo(HttpStatus.OK);
    }



    @Test
    @DirtiesContext
    void shouldUpdateAnExistingPrescribedMedicine() {
        PrescribedMedicineRequest prescribedMedicineUpdate = new PrescribedMedicineRequest(100L,2.0, 3, 24, timesList);
        HttpEntity<PrescribedMedicineRequest> request = new HttpEntity<>(prescribedMedicineUpdate);
        ResponseEntity<Void> putResponse = restTemplate
                .withBasicAuth("login", "password")
                .exchange("/families/100/familyMembers/100/prescriptions/100/prescribedMedicines/100", HttpMethod.PUT, request, Void.class);


        ResponseEntity<PrescribedMedicineResponse> getResponse = restTemplate
                .withBasicAuth("login", "password")
                .getForEntity("/families/100/familyMembers/100/prescriptions/100/prescribedMedicines/100",
                        PrescribedMedicineResponse.class);

        PrescribedMedicineResponse response = getResponse.getBody();
        assertThat(putResponse.getStatusCode()).isEqualTo(HttpStatus.NO_CONTENT);
        assertThat(getResponse.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response.id()).isNotNull();
        assertThat(response.medicine()).isEqualTo(100);
        assertThat(response.dosage()).isEqualTo(2.0);
        assertThat(response.numberOfDoses()).isEqualTo(3);
        assertThat(response.administrationTimes()).isEqualTo(timesList);
        assertThat(response.dosageInterval()).isEqualTo(24);
    }

    @Test
    void shouldNotUpdateAMedicineThatDoesNotExist() {
        PrescribedMedicineRequest unknownMedicine = new PrescribedMedicineRequest(100L,2.0, 3, 24, timesList);
        HttpEntity<PrescribedMedicineRequest> request = new HttpEntity<>(unknownMedicine);
        ResponseEntity<Void> response = restTemplate
                .withBasicAuth("login", "password")
                .exchange("/families/100/familyMembers/100/prescriptions/100/prescribedMedicines/99999", HttpMethod.PUT, request, Void.class);
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.NOT_FOUND);
    }
}




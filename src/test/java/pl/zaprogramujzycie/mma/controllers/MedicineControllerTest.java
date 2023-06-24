package pl.zaprogramujzycie.mma.controllers;

import org.flywaydb.core.Flyway;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.*;
import org.springframework.test.annotation.DirtiesContext;
import pl.zaprogramujzycie.mma.dto.request.MedicineRequest;
import pl.zaprogramujzycie.mma.dto.response.MedicineResponse;
import pl.zaprogramujzycie.mma.dto.response.MedicinesResponse;

import java.net.URI;
import java.time.OffsetDateTime;
import java.util.List;
import java.util.stream.Collectors;

import static org.assertj.core.api.Assertions.assertThat;


@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
public class MedicineControllerTest {

    @Autowired
    TestRestTemplate restTemplate;

    @BeforeEach
    void clearDatabase(@Autowired Flyway flyway) {
        flyway.clean();
        flyway.migrate();
    }

    @Test
    void shouldReturnAMedicineWhenDataIsSaved() {
        ResponseEntity<MedicineResponse> getResponse = restTemplate
                .withBasicAuth("login", "password")
                .getForEntity("/medicines/100", MedicineResponse.class);

        MedicineResponse response = getResponse.getBody();

        assertThat(getResponse.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response.id()).isNotNull();
        assertThat(response.name()).isEqualTo("med1");
        assertThat(response.expirationDate()).isEqualTo(OffsetDateTime.parse("2023-06-15T21:27:17.289601+02:00"));
        assertThat(response.periodAfterOpening()).isEqualTo(6);
        assertThat(response.familyId()).isEqualTo(100);
    }

    @Test
    @DirtiesContext
    void shouldCreateANewMedicine() {
        MedicineRequest newMedicine = new MedicineRequest( "med1",  OffsetDateTime.parse("2023-06-15T21:27:17.289601+02"), 6);

        ResponseEntity<Void> createResponse = restTemplate.withBasicAuth("login", "password")
                .postForEntity("/medicines", newMedicine, Void.class);

        URI locationOfNewMedicine = createResponse.getHeaders().getLocation();
        ResponseEntity<MedicineResponse> getResponse = restTemplate.withBasicAuth("login", "password")
                .getForEntity(locationOfNewMedicine, MedicineResponse.class);
        MedicineResponse response = getResponse.getBody();

        assertThat(createResponse.getStatusCode()).isEqualTo(HttpStatus.CREATED);
        assertThat(getResponse.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response.id()).isNotNull();
        assertThat(response.name()).isEqualTo("med1");
        assertThat(response.expirationDate()).isEqualTo(OffsetDateTime.parse("2023-06-15T21:27:17.289601+02:00"));
        assertThat(response.periodAfterOpening()).isEqualTo(6);
        assertThat(response.familyId()).isEqualTo(100);
    }


    @Test
    void shouldNotReturnAMedicineWithAnUnknownId() {
        ResponseEntity<MedicineResponse> response = restTemplate
                .withBasicAuth("login", "password")
                .getForEntity("/medicines/999", MedicineResponse.class);
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.NOT_FOUND);
    }

    @Test
    void shouldReturnAllMedicinesWhenListIsRequested() {
        ResponseEntity<MedicinesResponse> getResponse = restTemplate
                .withBasicAuth("login", "password")
                .getForEntity("/medicines", MedicinesResponse.class);

        MedicinesResponse response = getResponse.getBody();
        List<Long> ids = response. medicines()
                .stream()
                .map(MedicineResponse::id)
                .collect(Collectors.toList());
        List<String> names = response.medicines()
                .stream()
                .map(MedicineResponse::name)
                .collect(Collectors.toList());

        assertThat(getResponse.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response.medicines().size()).isEqualTo(3);
        assertThat(ids).containsExactlyInAnyOrder(100L, 101L, 1000L);
        assertThat(names).containsExactlyInAnyOrder("med1", "med2", "medToDelete");
    }

    @Test
    void shouldReturnAPageOfMedicines() {
        ResponseEntity<MedicinesResponse> getResponse = restTemplate
                .withBasicAuth("login", "password")
                .getForEntity("/medicines?page=0&size=2", MedicinesResponse.class);

        MedicinesResponse response = getResponse.getBody();

        assertThat(getResponse.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response.medicines().size()).isEqualTo(2);
    }

    @Test
    @DirtiesContext
    void shouldDeleteAnExistingMedicine() {
        ResponseEntity<Void> response = restTemplate
                .withBasicAuth("login", "password")
                .exchange("/medicines/1000", HttpMethod.DELETE, null, Void.class);

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.NO_CONTENT);
    }

    @Test
    void shouldNotDeleteMedicineConnectedToPrescribedMedicine(){
        ResponseEntity<Void> response = restTemplate
                .withBasicAuth("login", "password")
                .exchange("/medicines/101", HttpMethod.DELETE, null, Void.class);

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.IM_USED);
    }

    @Test
    void shouldNotDeleteAMedicineThatDoesNotExist() {
        ResponseEntity<Void> deleteResponse = restTemplate
                .withBasicAuth("login", "password")
                .exchange("/medicines/99999", HttpMethod.DELETE, null, Void.class);

        assertThat(deleteResponse.getStatusCode()).isEqualTo(HttpStatus.NOT_FOUND);
    }

    @Test
    void shouldNotAllowDeletionOfMedicineTheyDoNotOwn() {
        ResponseEntity<Void> deleteResponse = restTemplate
                .withBasicAuth("login", "password")
                .exchange("/medicines/102", HttpMethod.DELETE, null, Void.class);

        ResponseEntity<MedicineResponse> getResponse = restTemplate
                .withBasicAuth("login3", "password3")
                .getForEntity("/medicines/102", MedicineResponse.class);

        assertThat(deleteResponse.getStatusCode()).isEqualTo(HttpStatus.NOT_FOUND);
        assertThat(getResponse.getStatusCode()).isEqualTo(HttpStatus.OK);
    }


    @Test
    void shouldNotReturnAMedicineWhenUsingBadCredentials() {
        ResponseEntity<MedicineResponse> responseUsername = restTemplate
                .withBasicAuth("BAD-USER", "password")
                .getForEntity("/medicines/100", MedicineResponse.class);

        ResponseEntity<MedicineResponse> responsePassword = restTemplate
                .withBasicAuth("login", "BAD-PASSWORD")
                .getForEntity("/medicines/100", MedicineResponse.class);

        assertThat(responseUsername.getStatusCode()).isEqualTo(HttpStatus.UNAUTHORIZED);
        assertThat(responsePassword.getStatusCode()).isEqualTo(HttpStatus.UNAUTHORIZED);
    }

    @Test
    void shouldRejectUsersWhoAreNotMedicineOwners() {
        ResponseEntity<MedicineResponse> response = restTemplate
                .withBasicAuth("login1", "password1")
                .getForEntity("/medicines/100", MedicineResponse.class);

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.FORBIDDEN);
    }

    @Test
    void shouldNotAllowAccessToMedicinesTheyDoNotOwn() {
        ResponseEntity<MedicineResponse> response = restTemplate
                .withBasicAuth("login", "password")
                .getForEntity("/medicines/104", MedicineResponse.class);

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.NOT_FOUND);
    }

    @Test
    @DirtiesContext
    void shouldUpdateAnExistingMedicine() {
        MedicineRequest medicineUpdate = new MedicineRequest("newName",null, 0);
        HttpEntity<MedicineRequest> request = new HttpEntity<>(medicineUpdate);

        ResponseEntity<Void> putResponse = restTemplate
                .withBasicAuth("login", "password")
                .exchange("/medicines/100", HttpMethod.PUT, request, Void.class);
        ResponseEntity<MedicineResponse> getResponse = restTemplate
                .withBasicAuth("login", "password")
                .getForEntity("/medicines/100", MedicineResponse.class);
        MedicineResponse response = getResponse.getBody();

        assertThat(putResponse.getStatusCode()).isEqualTo(HttpStatus.NO_CONTENT);
        assertThat(getResponse.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response.id()).isNotNull();
        assertThat(response.name()).isEqualTo("newName");
        assertThat(response.expirationDate()).isEqualTo(OffsetDateTime.parse("2023-06-15T21:27:17.289601+02:00"));
        assertThat(response.periodAfterOpening()).isEqualTo(6);
        assertThat(response.familyId()).isEqualTo(100);
    }

    @Test
    void shouldNotUpdateAMedicineThatDoesNotExist() {
        MedicineRequest unknownMedicine = new MedicineRequest("nonExistingMedicine", null, 0);
        HttpEntity<MedicineRequest> request = new HttpEntity<>(unknownMedicine);
        ResponseEntity<Void> response = restTemplate
                .withBasicAuth("login", "password")
                .exchange("/medicines/99999", HttpMethod.PUT, request, Void.class);

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.NOT_FOUND);
    }
}




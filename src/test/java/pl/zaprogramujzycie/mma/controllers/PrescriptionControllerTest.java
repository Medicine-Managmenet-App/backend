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
import pl.zaprogramujzycie.mma.dto.request.PrescriptionRequest;
import pl.zaprogramujzycie.mma.dto.response.PrescriptionResponse;

import java.net.URI;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;


@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
public class PrescriptionControllerTest {
    @Autowired
    TestRestTemplate restTemplate;

    private List<Long> prescribedMedicineIds;

    private List<Long> newPrescribedMedicineIds;

    @BeforeEach
    void clearDatabase(@Autowired Flyway flyway) {
        flyway.clean();
        flyway.migrate();
    }

    @BeforeEach
    void setup () {
        prescribedMedicineIds = new ArrayList<>(Arrays.asList(100L, 101L, 102L));
        prescribedMedicineIds = new ArrayList<>(Arrays.asList(100L, 101L));
    }

    @Test
    void shouldReturnAPrescriptionWhenDataIsSaved() {
        ResponseEntity<PrescriptionResponse> getResponse = restTemplate
                .withBasicAuth("login", "password")
                .getForEntity("/families/100/familyMembers/100/prescriptions/100", PrescriptionResponse.class);

        PrescriptionResponse response = getResponse.getBody();

        assertThat(getResponse.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response.id()).isEqualTo(100);
        assertThat(response.prescribedMedicines()).containsExactlyInAnyOrder(100L, 101L);
    }

    @Test
    @DirtiesContext
    void shouldCreateANewPrescription() {
        PrescriptionRequest newPrescription = new PrescriptionRequest(new ArrayList<>());
        ResponseEntity<Void> createResponse = restTemplate
                .withBasicAuth("login", "password")
                .postForEntity("/families/100/familyMembers/100/prescriptions", newPrescription, Void.class);

        URI locationOfNewPrescription = createResponse.getHeaders().getLocation();
        ResponseEntity<PrescriptionResponse> getResponse = restTemplate
                .withBasicAuth("login", "password")
                .getForEntity(locationOfNewPrescription, PrescriptionResponse.class);

        PrescriptionResponse response = getResponse.getBody();

        assertThat(createResponse.getStatusCode()).isEqualTo(HttpStatus.CREATED);
        assertThat(getResponse.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response.id()).isNotNull();
    }

    @Test
    void shouldNotReturnAPrescriptionWithAnUnknownId() {
        ResponseEntity<String> response = restTemplate
                .withBasicAuth("login", "password")
                .getForEntity("/families/100/familyMembers/100/prescriptions/1000",
                        String.class);
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.NOT_FOUND);
    }

    @Test
    @DirtiesContext
    void shouldDeleteAnExistingPrescription() {
        ResponseEntity<Void> response = restTemplate
                .withBasicAuth("login", "password")
                .exchange("/families/100/familyMembers/100/prescriptions/100",
                        HttpMethod.DELETE, null, Void.class);

        ResponseEntity<String> getResponse = restTemplate
                .withBasicAuth("login", "password")
                .getForEntity("/families/100/familyMembers/100/prescriptions/100", String.class);

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.NO_CONTENT);
        assertThat(getResponse.getStatusCode()).isEqualTo(HttpStatus.NOT_FOUND);
    }


    @Test
    void shouldNotDeleteAPrescriptionThatDoesNotExist() {
        ResponseEntity<Void> deleteResponse = restTemplate
                .withBasicAuth("login", "password")
                .exchange("/families/100/familyMembers/100/prescriptions/99999", HttpMethod.DELETE, null, Void.class);
        assertThat(deleteResponse.getStatusCode()).isEqualTo(HttpStatus.NOT_FOUND);
    }

    @Test
    void shouldNotAllowDeletionOfPPrescriptionTheyDoNotOwn() {
        ResponseEntity<Void> deleteResponse = restTemplate
                .withBasicAuth("login", "password")
                .exchange("/families/101/familyMembers/103/prescriptions/103", HttpMethod.DELETE, null, Void.class);

        ResponseEntity<String> getResponse = restTemplate
                .withBasicAuth("login3", "password3")
                .getForEntity("/families/101/familyMembers/103/prescriptions/103", String.class);

        assertThat(deleteResponse.getStatusCode()).isEqualTo(HttpStatus.NOT_FOUND);
        assertThat(getResponse.getStatusCode()).isEqualTo(HttpStatus.OK);
    }

    @Test
    void shouldNotReturnAPrescriptionWhenUsingBadCredentials() {
        ResponseEntity<String> userResponse = restTemplate
                .withBasicAuth("BAD-USER", "password")
                .getForEntity("/families/101/familyMembers/103/prescriptions/103", String.class);
        assertThat(userResponse.getStatusCode()).isEqualTo(HttpStatus.UNAUTHORIZED);

        ResponseEntity<String> passwordResponse = restTemplate
                .withBasicAuth("login3", "BAD-PASSWORD")
                .getForEntity("/families/101/familyMembers/103/prescriptions/103", String.class);
        assertThat(passwordResponse.getStatusCode()).isEqualTo(HttpStatus.UNAUTHORIZED);
    }

    @Test
    void shouldNotAllowAccessToPrescribedMedicinesTheyDoNotOwn() {
        ResponseEntity<String> response = restTemplate
                .withBasicAuth("login", "password")
                .getForEntity("/families/101/familyMembers/103/prescriptions/103", String.class);
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.NOT_FOUND);
    }

    @Test
    @DirtiesContext
    void shouldUpdateAnExistingPrescription() {
        PrescriptionRequest prescribedMedicineUpdate = new PrescriptionRequest(newPrescribedMedicineIds);
        HttpEntity<PrescriptionRequest> request = new HttpEntity<>(prescribedMedicineUpdate);
        ResponseEntity<Void> putResponse = restTemplate
                .withBasicAuth("login", "password")
                .exchange("/families/100/familyMembers/100/prescriptions/100", HttpMethod.PUT, request, Void.class);


        ResponseEntity<PrescriptionResponse> getResponse = restTemplate
                .withBasicAuth("login", "password")
                .getForEntity("/families/100/familyMembers/100/prescriptions/100", PrescriptionResponse.class);

        PrescriptionResponse response = getResponse.getBody();
        assertThat(putResponse.getStatusCode()).isEqualTo(HttpStatus.NO_CONTENT);
        assertThat(getResponse.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response.id()).isNotNull();
        assertThat(response.prescribedMedicines()).containsExactlyInAnyOrder(100L, 101L);
    }

    @Test
    void shouldNotUpdateAPrescriptionThatDoesNotExist() {
        PrescriptionRequest unknownPrescription = new PrescriptionRequest(newPrescribedMedicineIds);
        HttpEntity<PrescriptionRequest> request = new HttpEntity<>(unknownPrescription);
        ResponseEntity<Void> response = restTemplate
                .withBasicAuth("login", "password")
                .exchange("/families/100/familyMembers/100/prescriptions/99999", HttpMethod.PUT, request, Void.class);
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.NOT_FOUND);
    }
}




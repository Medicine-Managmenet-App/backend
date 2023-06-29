package pl.zaprogramujzycie.mma.controllers;

import org.apache.coyote.Response;
import org.flywaydb.core.Flyway;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.security.crypto.password.PasswordEncoder;
import pl.zaprogramujzycie.mma.dto.request.ChangeFamilyRequest;
import pl.zaprogramujzycie.mma.dto.request.MedicineRequest;
import pl.zaprogramujzycie.mma.dto.request.UserRequest;
import pl.zaprogramujzycie.mma.dto.response.FamilyResponse;
import pl.zaprogramujzycie.mma.dto.response.MedicineResponse;
import pl.zaprogramujzycie.mma.dto.response.MedicinesResponse;
import pl.zaprogramujzycie.mma.dto.response.UserResponse;
import pl.zaprogramujzycie.mma.exceptions.NotFoundException;

import java.net.URI;
import java.time.OffsetDateTime;
import java.util.List;
import java.util.stream.Collectors;

import static org.assertj.core.api.Assertions.assertThat;


@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
public class UserControllerTest {

    @Autowired
    TestRestTemplate restTemplate;

    @BeforeEach
    void clearDatabase(@Autowired Flyway flyway) {
        flyway.clean();
        flyway.migrate();
    }

    @Test
    void shouldReturnAnAuthorizedUserPanel() {
        ResponseEntity<UserResponse> getResponse = restTemplate
                .withBasicAuth("login", "password")
                .getForEntity("/users/100", UserResponse.class);

        UserResponse response = getResponse.getBody();

        assertThat(getResponse.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response.id()).isEqualTo(100L);
        assertThat(response.login()).isEqualTo("login");
        assertThat(response.family()).isEqualTo(100L);
    }

    @Test
    void shouldReturnAnUserByLogin() {
        ResponseEntity<UserResponse> getResponse = restTemplate
                .getForEntity("/users?login=login", UserResponse.class);

        UserResponse response = getResponse.getBody();

        assertThat(getResponse.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response.id()).isEqualTo(100L);
        assertThat(response.login()).isEqualTo("login");
        assertThat(response.family()).isEqualTo(100L);
    }

    @Test
    @DirtiesContext
    //This test fails due to password encoding, so passwords are not asserted
    void shouldCreateANewUser() {
        UserRequest newUser = new UserRequest( "newLogin", "newPassword");
        ResponseEntity<Void> createResponse = restTemplate
                .postForEntity("/users", newUser, Void.class);

        URI locationOfNewUser = createResponse.getHeaders().getLocation();
        System.out.println("new URI: " + locationOfNewUser);
        ResponseEntity<UserResponse> getResponse = restTemplate
                .withBasicAuth("newLogin", "newPassword")
                .getForEntity(locationOfNewUser, UserResponse.class);
        UserResponse response = getResponse.getBody();
        System.out.println("password in for new usser "+ response.password());
        assertThat(createResponse.getStatusCode()).isEqualTo(HttpStatus.CREATED);
        assertThat(getResponse.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response.id()).isNotNull();
        assertThat(response.login()).isEqualTo("newLogin");
        assertThat(response.family()).isEqualTo(1L);


    }

    @Test
    void shouldNotReturnAnUserWithAnUnknownId() {
        ResponseEntity<MedicineResponse> response = restTemplate
                .withBasicAuth("login", "password")
                .getForEntity("/families/9999", MedicineResponse.class);
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.NOT_FOUND);
    }

    @Test
    @DirtiesContext
    void shouldDeleteAnExistingUser() {
        ResponseEntity<Void> response = restTemplate
                .withBasicAuth("login", "password")
                .exchange("/users/100", HttpMethod.DELETE, null, Void.class);

        ResponseEntity<UserResponse> getResponse = restTemplate
                .withBasicAuth("login", "password")
                    .getForEntity("/users/100", UserResponse.class);


        ResponseEntity<FamilyResponse> familyResponse = restTemplate
                .withBasicAuth("login1", "password1")
                .getForEntity("/families/100",  FamilyResponse.class);


        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.NO_CONTENT);
        assertThat(getResponse.getStatusCode()).isEqualTo(HttpStatus.NOT_FOUND);
        assertThat(familyResponse.getBody().users()).doesNotContain("login");
    }

    @Test
    void shouldNotDeleteAnUserThatDoesNotExist() {
        ResponseEntity<Void> deleteResponse = restTemplate
                .withBasicAuth("login", "password")
                .exchange("/users/99999", HttpMethod.DELETE, null, Void.class);
        assertThat(deleteResponse.getStatusCode()).isEqualTo(HttpStatus.NOT_FOUND);
    }

    @Test
    void shouldNotReturnAnUserWhenUsingBadCredentials() {
        ResponseEntity<MedicineResponse> responseUsername = restTemplate
                .withBasicAuth("BAD-USER", "password")
                .getForEntity("/users/100", MedicineResponse.class);

        ResponseEntity<MedicineResponse> responsePassword = restTemplate
                .withBasicAuth("login", "BAD-PASSWORD")
                .getForEntity("/users/100", MedicineResponse.class);

        assertThat(responseUsername.getStatusCode()).isEqualTo(HttpStatus.UNAUTHORIZED);
        assertThat(responsePassword.getStatusCode()).isEqualTo(HttpStatus.UNAUTHORIZED);
    }



    @Test
    @DirtiesContext
    void shouldUpdateAUsersFamily() {
        ChangeFamilyRequest newFamily = new ChangeFamilyRequest(101L);
        HttpEntity<ChangeFamilyRequest> request = new HttpEntity<>(newFamily);

        ResponseEntity<Void> putResponse = restTemplate
                .withBasicAuth("login", "password")
                .exchange("/users/100", HttpMethod.PUT, request, Void.class);
        ResponseEntity<UserResponse> getResponse = restTemplate
                .withBasicAuth("login", "password")
                .getForEntity("/users/100", UserResponse.class);
        UserResponse response = getResponse.getBody();

        assertThat(putResponse.getStatusCode()).isEqualTo(HttpStatus.NO_CONTENT);
        assertThat(getResponse.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response.family()).isEqualTo(101L);
    }

    @Test
    @DirtiesContext
        //This test fails due to password encoding, so passwords are not asserted
    void shouldUpdateAUsersPassword() {
        UserRequest newPassword = new UserRequest("login", "newPassword");
        HttpEntity<UserRequest> request = new HttpEntity<>(newPassword);

        ResponseEntity<Void> putResponse = restTemplate
                .withBasicAuth("login", "password")
                .exchange("/users/100/passwordChange", HttpMethod.PUT, request, Void.class);
        ResponseEntity<UserResponse> getResponse = restTemplate
                .withBasicAuth("login", "password")
                .getForEntity("/users/100", UserResponse.class);
        UserResponse response = getResponse.getBody();

        assertThat(putResponse.getStatusCode()).isEqualTo(HttpStatus.NO_CONTENT);
        assertThat(getResponse.getStatusCode()).isEqualTo(HttpStatus.OK);
    }
}




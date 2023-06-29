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
import pl.zaprogramujzycie.mma.dto.request.FamilyMemberRequest;
import pl.zaprogramujzycie.mma.dto.request.FamilyRequest;
import pl.zaprogramujzycie.mma.dto.response.FamilyMemberResponse;
import pl.zaprogramujzycie.mma.dto.response.FamilyResponse;
import pl.zaprogramujzycie.mma.dto.response.PrescribedMedicineResponse;
import pl.zaprogramujzycie.mma.entities.User;

import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;


@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
public class FamilyControllerTest {
    @Autowired
    TestRestTemplate restTemplate;

    final List<Long> usersIds = new ArrayList<>();

    @BeforeEach
    void clearDatabase(@Autowired Flyway flyway) {
        flyway.clean();
        flyway.migrate();
    }

    @BeforeEach
    void setup() {
        usersIds.add(100L);
        usersIds.add(101L);

    }



    @Test
    void shouldReturnAFamily() {
        ResponseEntity<FamilyResponse> getResponse = restTemplate
                .withBasicAuth("login", "password")
                .getForEntity("/families/100", FamilyResponse.class);

        FamilyResponse response = getResponse.getBody();

        assertThat(getResponse.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response.id()).isEqualTo(100);
        assertThat(response.users()).containsExactlyInAnyOrder("login", "login1", "login2");
        assertThat(response.medicines()).containsExactlyInAnyOrder(1000L, 100L, 101L);
        assertThat(response.members()).containsExactlyInAnyOrder(100L, 101L, 102L, 104L);

    }

    @Test
    void shouldNotReturnAFamilyWithAnUnknownId() {
        ResponseEntity<String> response = restTemplate
                .withBasicAuth("login", "password")
                .getForEntity("/prescribedMedicines/1000", String.class);
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.NOT_FOUND);
    }


    @Test
    void shouldNotReturnAFamilyWhenUsingBadCredentials() {
        ResponseEntity<String> response = restTemplate
                .withBasicAuth("BAD-USER", "password")
                .getForEntity("/families/100", String.class);
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.UNAUTHORIZED);

        response = restTemplate
                .withBasicAuth("login", "BAD-PASSWORD")
                .getForEntity("/families/100", String.class);
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.UNAUTHORIZED);
    }

    @Test
    void shouldNotAllowAccessToFamilyTheyDoNotOwn() {
        ResponseEntity<String> response = restTemplate
                .withBasicAuth("password", "password")
                .getForEntity("/families/103", String.class);
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.UNAUTHORIZED);
    }
    @Test
    @DirtiesContext
    void shouldAddAnUserToTheList() {
        FamilyRequest usersUpdate = new FamilyRequest(104L );
        HttpEntity<FamilyRequest> request = new HttpEntity<>(usersUpdate);
        ResponseEntity<Void> putResponse = restTemplate
                .withBasicAuth("login3", "password3")
                .exchange("/families/100", HttpMethod.PUT, request, Void.class);

        ResponseEntity<FamilyResponse> getResponse = restTemplate
                .withBasicAuth("login", "password")
                .getForEntity("/families/100",
                        FamilyResponse.class);

        FamilyResponse response = getResponse.getBody();
        assertThat(putResponse.getStatusCode()).isEqualTo(HttpStatus.NO_CONTENT);
        assertThat(getResponse.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response.id()).isNotNull();
        assertThat(response.users()).containsExactlyInAnyOrder("login1", "login2", "login3", "login");
    }


    @Test
    void shouldNotUpdateAFamilyThatDoesNotExist() {
        FamilyRequest usersUpdate = new FamilyRequest(104L );
        HttpEntity<FamilyRequest> request = new HttpEntity<>(usersUpdate);
        ResponseEntity<Void> response = restTemplate
                .withBasicAuth("login", "password")
                .exchange("/families/99999", HttpMethod.PUT, request, Void.class);
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.NOT_FOUND);
    }
}




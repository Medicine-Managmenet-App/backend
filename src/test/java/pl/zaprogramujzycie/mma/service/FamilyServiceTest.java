package pl.zaprogramujzycie.mma.service;


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
import pl.zaprogramujzycie.mma.dto.request.FamilyRequest;
import pl.zaprogramujzycie.mma.dto.request.PrescriptionRequest;
import pl.zaprogramujzycie.mma.dto.response.PrescriptionResponse;
import pl.zaprogramujzycie.mma.entities.Family;
import pl.zaprogramujzycie.mma.services.FamilyService;

import java.net.URI;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;


@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
public class FamilyServiceTest {

    @Autowired
    private FamilyService service;


    @Test
    public void shouldCreateFamily (){
        Family newFamily = service.create();
        assertThat(newFamily.getId()).isNotNull();
        assertThat(newFamily.getId()).isEqualTo(1);
    }
    @Test
    public void shouldDeleteUserFromList () {
        //ToDo write method body
    }

    @Test
    void shouldDeleteFamilyWhenUsersListIsEmpty() {
        //ToDo add method body
    }


    // @Test
    // @DirtiesContext
    // void shouldDeleteAnExistingFamily() {
    //     ResponseEntity<Void> response = restTemplate
    //             .withBasicAuth("login", "password")
    //             .exchange("/families/100", HttpMethod.DELETE, null, Void.class);
    //
    //     ResponseEntity<String> getResponse = restTemplate
    //             .withBasicAuth("login", "password")
    //             .getForEntity("/families/100", String.class);
    //     assertThat(response.getStatusCode()).isEqualTo(HttpStatus.NO_CONTENT);
    //     assertThat(getResponse.getStatusCode()).isEqualTo(HttpStatus.NOT_FOUND);
    // }
    //
    //
    // @Test
    // void shouldNotDeleteAFamilyThatDoesNotExist() {
    //     ResponseEntity<Void> deleteResponse = restTemplate
    //             .withBasicAuth("login", "password")
    //             .exchange("/prescribedMedicines/99999", HttpMethod.DELETE, null, Void.class);
    //     assertThat(deleteResponse.getStatusCode()).isEqualTo(HttpStatus.NOT_FOUND);
    // }
    //
    // @Test
    // void shouldNotAllowDeletionOfFamilyTheyDoNotOwn() {
    //     ResponseEntity<Void> deleteResponse = restTemplate
    //             .withBasicAuth("login", "password")
    //             .exchange("/families/103", HttpMethod.DELETE, null, Void.class);
    //     assertThat(deleteResponse.getStatusCode()).isEqualTo(HttpStatus.NOT_FOUND);
    //
    //     ResponseEntity<String> getResponse = restTemplate
    //             .withBasicAuth("login3", "password3")
    //             .getForEntity("/families/103", String.class);
    //     assertThat(getResponse.getStatusCode()).isEqualTo(HttpStatus.OK);
    // }



}

package pl.zaprogramujzycie.mma.controllers;

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
import pl.zaprogramujzycie.mma.dto.request.FamilyMemberChildRequest;
import pl.zaprogramujzycie.mma.dto.request.FamilyMemberRequest;
import pl.zaprogramujzycie.mma.dto.response.FamilyMemberResponse;
import pl.zaprogramujzycie.mma.dto.response.FamilyMembersResponse;

import java.net.URI;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;


@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
public class FamilyMemberControllerTest {
    @Autowired
    TestRestTemplate restTemplate;


    @BeforeEach
    void clearDatabase(@Autowired Flyway flyway) {
        flyway.clean();
        flyway.migrate();
    }

    @Test
    void shouldReturnAFamilyMember() {
        ResponseEntity<FamilyMemberResponse> getResponse = restTemplate
                .withBasicAuth("login", "password")
                .getForEntity("/families/100/familyMembers/100", FamilyMemberResponse.class);

        FamilyMemberResponse response = getResponse.getBody();

        assertThat(getResponse.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response.getId()).isEqualTo(100);
        assertThat(response.getName()).isEqualTo("John");
    }

    @Test
    void shouldReturnAChildFamilyMember() {
        ResponseEntity<FamilyMemberResponse> getResponse = restTemplate
                .withBasicAuth("login", "password")
                .getForEntity("/families/100/familyMembers/102", FamilyMemberResponse.class);

        FamilyMemberResponse response = getResponse.getBody();

        assertThat(getResponse.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response.getId()).isEqualTo(102);
        assertThat(response.getName()).isEqualTo("Jenny");
        assertThat(response.getAge()).isEqualTo(5);
        assertThat(response.getWeight()).isEqualTo(12.5);
    }

    @Test
    @DirtiesContext
    void shouldCreateANewAdultFamilyMember() {
        FamilyMemberRequest newFamilyMember = new FamilyMemberRequest("Tom", false);
        ResponseEntity<FamilyMemberResponse> createResponse = restTemplate.
                withBasicAuth("login", "password")
                .postForEntity("/families/100/familyMembers/",
                        newFamilyMember, FamilyMemberResponse.class);

        URI locationOfNewMember = createResponse.getHeaders().getLocation();
        ResponseEntity<FamilyMemberResponse> getResponse = restTemplate.
                withBasicAuth("login", "password")
                .getForEntity(locationOfNewMember, FamilyMemberResponse.class);

        FamilyMemberResponse response = getResponse.getBody();
        assertThat(createResponse.getStatusCode()).isEqualTo(HttpStatus.CREATED);
        assertThat(getResponse.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response.getId()).isNotNull();
        assertThat(response.getName()).isEqualTo("Tom");
        assertThat(response.isChild()).isEqualTo(false);
        assertThat(response.getAge()).isEqualTo(0);
        assertThat(response.getWeight()).isEqualTo(0);
    }

    @Test
    @DirtiesContext
    void shouldCreateANewChildFamilyMember() {
        FamilyMemberRequest newFamilyMember = new FamilyMemberChildRequest("Tom", true, 8, 22);
        ResponseEntity<FamilyMemberResponse> createResponse = restTemplate.
                withBasicAuth("login", "password")
                .postForEntity("/families/100/familyMembers/",
                        newFamilyMember, FamilyMemberResponse.class);

        URI locationOfNewMember = createResponse.getHeaders().getLocation();
        ResponseEntity<FamilyMemberResponse> getResponse = restTemplate.
                withBasicAuth("login", "password")
                .getForEntity(locationOfNewMember, FamilyMemberResponse.class);

        FamilyMemberResponse response = getResponse.getBody();
        assertThat(createResponse.getStatusCode()).isEqualTo(HttpStatus.CREATED);
        assertThat(getResponse.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response.getId()).isNotNull();
        assertThat(response.getName()).isEqualTo("Tom");
        assertThat(response.isChild()).isEqualTo(true);
        assertThat(response.getAge()).isEqualTo(8);
        assertThat(response.getWeight()).isEqualTo(22);
    }

    //Disabled due to problem with Jackson subtypes exception surpassing custom NotFoundException
    @Disabled
    @Test
    void shouldNotReturnAFamilyMemberWithAnUnknownId() {
        ResponseEntity<FamilyMemberResponse> response = restTemplate
                .withBasicAuth("login", "password")
                .getForEntity("/families/100/familyMembers/1000", FamilyMemberResponse.class);
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.NOT_FOUND);
    }

    @Test
    void shouldNotReturnAFamilyMemberWhenUsingBadCredentials() {
        ResponseEntity<String> userResponse = restTemplate
                .withBasicAuth("BAD-USER", "password")
                .getForEntity("/families/100/familyMembers/100", String.class);
        ResponseEntity<String> passwordResponse = restTemplate
                .withBasicAuth("login", "BAD-PASSWORD")
                .getForEntity("/families/100/familyMembers/100", String.class);

        assertThat(userResponse.getStatusCode()).isEqualTo(HttpStatus.UNAUTHORIZED);
        assertThat(passwordResponse.getStatusCode()).isEqualTo(HttpStatus.UNAUTHORIZED);
    }


    @Test
    void shouldNotAllowAccessToFamilyMemberTheyDoNotOwn() {
        ResponseEntity<String> response = restTemplate
                .withBasicAuth("login", "password")
                .getForEntity("/families/101/familyMembers/103", String.class);
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.NOT_FOUND);
    }

    @Test
    void shouldReturnAllFamilyMembersWhenListIsRequested() {
        ResponseEntity<FamilyMembersResponse> getResponse = restTemplate
                .withBasicAuth("login", "password")
                .getForEntity("/families/100/familyMembers/", FamilyMembersResponse.class);

        FamilyMembersResponse response = getResponse.getBody();
        List<FamilyMemberResponse> list= response.members();

        assertThat(getResponse.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response.members().size()).isEqualTo(4);
        assertThat(list.stream().map(FamilyMemberResponse::getId)).containsExactlyInAnyOrder(100L, 101L, 102L, 104L);
    }

    @Test
    void shouldReturnAPageOfFamilyMembers() {
        ResponseEntity<FamilyMembersResponse> getResponse = restTemplate
                .withBasicAuth("login", "password")
                .getForEntity("/families/100/familyMembers/?page=0&size=1", FamilyMembersResponse.class);

        FamilyMembersResponse response = getResponse.getBody();

        assertThat(getResponse.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response.members().size()).isEqualTo(1);
    }


    @Test
    @DirtiesContext
    void shouldDeleteAnExistingFamilyMember() {
        ResponseEntity<Void> response = restTemplate
                .withBasicAuth("login", "password")
                .exchange("/families/100/familyMembers/104",
                        HttpMethod.DELETE, null, Void.class);


        ResponseEntity<String> getResponse = restTemplate
                .withBasicAuth("login", "password")
                .getForEntity("/families/100/familyMembers/104",
                        String.class);

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.NO_CONTENT);
        assertThat(getResponse.getStatusCode()).isEqualTo(HttpStatus.NOT_FOUND);
    }


    @Test
    void shouldNotDeleteAFamilyMemberThatDoesNotExist() {
        ResponseEntity<Void> deleteResponse = restTemplate.
                withBasicAuth("login", "password")
                .exchange("/families/100/familyMembers/99999",
                        HttpMethod.DELETE, null, Void.class);
        assertThat(deleteResponse.getStatusCode()).isEqualTo(HttpStatus.NOT_FOUND);
    }

    @Test
    void shouldNotAllowDeletionOfFamilyMemberTheyDoNotOwn() {
        ResponseEntity<Void> deleteResponse = restTemplate
                .withBasicAuth("login", "password")
                .exchange("/families/101/familyMembers/103", HttpMethod.DELETE, null, Void.class);

        ResponseEntity<String> getResponse = restTemplate
                .withBasicAuth("login3", "password3")
                .getForEntity("/families/101/familyMembers/103", String.class);

        assertThat(deleteResponse.getStatusCode()).isEqualTo(HttpStatus.NOT_FOUND);
        assertThat(getResponse.getStatusCode()).isEqualTo(HttpStatus.OK);
    }

    @Test
    @DirtiesContext
    void shouldUpdateAnAdultFamilyMemberName() {
        FamilyMemberRequest nameUpdate = new FamilyMemberRequest("Johnny", false );
        HttpEntity<FamilyMemberRequest> request = new HttpEntity<>(nameUpdate);
        ResponseEntity<Void> putResponse = restTemplate
                .withBasicAuth("login", "password")
                .exchange("/families/100/familyMembers/100", HttpMethod.PUT, request, Void.class);

        ResponseEntity<FamilyMemberResponse> getResponse = restTemplate
                .withBasicAuth("login", "password")
                .getForEntity("/families/100/familyMembers/100",
                        FamilyMemberResponse.class);

        FamilyMemberResponse response = getResponse.getBody();
        assertThat(putResponse.getStatusCode()).isEqualTo(HttpStatus.NO_CONTENT);
        assertThat(getResponse.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response.getId()).isNotNull();
        assertThat(response.getName()).isEqualTo("Johnny");
    }

    @Test
    @DirtiesContext
    void shouldChangeFamilyMemberToChild() {
        FamilyMemberRequest nameUpdate = new FamilyMemberChildRequest("John", true , 8, 22);
        HttpEntity<FamilyMemberRequest> request = new HttpEntity<>(nameUpdate);
        ResponseEntity<Void> putResponse = restTemplate
                .withBasicAuth("login", "password")
                .exchange("/families/100/familyMembers/100", HttpMethod.PUT, request, Void.class);

        ResponseEntity<FamilyMemberResponse> getResponse = restTemplate
                .withBasicAuth("login", "password")
                .getForEntity("/families/100/familyMembers/100",
                        FamilyMemberResponse.class);

        FamilyMemberResponse response = getResponse.getBody();
        assertThat(putResponse.getStatusCode()).isEqualTo(HttpStatus.NO_CONTENT);
        assertThat(getResponse.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response.getId()).isNotNull();
        assertThat(response.getName()).isEqualTo("John");
        assertThat(response.isChild()).isEqualTo(true);
        assertThat(response.getAge()).isEqualTo(8);
        assertThat(response.getWeight()).isEqualTo(22);
    }

    @Test
    @DirtiesContext
    void shouldChangeFamilyMemberToAdult() {
        FamilyMemberRequest nameUpdate = new FamilyMemberRequest("Jenny", false);
        HttpEntity<FamilyMemberRequest> request = new HttpEntity<>(nameUpdate);
        ResponseEntity<Void> putResponse = restTemplate
                .withBasicAuth("login", "password")
                .exchange("/families/100/familyMembers/102", HttpMethod.PUT, request, Void.class);

        ResponseEntity<FamilyMemberResponse> getResponse = restTemplate
                .withBasicAuth("login", "password")
                .getForEntity("/families/100/familyMembers/102",
                        FamilyMemberResponse.class);

        FamilyMemberResponse response = getResponse.getBody();
        assertThat(putResponse.getStatusCode()).isEqualTo(HttpStatus.NO_CONTENT);
        assertThat(getResponse.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response.getId()).isNotNull();
        assertThat(response.getName()).isEqualTo("Jenny");
        assertThat(response.isChild()).isEqualTo(false);
    }

    @Test
    void shouldNotUpdateAFamilyMemberThatDoesNotExist() {
        FamilyMemberRequest unknownMember = new FamilyMemberRequest("Johnny", false );
        HttpEntity<FamilyMemberRequest> request = new HttpEntity<>(unknownMember);
        ResponseEntity<Void> response = restTemplate
                .withBasicAuth("login", "password")
                .exchange("/families/100/familyMembers/99999", HttpMethod.PUT, request, Void.class);
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.NOT_FOUND);
    }
}




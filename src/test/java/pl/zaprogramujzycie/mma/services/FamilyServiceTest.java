package pl.zaprogramujzycie.mma.services;



import org.flywaydb.core.Flyway;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.orm.jpa.JpaObjectRetrievalFailureException;
import org.springframework.test.annotation.DirtiesContext;
import pl.zaprogramujzycie.mma.dto.response.FamilyResponse;
import pl.zaprogramujzycie.mma.entities.Family;
import pl.zaprogramujzycie.mma.exceptions.NotFoundException;

import java.security.Principal;
import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
public class FamilyServiceTest {


    @Autowired
    FamilyService service;


    Principal principal = () -> "login";

    Family mockFamilyMultipleUsers;
    Family mockFamilyOneUser;

    @BeforeEach
    void clearDatabase(@Autowired Flyway flyway) {
        flyway.clean();
        flyway.migrate();
    }


    @BeforeEach
    public void setUp() {
        mockFamilyMultipleUsers = new Family(500L, new ArrayList<>(), new ArrayList<>(), new ArrayList<>());
        mockFamilyOneUser = new Family(501L, new ArrayList<>(), new ArrayList<>(), new ArrayList<>());

        List<String> users = new ArrayList<>();
        List<String> user = new ArrayList<>();

        users.add("login");
        users.add( "login2");
        mockFamilyMultipleUsers.setLogins(users);

        user.add("login");
        mockFamilyOneUser.setLogins(user);
    }


    @Test
    public void shouldGetFamilyById() throws NotFoundException {
        FamilyResponse family = service.findById(principal, 100L);
        assertThat(family.id()).isEqualTo(100L);
        assertThat(family.users()).containsExactlyInAnyOrder("login", "login1", "login2");
    }

    @Test
    public void shouldCreateFamily (){
        Family newFamily = service.create();
        assertThat(newFamily.getId()).isNotNull();
        assertThat(newFamily.getId()).isEqualTo(1);
    }

    @Test
    public void shouldDeleteUserFromList () throws NotFoundException {
        service.removeUserFromTheFamily(100L, "login");
        FamilyResponse family = service.findById(principal, 100L);
        assertThat(family.users()).containsExactlyInAnyOrder("login1", "login2");

    }

    @Test
    void shouldDeleteFamilyWhenUsersListIsEmpty() throws NotFoundException {
        Principal principal2 = () -> "login3";
        service.removeUserFromTheFamily(101, "login3");
        try{
            FamilyResponse family = service.findById(principal2, 101L);
        } catch (NotFoundException ex){
            assertThat(ex.getMessage()).contains("NotFound");
        } catch (JpaObjectRetrievalFailureException jpa) {
            assertThat(jpa.getMessage()).contains("Unable to find");
        }

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

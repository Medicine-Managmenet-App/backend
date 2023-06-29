package pl.zaprogramujzycie.mma.services;



import org.flywaydb.core.Flyway;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
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


    final Principal principal = () -> "login";

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
        Family newFamily = service.create("login5");
        assertThat(newFamily.getId()).isNotNull();
        assertThat(newFamily.getId()).isEqualTo(1);
        assertThat(newFamily.getLogins()).containsExactlyInAnyOrder("login5");
    }

    @Test
    public void shouldDeleteUserFromList () throws NotFoundException {
        service.removeUserFromTheFamily(100L, "login1");
        FamilyResponse family = service.findById(principal, 100L);
        assertThat(family.users()).containsExactlyInAnyOrder("login", "login2");

    }

    @Test
    void shouldDeleteFamilyWhenUsersListIsEmpty() throws NotFoundException {
        Principal principal2 = () -> "login3";
        service.removeUserFromTheFamily(101, "login3");
        try{
            FamilyResponse family = service.findById(principal2, 101L);
        } catch (NotFoundException ex){
            assertThat(ex.getClass()).isEqualTo(NotFoundException.class);
        }
    }

}

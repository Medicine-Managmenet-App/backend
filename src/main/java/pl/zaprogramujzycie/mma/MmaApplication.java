package pl.zaprogramujzycie.mma;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import pl.zaprogramujzycie.mma.entity.FamilyMember;

@SpringBootApplication
@ComponentScan("pl.zaprogramujzycie.mma")
public class MmaApplication {

	public static void main(String[] args) {
		SpringApplication.run(MmaApplication.class, args);
	}

}

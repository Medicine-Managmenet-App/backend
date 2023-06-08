package pl.zaprogramujzycie.mma.utils.swagger;

import io.swagger.v3.oas.models.ExternalDocumentation;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class OpenApiConfig {

    @Bean
    public OpenAPI MedicineManagementAPI() {
        return new OpenAPI()
                .info(new Info().title("Medicine Management API")
                        .description("The API for the Medicine Management App")
                        .version("1.0.0")
                        .license(new License().name("Apache 2.0").url("http://www.apache.org/licenses/LICENSE-2.0")))
                .externalDocs(new ExternalDocumentation()
                        .description("Aleksandra Kemona, aleksandra.kemona@gmail.com"));
    }

}
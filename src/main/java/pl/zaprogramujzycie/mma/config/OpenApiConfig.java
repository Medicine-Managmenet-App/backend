package pl.zaprogramujzycie.mma.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import io.swagger.v3.oas.models.servers.Server;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;

@Configuration
@RequiredArgsConstructor
public class OpenApiConfig {

    private final ApplicationConfiguration configuration;

    @Bean
    public OpenAPI MedicineManagementAPI() {

        Server developmentServer = new Server();
        developmentServer.setUrl(configuration.getOpenApi().getDevelopmentURL());
        developmentServer.setDescription("Server URL in development environment");

        Server productionServer = new Server();
        productionServer.setUrl(configuration.getOpenApi().getProductionURL());
        productionServer.setDescription("Server URL in production environment");

        Contact contact = new Contact();
        contact.setEmail("aleksandra.kemona@gmail.com");
        contact.setName("Aleksandra");

        License license = new License().name("Apache 2.0 License").url("http://www.apache.org/licenses/LICENSE-2.0");

        Info info = new Info()
                .title("Medicine Management API")
                .description("The API for the Medicine Management App")
                .version("0.0.1")
                .license(license);

        return new OpenAPI().info(info).servers(List.of(developmentServer, productionServer));
    }
}
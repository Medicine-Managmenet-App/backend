package pl.zaprogramujzycie.mma.utils.swagger;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import io.swagger.v3.oas.models.servers.Server;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;

@Configuration
public class OpenApiConfig {
    @Value("http://localhost:8080")
    private String developmentURL;

    //ToDo add production url
    @Value("")
    private String productionUrl;

    @Bean
    public OpenAPI MedicineManagementAPI() {
        Server developmentServer = new Server();
        developmentServer.setUrl(developmentURL);
        developmentServer.setDescription("Server URL in development environment");

        Server productionServer = new Server();
        productionServer.setUrl(productionUrl);
        productionServer.setDescription("Server URL in production environment");

        Contact contact = new Contact();
        contact.setEmail("aleksandra.kemona@gmail.com");
        contact.setName("Aleksandra");

        License license = new License().name("Apache 2.0 License").url("http://www.apache.org/licenses/LICENSE-2.0");

        Info info = new Info()
                .title("Medicine Management API")
                .description("The API for the Medicine Management App")
                .version("1.0.0")
                .license(license);

        return new OpenAPI().info(info).servers(List.of(developmentServer, productionServer));
    }
}
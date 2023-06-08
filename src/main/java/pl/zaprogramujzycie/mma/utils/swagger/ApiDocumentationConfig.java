package pl.zaprogramujzycie.mma.utils.swagger;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Contact;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.info.License;
import io.swagger.v3.oas.annotations.servers.Server;

@OpenAPIDefinition(
        info = @Info(
                description = "The API for the Medicine Management App",
                version = "1.0.0",
                title = "Medicine Management API",
                contact = @Contact(
                        name = "Aleksandra Kemona",
                        email = "aleksandra.kemona@gmail.com"
                ),
                license = @License(
                        name = "Apache 2.0",
                        url = "http://www.apache.org/licenses/LICENSE-2.0"
                )
        ),
        servers = {@Server(url = "virtserver.swaggerhub.com/MedicineMA/MmaAPI/1.0.0")}
)
public interface ApiDocumentationConfig {

}

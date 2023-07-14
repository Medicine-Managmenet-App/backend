package pl.zaprogramujzycie.mma.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Data
@Configuration
@ConfigurationProperties("myConfig")
public class ApplicationConfiguration {

    private OpenApi openApi = new OpenApi();
    private Jwt jwt;

    @Data
    public class OpenApi {
        private String developmentURL;
        private String productionURL;
    }

    @Data
    public class Jwt {
        private String signKey;
        private long expiration;
    }


}

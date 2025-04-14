package esogu.ilerijava.integration.config;


//import lombok.Value;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value; // factory annotation must be used for reading value from properties
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestClient;
import org.springframework.web.client.RestTemplate;

@Configuration
public class RestClientConfig {

    @Value("${external.api.base-url}")
    private String baseUrl;

    @Bean
    @Qualifier("integrationRestClient")
    public RestClient integrationRestClient() {
        return RestClient.builder()
                .baseUrl(baseUrl)
                .defaultHeader("Accept", "application/json")
                .defaultHeader("Content-Type", "application/json-patch+json") // ✅ düzeltme burada
                .build();
    }

    @Bean
    public RestTemplate restTemplate() {
        return new RestTemplate();
    }

    public String getBaseUrl() {
        return baseUrl;
    }

}

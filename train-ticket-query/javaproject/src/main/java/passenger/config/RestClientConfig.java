package passenger.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.web.client.RestClient;

@Configuration
public class RestClientConfig {

    private String authToken;
    private String tcddApiBaseUrl;
    private String tcddCdnBaseUrl;

    public RestClientConfig(@Value("${TCDD_AUTH_TOKEN}") String authToken,
                            @Value("${TCDD_API_BASE_URL}") String tcddApiBaseUrl,
                            @Value("${TCDD_CDN_BASE_URL}") String tcddCdnBaseUrl) {
        this.authToken = authToken;
        this.tcddApiBaseUrl = tcddApiBaseUrl;
        this.tcddCdnBaseUrl = tcddCdnBaseUrl;
    }

    @Bean(name = "tcddRestClient")
    public RestClient tcddRestClient() {
        return RestClient.builder()
                .baseUrl(tcddApiBaseUrl)
                .defaultHeader(HttpHeaders.AUTHORIZATION, authToken)
                .defaultHeader("Unit-Id", "3895")
                .defaultHeader(HttpHeaders.ACCEPT, MediaType.APPLICATION_JSON_VALUE)
                .defaultHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
                .build();
    }

    @Bean(name = "tcddCdnRestClient")
    public RestClient tcddCdnRestClient() {
        return RestClient.builder()
                .baseUrl(tcddCdnBaseUrl)
                .defaultHeader(HttpHeaders.AUTHORIZATION, authToken)
                .defaultHeader("Unit-Id", "3895")
                .defaultHeader(HttpHeaders.ACCEPT, MediaType.APPLICATION_JSON_VALUE)
                .defaultHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
                .build();
    }
}

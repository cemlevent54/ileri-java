package esogu.ilerijava.integration.services;

import com.fasterxml.jackson.databind.ObjectMapper;
import esogu.ilerijava.integration.config.RestClientConfig;
import esogu.ilerijava.integration.dto.request.login.LoginRequest;
import esogu.ilerijava.integration.dto.request.refreshToken.RefreshTokenRequest;
import esogu.ilerijava.integration.dto.response.login.LoginResponse;
import esogu.ilerijava.integration.dto.response.refreshToken.RefreshTokenResponse;
import esogu.ilerijava.integration.interfaces.AuthService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClient;

@Service
@Slf4j
public class AuthServiceImpl implements AuthService {

    private final RestClient integrationRestClient;
    private final ObjectMapper objectMapper;
    private final RestClientConfig restClientConfig;

    public AuthServiceImpl(RestClientConfig restClientConfig, @Qualifier("integrationRestClient") RestClient integrationRestClient, ObjectMapper objectMapper) {
        this.restClientConfig = restClientConfig;
        this.integrationRestClient = integrationRestClient;
        this.objectMapper = objectMapper;
    }

    @Override
    public ResponseEntity<LoginResponse> login(LoginRequest request) {
        try {
            String userName = request.getUserName();
            String password = request.getPassword();

            ResponseEntity<LoginResponse> rawResponse = integrationRestClient.post()
                    .uri("/IntegrationKullanici/Login")
                    .body(request)
                    .retrieve()
                    .toEntity(LoginResponse.class);

            log.info("API URL: {}", restClientConfig.getBaseUrl() + "/IntegrationKullanici/Login");

            if (rawResponse.getBody() != null) {
                LoginResponse response = rawResponse.getBody();

                if (rawResponse.getStatusCode().is2xxSuccessful() && Boolean.TRUE.equals(response.getStatus())) {
                    log.info("Login BAŞARILI: {} - {}", userName, password);
                    return ResponseEntity.ok(response);
                } else {
                    log.warn("Login BAŞARISIZ: {} - {} | Mesaj: {}", userName, password, response.getMessage());
                    return ResponseEntity.status(rawResponse.getStatusCode()).body(response);
                }
            }
            else {
                throw new RuntimeException("API'den gecerli bir JSON yanit alinamadi.");
            }




        }
        catch (Exception e) {
            log.error("Login failed: {}", e.getMessage());
            throw new RuntimeException("Login işlemi sırasında bir hata oluştu: " + e.getMessage());
        }


    }

    @Override
    public ResponseEntity<RefreshTokenResponse> refreshToken(RefreshTokenRequest request) {
        try {
            String refreshToke = request.getRefreshToken();

            ResponseEntity<RefreshTokenResponse> rawResponse = integrationRestClient.post()
                    .uri("/IntegrationKullanici/RefreshToken")
                    .body(request)
                    .retrieve()
                    .toEntity(RefreshTokenResponse.class);

            log.info("API URL: {}", restClientConfig.getBaseUrl() + "/IntegrationKullanici/RefreshToken");

            if (rawResponse.getBody() != null) {
                RefreshTokenResponse response = rawResponse.getBody();

                if (rawResponse.getStatusCode().is2xxSuccessful() && Boolean.TRUE.equals(response.getStatus())) {
                    log.info("Refresh token BAŞARILI: {}", refreshToke);
                    return ResponseEntity.ok(response);
                } else {
                    log.warn("Refresh token BAŞARISIZ: {} | Mesaj: {}", refreshToke, response.getMessage());
                    return ResponseEntity.status(rawResponse.getStatusCode()).body(response);
                }
            }
            else {
                throw new RuntimeException("API'den gecerli bir JSON yanit alinamadi.");
            }


        } catch(Exception ex) {
            log.error("Refresh token failed: {}", ex.getMessage());
            throw new RuntimeException("Refresh token işlemi sırasında bir hata oluştu: " + ex.getMessage());
        }
    }

}

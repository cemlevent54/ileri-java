package esogu.ilerijava.integration.dto.response.refreshToken;

import esogu.ilerijava.integration.dto.response.login.JwtToken;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import org.springframework.boot.autoconfigure.security.oauth2.resource.OAuth2ResourceServerProperties;

@Data
@Schema(description = "Refresh Token Response DTO")
public class RefreshTokenResponse {

    @Schema(description = "Jwt Token", example = "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9...")
    private JwtToken jwtToken;

    @Schema(description = "Refresh token", example = "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9...")
    private String refreshToken;

    @Schema(description = "Message of the response", example = "success")
    private String message;

    @Schema(description = "Status of the response", example = "success")
    private String status;
}

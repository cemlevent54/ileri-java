package esogu.ilerijava.integration.dto.response.login;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.time.Instant;

@Data
@Schema(description = "JWT Token DTO")
public class JwtToken {

    @Schema(description = "Access token", example = "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9...")
    private String accessToken;
    @Schema(description = "Expiration of token", example = "2023-10-01T12:00:00Z")
    private Instant expiration;
}

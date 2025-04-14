package esogu.ilerijava.integration.dto.response.login;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
@Schema(description = "Login Response DTO")
public class LoginResponse {

    @Schema(description = "JWT Token", implementation = JwtToken.class)
    private JwtToken jwtToken;

    @Schema(description = "Refresh token", example = "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9...")
    private String refreshToken;

    @Schema(description = "Message", example = "Login successful")
    private String message;

    @Schema(description = "Status", example = "true")
    private String status;
}

package esogu.ilerijava.integration.dto.request.login;


import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
@Schema(description = "Login Request DTO")
public class LoginRequest {

    @Schema(description = "Username for login", example = "user123")
    private String userName;

    @Schema(description = "Password for login", example = "password123")
    private String password;
}

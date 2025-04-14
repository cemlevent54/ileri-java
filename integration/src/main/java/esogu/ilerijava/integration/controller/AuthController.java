package esogu.ilerijava.integration.controller;

import esogu.ilerijava.integration.dto.request.login.LoginRequest;
import esogu.ilerijava.integration.dto.request.refreshToken.RefreshTokenRequest;
import esogu.ilerijava.integration.dto.response.login.LoginResponse;
import esogu.ilerijava.integration.dto.response.refreshToken.RefreshTokenResponse;
import esogu.ilerijava.integration.interfaces.AuthService;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/IntegrationKullanici")
@RequiredArgsConstructor
public class AuthController {
    private final AuthService authService;

    @Operation(summary = "Login Endpoint", description = "Token almak için kullanılır.")
    @PostMapping("/login")
    public ResponseEntity<LoginResponse> login(@RequestBody LoginRequest request) {
        return authService.login(request);
    }

    @Operation(summary = "Refresh Token Endpoint", description = "Token yenilemek için kullanılır.")
    @PostMapping("/refresh-token")
    public ResponseEntity<RefreshTokenResponse> refreshToken(@RequestBody RefreshTokenRequest request) {
        return authService.refreshToken(request);
    }
}

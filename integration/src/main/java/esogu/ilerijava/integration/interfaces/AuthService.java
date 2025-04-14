package esogu.ilerijava.integration.interfaces;

import esogu.ilerijava.integration.dto.request.refreshToken.RefreshTokenRequest;
import esogu.ilerijava.integration.dto.response.login.LoginResponse;
import esogu.ilerijava.integration.dto.request.login.LoginRequest;
import esogu.ilerijava.integration.dto.response.refreshToken.RefreshTokenResponse;
import org.springframework.http.ResponseEntity;

public interface AuthService {

    ResponseEntity<LoginResponse> login(LoginRequest request);
    ResponseEntity<RefreshTokenResponse> refreshToken(RefreshTokenRequest request);

}

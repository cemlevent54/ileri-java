package esogu.ilerijava.integration.controller;


import esogu.ilerijava.integration.config.RestClientConfig;
import io.swagger.v3.core.util.Json;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import javax.print.attribute.standard.Media;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/integration")
@RequiredArgsConstructor
public class IntegrationController {

    private final RestClientConfig restClientConfig;


    @Operation(summary = "Url Endpoint", description = "Base URL'i kontrol etmek için kullanılır.")
    @PostMapping("/url")
    public String checkUrl() {
        String url = restClientConfig.getBaseUrl(); // örnek: https://testortami.com.tr/api/login
        return url;
    }


}

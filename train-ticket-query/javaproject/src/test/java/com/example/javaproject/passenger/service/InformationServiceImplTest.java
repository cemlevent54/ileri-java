package com.example.javaproject.passenger.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestClient;
import passenger.service.InformationServiceImpl;
import org.mockito.Mockito;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.mock;

public class InformationServiceImplTest {
    private InformationServiceImpl informationService;
    private RestClient restClient;
    private ObjectMapper objectMapper;

    @BeforeEach
    public void setup() {
        restClient = mock(RestClient.class);
        objectMapper = new ObjectMapper();
        informationService = new InformationServiceImpl(restClient, objectMapper);
    }

    @Test
    public void testGetStationIdByStationName_shouldReturnId() throws Exception {
        // Mock JSON veri
        String mockJson = "[{\"id\":5,\"name\":\"ARİFİYE\",\"district\":{\"city\":{\"name\":\"SAKARYA\"}}}]";

        // Spy ile getStationPairs() metodunu mockla
        InformationServiceImpl spyService = Mockito.spy(informationService);
        doReturn(ResponseEntity.ok(mockJson)).when(spyService).getStationPairs();

        // Test edilecek metodu çağır
        ResponseEntity<String> response = spyService.getStationIdByStationName("ARİFİYE , SAKARYA");

        // Beklenen sonuçları assert et
        assertNotNull(response);
        assertTrue(response.getStatusCode().is2xxSuccessful());
        assertEquals("5", response.getBody());
    }
}

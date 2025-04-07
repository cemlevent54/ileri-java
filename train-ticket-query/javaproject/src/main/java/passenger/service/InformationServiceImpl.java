package passenger.service;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpStatusCodeException;
import org.springframework.web.client.RestClient;
import passenger.dto.stations.StationDTO;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import passenger.service.interfaces.InformationService;

@Service
@Slf4j
public class InformationServiceImpl implements InformationService {

    private final RestClient restClient;
    private final ObjectMapper objectMapper;

    private static final String URL = "https://cdn-api-prod-ytp.tcddtasimacilik.gov.tr/datas/station-pairs-INTERNET.json?environment=dev&userId=1";

    @Value("${TCDD_AUTH_TOKEN}")
    private String authToken;

    public InformationServiceImpl(@Qualifier("tcddCdnRestClient") RestClient restClient, ObjectMapper objectMapper) {
        this.restClient = restClient;
        this.objectMapper = objectMapper;
    }

    @Override
    public ResponseEntity<String> getStationPairs() {
        try {
            ResponseEntity<String> rawResponse = restClient.get()
                    .uri("/datas/station-pairs-INTERNET.json?environment=dev&userId=1")
                    .retrieve()
                    .toEntity(String.class);

            if (rawResponse.getBody() == null || rawResponse.getBody().isBlank()) {
                throw new RuntimeException("API'den geçerli bir JSON yanıtı alınamadı.");
            }

            return ResponseEntity.ok(rawResponse.getBody());

        } catch (HttpStatusCodeException e) {
            log.error("Error while getting station pairs", e);
            return ResponseEntity.status(e.getStatusCode()).body(e.getResponseBodyAsString());
        } catch (Exception ex) {
            log.error("Error while getting station pairs", ex);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error while getting station pairs");
        }
    }

    @Override
    public ResponseEntity<String> getStationByNameAndCity(String stationParam) {
        try {
            String[] parts = stationParam.split(" , ");
            if (parts.length != 2) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                        .body("Hatalı format! Doğru format: 'İSTASYONADI , ŞEHİRADI'");
            }
            String stationName = parts[0].trim();
            String cityName = parts[1].trim();

            // API'den istasyon verisini string formatında al
            ResponseEntity<String> rawResponse = getStationPairs();
            if (!rawResponse.getStatusCode().is2xxSuccessful() || rawResponse.getBody() == null) {
                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("İstasyon verileri alınamadı.");
            }

            // JSON verisini List<StationDTO> formatına çevir
            List<StationDTO> stations = objectMapper.readValue(
                    rawResponse.getBody(), new TypeReference<List<StationDTO>>() {});

            // Verilen name ve cityName ile eşleşen istasyonu bul
            Optional<StationDTO> matchedStation = stations.stream()
                    .filter(station ->
                            station.getName().equalsIgnoreCase(stationName) &&
                                    station.getDistrict() != null &&
                                    station.getDistrict().getCity() != null &&
                                    station.getDistrict().getCity().getName().equalsIgnoreCase(cityName))
                    .findFirst();

            return matchedStation.map(station -> {
                try {
                    return ResponseEntity.ok(objectMapper.writeValueAsString(station));
                } catch (Exception e) {
                    log.error("Error converting station data to JSON", e);
                    return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("İstasyon verisi JSON formatına çevrilemedi.");
                }
            }).orElse(ResponseEntity.status(HttpStatus.NOT_FOUND).body("İstasyon bulunamadı: " + stationName + ", " + cityName));

        } catch (Exception e) {
            log.error("Error while fetching station by name and city", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("İstasyon getirilirken hata oluştu.");
        }
    }

    @Override
    public ResponseEntity<String> getStationIdByStationName(String stationParam) {
        try {
            ResponseEntity<String> rawResponse = getStationByNameAndCity(stationParam);

            if (!rawResponse.getStatusCode().is2xxSuccessful() || rawResponse.getBody() == null) {
                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("İstasyon verisi alınamadı.");
            }

            Map<String, Object> stationData = objectMapper.readValue(rawResponse.getBody(), new TypeReference<Map<String, Object>>() {});
            if (stationData.containsKey("id")) {
                return ResponseEntity.ok(String.valueOf(stationData.get("id")));
            } else {
                log.warn("JSON içinde 'id' bulunamadı: {}", rawResponse.getBody());
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("İstasyon ID bulunamadı.");
            }

        } catch (Exception e) {
            log.error("Error while fetching station ID", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("İstasyon ID alınırken hata oluştu.");
        }
    }
}

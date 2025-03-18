package passenger.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.apache.coyote.Response;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.client.HttpStatusCodeException;
import org.springframework.web.client.RestClient;
import passenger.config.RestClientConfig;
import com.fasterxml.jackson.core.type.TypeReference;
import passenger.dto.stations.StationDTO;

import java.util.List;
import java.util.Map;
import java.util.Optional;

@Service
@Slf4j
public class InformationService {
    private final RestClient restClient;
    private final ObjectMapper objectMapper;
    private static final String API_URL = "https://web-api-prod-ytp.tcddtasimacilik.gov.tr/tms/train/train-availability?environment=dev&userId=1";
    private static final String AUTH_TOKEN = "Bearer eyJhbGciOiJSUzI1NiIsInR5cCIgOiAiSldUIiwia2lkIiA6ICJlVFFicDhDMmpiakp1cnUzQVk2a0ZnV196U29MQXZIMmJ5bTJ2OUg5THhRIn0.eyJleHAiOjE3MjEzODQ0NzAsImlhdCI6MTcyMTM4NDQxMCwianRpIjoiYWFlNjVkNzgtNmRkZS00ZGY4LWEwZWYtYjRkNzZiYjZlODNjIiwiaXNzIjoiaHR0cDovL3l0cC1wcm9kLW1hc3RlcjEudGNkZHRhc2ltYWNpbGlrLmdvdi50cjo4MDgwL3JlYWxtcy9tYXN0ZXIiLCJhdWQiOiJhY2NvdW50Iiwic3ViIjoiMDAzNDI3MmMtNTc2Yi00OTBlLWJhOTgtNTFkMzc1NWNhYjA3IiwidHlwIjoiQmVhcmVyIiwiYXpwIjoidG1zIiwic2Vzc2lvbl9zdGF0ZSI6IjAwYzM4NTJiLTg1YjEtNDMxNS04OGIwLWQ0MWMxMTcyYzA0MSIsImFjciI6IjEiLCJyZWFsbV9hY2Nlc3MiOnsicm9sZXMiOlsiZGVmYXVsdC1yb2xlcy1tYXN0ZXIiLCJvZmZsaW5lX2FjY2VzcyIsInVtYV9hdXRob3JpemF0aW9uIl19LCJyZXNvdXJjZV9hY2Nlc3MiOnsiYWNjb3VudCI6eyJyb2xlcyI6WyJtYW5hZ2UtYWNjb3VudCIsIm1hbmFnZS1hY2NvdW50LWxpbmtzIiwidmlldy1wcm9maWxlIl19fSwic2NvcGUiOiJvcGVuaWQgZW1haWwgcHJvZmlsZSIsInNpZCI6IjAwYzM4NTJiLTg1YjEtNDMxNS04OGIwLWQ0MWMxMTcyYzA0MSIsImVtYWlsX3ZlcmlmaWVkIjpmYWxzZSwicHJlZmVycmVkX3VzZXJuYW1lIjoid2ViIiwiZ2l2ZW5fbmFtZSI6IiIsImZhbWlseV9uYW1lIjoiIn0.AIW_4Qws2wfwxyVg8dgHRT9jB3qNavob2C4mEQIQGl3urzW2jALPx-e51ZwHUb-TXB-X2RPHakonxKnWG6tDIP5aKhiidzXDcr6pDDoYU5DnQhMg1kywyOaMXsjLFjuYN5PAyGUMh6YSOVsg1PzNh-5GrJF44pS47JnB9zk03Pr08napjsZPoRB-5N4GQ49cnx7ePC82Y7YIc-gTew2baqKQPz9_v381Gbm2V38PZDH9KldlcWut7kqQYJFMJ7dkM_entPJn9lFk7R5h5j_06OlQEpWRMQTn9SQ1AYxxmZxBu5XYMKDkn4rzIIVCkdTPJNCt5PvjENjClKFeUA1DOg";
    private static final String URL = "https://cdn-api-prod-ytp.tcddtasimacilik.gov.tr/datas/station-pairs-INTERNET.json?environment=dev&userId=1";


    public InformationService(RestClient restClient, ObjectMapper objectMapper) {
        this.restClient = restClient;
        this.objectMapper = objectMapper;
    }

    public ResponseEntity<String> getStationPairs() {
        try {
            ResponseEntity<String> rawResponse = restClient.get()
                    .uri(URL)
                    .header(HttpHeaders.AUTHORIZATION,AUTH_TOKEN)
                    .header("Unit-Id", "3895")
                    .header(HttpHeaders.ACCEPT, MediaType.APPLICATION_JSON_VALUE)
                    .header(HttpHeaders.ACCEPT,MediaType.APPLICATION_JSON_VALUE)
                    .retrieve()
                    .toEntity(String.class);

//            log.info("API Response: {}", rawResponse.getBody());

            if (rawResponse.getBody() == null || rawResponse.getBody().isBlank()) {
                throw new RuntimeException("API'den geçerli bir JSON yanıtı alınamadı.");
            }

            return ResponseEntity.ok(rawResponse.getBody());



        } catch (HttpStatusCodeException e) {
            log.error("Error while getting station pairs", e);
            return ResponseEntity.status(e.getStatusCode()).body(e.getResponseBodyAsString());
        }
        catch (Exception ex) {
            log.error("Error while getting station pairs", ex);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error while getting station pairs");
        }
    }

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

    public ResponseEntity<String> getStationIdByStationName(String stationParam) {
        try {
            ResponseEntity<String> rawResponse = getStationByNameAndCity(stationParam);

            // Eğer API çağrısı başarısızsa, hata mesajını döndür
            if (!rawResponse.getStatusCode().is2xxSuccessful() || rawResponse.getBody() == null) {
                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("İstasyon verisi alınamadı.");
            }

            // JSON yanıtını bir Map'e çevirerek 'id' değerini çek
            Map<String, Object> stationData = objectMapper.readValue(rawResponse.getBody(), new TypeReference<Map<String, Object>>() {});

            // 'id' varsa, döndür
            if (stationData.containsKey("id")) {
                return ResponseEntity.ok(String.valueOf(stationData.get("id")));
            } else {
                log.warn("JSON içinde 'id' bulunamadı: {}", rawResponse.getBody());
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("İstasyon ID bulunamadı.");
            }
        } catch (Exception e) {
            log.error("Error while fetching station by name and city", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("İstasyon getirilirken hata oluştu.");
        }
    }


}

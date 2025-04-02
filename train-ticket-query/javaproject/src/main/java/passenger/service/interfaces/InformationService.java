package passenger.service.interfaces;

import org.springframework.http.ResponseEntity;


public interface InformationService {
    ResponseEntity<String> getStationPairs();
    ResponseEntity<String> getStationByNameAndCity(String stationParam);
    ResponseEntity<String> getStationIdByStationName(String stationParam);
}

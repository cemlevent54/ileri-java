package passenger.controller;

import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import passenger.service.InformationService;

@RestController
@RequestMapping("/api/infos")
@RequiredArgsConstructor
public class InformationController {
    private final InformationService infoService;

    @Operation(summary = "Station Pair getir", description = "TCDD API'den tren seferlerini getirir.")
    @GetMapping(value = "/station-pairs", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> getTrains() {
        try {
            return infoService.getStationPairs();
        } catch (Exception e) {
            return ResponseEntity.internalServerError().build();
        }
    }

    @Operation(summary = "Parametreli Station Pair getir", description = "TCDD API'den tren seferlerini getirir.")
    @GetMapping(value = "/station/{stationname}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> getStationByNameAndCity(
            @RequestParam String stationName) {
        return infoService.getStationByNameAndCity(stationName);
    }

    @Operation(summary = "Station ismiyle station id getir", description = "Station ismiyle station id getir.")
    @GetMapping(value="/stationid/{stationname}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> getStationIdByName(@RequestParam String stationName){
        return infoService.getStationIdByStationName(stationName);
    }
}






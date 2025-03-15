package passenger.controller;


import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import passenger.dto.SeferRequestDto;
import passenger.dto.seferResponse.SeferResponseDto;
import passenger.service.PassengerService;

@RestController
@RequestMapping("/api/trains")
@RequiredArgsConstructor
public class PassengerController {

    private final PassengerService passengerService;

    @Operation(summary = "Seferleri Getir", description = "TCDD API'den tren seferlerini getirir.")
    @PostMapping(value = "/search", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<SeferResponseDto> getTrains(@RequestBody SeferRequestDto seferRequestDto) {
        try {
            return passengerService.getTrains(seferRequestDto);
        } catch (Exception e) {
            return ResponseEntity.internalServerError().build();
        }
    }
}

package passenger.controller;

import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import passenger.dto.SeferRequestDto;
import passenger.dto.seferResponse.Sefer;
import passenger.dto.seferResponse.SeferResponseDto;
import passenger.helper.DateTimeParser;
import passenger.service.PassengerServiceImpl;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/api/trains")
@RequiredArgsConstructor
public class PassengerController {

    private final PassengerServiceImpl passengerService;

    @Operation(summary = "Seferleri Getir", description = "TCDD API'den tren seferlerini getirir.")
    @PostMapping(value = "/search", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<SeferResponseDto> getTrains(@RequestBody SeferRequestDto seferRequestDto) {
        return passengerService.getTrains(seferRequestDto);
    }

    @Operation(summary = "Uygun Tren Seferlerini Getir", description = "TCDD API'den uygun tren seferlerini getirir.")
    @PostMapping(value = "/getTrain", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<Sefer>> getTrain(
            @RequestParam String gidisTarih,
            @RequestParam String gidisTarihSon,
            @RequestParam String binisIstasyonu,
            @RequestParam String inisIstasyonu,
            @RequestParam String koltukTipi,
            @RequestParam(defaultValue = "false") boolean sendEmail) {

        LocalDateTime start = DateTimeParser.parse(gidisTarih);
        LocalDateTime end = DateTimeParser.parse(gidisTarihSon);

        if (binisIstasyonu == null || binisIstasyonu.isEmpty() ||
                inisIstasyonu == null || inisIstasyonu.isEmpty()) {
            return ResponseEntity.badRequest().body(null);
        }

        return passengerService.getTrain(start, end, binisIstasyonu, inisIstasyonu, koltukTipi, sendEmail);
    }

    @Operation(summary = "Uygun Tren Seferlerini Getir (Sayfalı)", description = "TCDD API'den uygun tren seferlerini sayfa destekli olarak getirir.")
    @PostMapping(value = "/getTrainWithPaging", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Page<Sefer>> getTrainWithPaging(
            @RequestParam String gidisTarih,
            @RequestParam String gidisTarihSon,
            @RequestParam String binisIstasyonu,
            @RequestParam String inisIstasyonu,
            @RequestParam String koltukTipi,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size
    ) {
        LocalDateTime start = DateTimeParser.parse(gidisTarih);
        LocalDateTime end = DateTimeParser.parse(gidisTarihSon);

        if (binisIstasyonu == null || binisIstasyonu.isEmpty() ||
                inisIstasyonu == null || inisIstasyonu.isEmpty()) {
            return ResponseEntity.badRequest().body(Page.empty());
        }

        return passengerService.getTrainWithPaging(start, end, binisIstasyonu, inisIstasyonu, koltukTipi, page, size);
    }


}

package passenger.controller;


import ch.qos.logback.classic.Logger;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import passenger.dto.SeferRequestDto;
import passenger.dto.seferResponse.Sefer;
import passenger.dto.seferResponse.SeferResponseDto;
import passenger.service.PassengerService;

import java.time.LocalDateTime;
import java.time.OffsetDateTime;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

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

    @Operation(summary = "Uygun Tren Seferlerini Getir", description = "TCDD API'den uygun tren seferlerini getirir.")
    @PostMapping(value = "/getTrain", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<Sefer>> getTrain(
            @RequestParam String gidisTarih,
            @RequestParam String gidisTarihSon,
            @RequestParam String binisIstasyonu,
            @RequestParam String inisIstasyonu,
            @RequestParam String koltukTipi) {
        try {
            LocalDateTime gidisTarihConverted = convertToLocalDateTime(gidisTarih);
            LocalDateTime gidisTarihSonConverted = convertToLocalDateTime(gidisTarihSon);

            if (binisIstasyonu == null || binisIstasyonu.isEmpty() || inisIstasyonu == null || inisIstasyonu.isEmpty()) {
                return ResponseEntity.badRequest().body(null);  // Bad request if station names are empty
            }

            return passengerService.getTrain(gidisTarihConverted, gidisTarihSonConverted, binisIstasyonu, inisIstasyonu,koltukTipi);
        } catch (Exception e) {
            return ResponseEntity.internalServerError().build();
        }
    }

    private LocalDateTime convertToLocalDateTime(String dateTime) {
        try {
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss");
            return LocalDateTime.parse(dateTime, formatter);
        } catch (Exception e) {
            throw new RuntimeException("Tarih formatı hatalı: " + dateTime, e);
        }
    }

}

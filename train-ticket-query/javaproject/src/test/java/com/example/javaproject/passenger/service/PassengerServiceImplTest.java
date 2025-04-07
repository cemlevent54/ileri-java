package com.example.javaproject.passenger.service;


import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestClient;
import passenger.dto.SeferRequestDto;
import passenger.dto.seferResponse.*;
import passenger.helper.TimeConverter;
import passenger.service.PassengerServiceImpl;
import passenger.service.interfaces.CreateSeferRequestService;
import passenger.service.interfaces.EmailService;
import passenger.service.interfaces.InformationService;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Answers.RETURNS_DEEP_STUBS;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

public class PassengerServiceImplTest {

    private PassengerServiceImpl passengerService;
    private InformationService informationService;
    private CreateSeferRequestService createSeferRequestService;
    private EmailService emailService;
    private ObjectMapper objectMapper;
    private TimeConverter timeConverter;

    @BeforeEach
    public void setup() {
        informationService = mock(InformationService.class);
        createSeferRequestService = mock(CreateSeferRequestService.class);
        emailService = mock(EmailService.class);
        objectMapper = new ObjectMapper();
        timeConverter = new TimeConverter();

        passengerService = new PassengerServiceImpl(
                null,
                objectMapper,
                informationService,
                createSeferRequestService,
                timeConverter,
                emailService
        );
    }

    @Test
    public void testGetTrain_withFiveValidTrainsAndEmailSent() {
        // 📅 Test girdileri
        LocalDateTime gidisTarih = LocalDateTime.of(2025, 4, 20, 0, 0);
        LocalDateTime gidisTarihSon = LocalDateTime.of(2025, 4, 20, 23, 59);
        String binis = "ARİFİYE , SAKARYA";
        String inis = "ESKİŞEHİR , ESKİŞEHİR";
        String koltukTipi = "EKONOMİ";

        // 🧪 İstasyon ID mock
        when(informationService.getStationIdByStationName(binis)).thenReturn(ResponseEntity.ok("5"));
        when(informationService.getStationIdByStationName(inis)).thenReturn(ResponseEntity.ok("93"));

        // 🧾 Mock SeferRequestDto
        SeferRequestDto requestDto = new SeferRequestDto();
        when(createSeferRequestService.createSeferRequest(any(), any(), eq(binis), eq(inis), eq(5), eq(93), eq(koltukTipi)))
                .thenReturn(requestDto);

        // 📦 Koltuk tipi mock
        CabinClass cabinClass = new CabinClass();
        cabinClass.setName("EKONOMİ");

        CabinClassesDto cabinClassesDto = new CabinClassesDto();
        cabinClassesDto.setCabinClass(cabinClass);
        cabinClassesDto.setAvailabilityCount(10); // 10 boş koltuk

        AvailabilityFareInfoDto fareInfo = new AvailabilityFareInfoDto();
        fareInfo.setCabinClasses(List.of(cabinClassesDto));

        // 🚉 Segment
        TrainSegmentsDto segment = new TrainSegmentsDto();
        segment.setDepartureStationId(5);
        segment.setArrivalStationId(93);
        segment.setDepartureTime(gidisTarih.plusHours(8));

        // 🚆 5 tren oluştur
        List<TrainsDto> trains = new ArrayList<>();
        for (int i = 1; i <= 5; i++) {
            TrainsDto train = new TrainsDto();
            train.setName("train" + i);
            train.setTrainSegments(List.of(segment));
            train.setAvailableFareInfo(List.of(fareInfo));
            trains.add(train);
        }

        // DTO hiyerarşisi
        TrainAvailabilityDto availabilityDto = new TrainAvailabilityDto();
        availabilityDto.setTrains(trains);

        TrainLegDto legDto = new TrainLegDto();
        legDto.setTrainAvailabilities(List.of(availabilityDto));

        SeferResponseDto responseDto = new SeferResponseDto();
        responseDto.setTrainLegs(List.of(legDto));

        // 🕵️ getTrains() spy mock
        PassengerServiceImpl spyService = spy(passengerService);
        doReturn(ResponseEntity.ok(responseDto)).when(spyService).getTrains(requestDto);

        // 🚀 Çağır
        ResponseEntity<List<Sefer>> response = spyService.getTrain(gidisTarih, gidisTarihSon, binis, inis, koltukTipi, true);

        // ✅ Kontroller
        assertNotNull(response);
        assertTrue(response.getStatusCode().is2xxSuccessful());

        List<Sefer> seferList = response.getBody();
        assertNotNull(seferList);
        assertEquals(5, seferList.size());

        for (int i = 0; i < 5; i++) {
            Sefer sefer = seferList.get(i);
            assertEquals("train" + (i + 1), sefer.getTrenAdi());
            assertEquals(koltukTipi, sefer.getKoltukTipi());
            assertEquals(binis, sefer.getBinisIstasyonu());
            assertEquals(inis, sefer.getVarisIstasyonu());
            assertTrue(sefer.getBosYerSayisi() >= 0);
        }

        // 📧 E-posta kontrolü
        verify(emailService, times(1)).sendTrainScheduleEmail(eq(seferList), anyString(), anyString());
    }

}

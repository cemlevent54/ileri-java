package passenger.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClient;
import passenger.dto.SeferRequestDto;
import passenger.service.interfaces.InformationService;
import passenger.service.interfaces.CreateSeferRequestService;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.List;

@Service
@Slf4j
public class CreateSeferRequestServiceImpl implements CreateSeferRequestService {
    private final RestClient restClient;
    private final ObjectMapper objectMapper;
    private static final String API_URL = "https://web-api-prod-ytp.tcddtasimacilik.gov.tr/tms/train/train-availability?environment=dev&userId=1";

    @Value("${TCDD_AUTH_TOKEN}")
    private String AUTH_TOKEN;

    private final InformationService informationService;

    public CreateSeferRequestServiceImpl(@Qualifier("tcddRestClient") RestClient restClient, ObjectMapper objectMapper, InformationService informationService) {
        this.restClient = restClient;
        this.objectMapper = objectMapper;
        this.informationService = informationService;
    }

    @Override
    public SeferRequestDto createSeferRequest(LocalDateTime gidisTarih, LocalDateTime gidisTarihSon, String binisIstasyonu, String inisIstasyonu,
                                              int binisIstasyonuId, int inisIstasyonuId, String koltukTipi) {
        try {
            // ✅ 1. SeferRequestDto nesnesini oluştur
            SeferRequestDto seferRequestDto = new SeferRequestDto();
            seferRequestDto.setSearchType("DOMESTIC"); // Default olarak DOMESTIC
            seferRequestDto.setSearchReservation(false); // Normal arama

            // 📌 2. Güzergah bilgilerini oluştur
            LocalDateTime formattedDate = gidisTarih.atZone(ZoneId.systemDefault())
                    .withZoneSameInstant(ZoneId.of("UTC"))
                    .toLocalDateTime();

            SeferRequestDto.SearchRoutes searchRoute = new SeferRequestDto.SearchRoutes(
                    binisIstasyonuId, binisIstasyonu,
                    inisIstasyonuId, inisIstasyonu,
                    formattedDate
            );

            seferRequestDto.setSearchRoutes(List.of(searchRoute)); // Tek güzergah için listeye ekle

            // 📌 3. Yolcu tipi (Default: 1 yetişkin)
            SeferRequestDto.PassengerTypeCount passenger = new SeferRequestDto.PassengerTypeCount(1, 1);
            seferRequestDto.setPassengerTypeCounts(List.of(passenger));

            log.info("✅ Oluşturulan SeferRequestDto: {}", seferRequestDto);

            return seferRequestDto;


        } catch (Exception e) {
            log.error("Error while creating sefer request", e);
            return null;
        }
    }
}

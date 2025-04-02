package passenger.service;

import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpStatusCodeException;
import org.springframework.web.client.RestClient;
import passenger.dto.SeferRequestDto;
import passenger.dto.seferResponse.*;
import passenger.helper.TimeConverter;
import passenger.service.interfaces.InformationService;
import passenger.service.interfaces.PassengerService;
import passenger.service.interfaces.CreateSeferRequestService;
import passenger.service.interfaces.EmailService;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
@Slf4j
public class PassengerServiceImpl implements PassengerService {

    private final RestClient restClient;
    private final ObjectMapper objectMapper;
    private final InformationService informationService;
    private final CreateSeferRequestService createSeferRequestService;
    private final TimeConverter timeConverter;
    private final EmailService emailService;


    private static final String API_URL = "https://web-api-prod-ytp.tcddtasimacilik.gov.tr/tms/train/train-availability?environment=dev&userId=1";

    @Value("${TCDD_AUTH_TOKEN}")
    private String AUTH_TOKEN;


    public PassengerServiceImpl(RestClient restClient, ObjectMapper objectMapper, InformationService informationService,
                            CreateSeferRequestService createSeferRequestService, TimeConverter timeConverter, EmailService emailService) {
        this.restClient = restClient;
        this.objectMapper = objectMapper;
        this.informationService = informationService;
        this.createSeferRequestService = createSeferRequestService;
        this.timeConverter = timeConverter;
        this.emailService = emailService;
    }

    @Override
    public ResponseEntity<SeferResponseDto> getTrains(SeferRequestDto seferRequestDto) {
        try {
            log.info("Sefer Request: {}", seferRequestDto);
            ResponseEntity<String> rawResponse = restClient.post()
                    .uri(API_URL)
                    .header(HttpHeaders.AUTHORIZATION,AUTH_TOKEN)
                    .header("Unit-Id","3895")
                    .header(HttpHeaders.ACCEPT, MediaType.APPLICATION_JSON_VALUE)
                    .contentType(MediaType.APPLICATION_JSON)
                    .body(seferRequestDto)
                    .retrieve()
                    .toEntity(String.class);

            // log.info("API Response: {}", rawResponse.getBody());

            if (rawResponse.getBody() == null || rawResponse.getBody().isBlank()) {
                throw new RuntimeException("API'den geçerli bir JSON yanıtı alınamadı.");
            }

            SeferResponseDto responseDto = objectMapper.readValue(rawResponse.getBody(), SeferResponseDto.class);

            return ResponseEntity.ok(responseDto);

        } catch (HttpStatusCodeException e) {
            log.error("HTTP ERROR: Status Code: {} | Response: {}", e.getStatusCode(), e.getResponseBodyAsString());
            throw new RuntimeException("TCDD API Hata Kodu: " + e.getStatusCode() + " - " + e.getResponseBodyAsString());
        } catch (JsonMappingException e) {
            log.error("JSON Parsing Error: {}", e.getMessage());
            throw new RuntimeException("JSON dönüşüm hatası: API yanıtı beklenen formata uymuyor.");
        } catch (Exception e) {
            log.error("General Error: {}", e.getMessage(), e);
            throw new RuntimeException("TCDD API isteği başarısız: " + e.getMessage());
        }
    }

    @Override
    public ResponseEntity<List<Sefer>> getTrain(LocalDateTime gidisTarih, LocalDateTime gidisTarihSon,
                                                String binisIstasyonu, String inisIstasyonu, String koltukTipi) {
        try {
            // 1️⃣ İstasyon ID'lerini al
            ResponseEntity<String> binisResponse = informationService.getStationIdByStationName(binisIstasyonu);
            ResponseEntity<String> inisResponse = informationService.getStationIdByStationName(inisIstasyonu);

            if (!binisResponse.getStatusCode().is2xxSuccessful() || binisResponse.getBody() == null) {
                throw new RuntimeException("❌ Biniş istasyonu bulunamadı: " + binisIstasyonu);
            }
            if (!inisResponse.getStatusCode().is2xxSuccessful() || inisResponse.getBody() == null) {
                throw new RuntimeException("❌ İniş istasyonu bulunamadı: " + inisIstasyonu);
            }

            // 2️⃣ İstasyon ID'lerini integer'a çevir
            int binisIstasyonuId = Integer.parseInt(binisResponse.getBody());
            int inisIstasyonuId = Integer.parseInt(inisResponse.getBody());

            log.info("📍 Biniş İstasyonu ID: {}, İniş İstasyonu ID: {}", binisIstasyonuId, inisIstasyonuId);

            // 3️⃣ Sefer talebini oluştur
            SeferRequestDto seferRequestDto = createSeferRequestService.createSeferRequest(
                    gidisTarih, gidisTarihSon,
                    binisIstasyonu, inisIstasyonu,
                    binisIstasyonuId, inisIstasyonuId, koltukTipi
            );

            log.info("📨 API'ye gönderilen SeferRequestDto: {}", seferRequestDto);

            // 4️⃣ Tren bilgilerini al
            ResponseEntity<SeferResponseDto> seferResponseDto = getTrains(seferRequestDto);
            SeferResponseDto responseDto = seferResponseDto.getBody();

            if (responseDto == null || responseDto.getTrainLegs() == null || responseDto.getTrainLegs().isEmpty()) {
                log.warn("⚠️ API'den geçerli bir tren bilgisi alınamadı.");
                return ResponseEntity.ok(new ArrayList<>());  // Boş liste döndür
            }

            log.info("📥 API'den gelen tren bilgisi alındı. Tren sayısı: {}", responseDto.getTrainLegs().size());

            List<Sefer> seferListesi = new ArrayList<>();

            // 5️⃣ Gelen trenleri filtrele
            for (TrainLegDto trainLeg : responseDto.getTrainLegs()) {
                for (TrainAvailabilityDto trainAvailability : trainLeg.getTrainAvailabilities()) {
                    for (TrainsDto train : trainAvailability.getTrains()) {

                        LocalDateTime kalkisTarihi = null;
                        LocalDateTime varisTarihi = null;
                        boolean isTrainValid = false;

                        log.info("🚆 İşlenen tren: {}", train.getName());

                        // 🔍 Tren segmentlerini kontrol et
                        for (TrainSegmentsDto segment : train.getTrainSegments()) {
                            log.info("📌 Segment: Departure ID: {}, Arrival ID: {} | Kalkış Zamanı: {}",
                                    segment.getDepartureStationId(), segment.getArrivalStationId(), segment.getDepartureTime());

                            if (segment.getDepartureStationId() == binisIstasyonuId) {
                                kalkisTarihi = segment.getDepartureTime();
                            }
                            if (segment.getArrivalStationId() == inisIstasyonuId && kalkisTarihi != null) {
                                varisTarihi = segment.getDepartureTime();
                                isTrainValid = true;
                                break; // 🚀 Eğer biniş ve iniş istasyonları eşleşiyorsa, döngüyü kır
                            }
                        }

                        // ❌ Eğer uygun tren değilse logla ve devam et
                        if (!isTrainValid || kalkisTarihi == null || varisTarihi == null) {
                            log.info("⏭️ Tren uygun değil, biniş ve iniş istasyonları arasında doğrudan bağlantı yok: {}", train.getName());
                            continue;
                        }

                        // 📅 Tarih filtresi
                        if (kalkisTarihi.isBefore(gidisTarih) || kalkisTarihi.isAfter(gidisTarihSon)) {
                            log.info("⏭️ Tren {} uygun değil. Tarih aralığında değil: {}", train.getName(), kalkisTarihi);
                            continue;
                        }

                        // 🎟️ Koltuk bilgilerini kontrol et
                        int availableSeats = 0;
                        for (AvailabilityFareInfoDto availableFare : train.getAvailableFareInfo()) {
                            for (CabinClassesDto cabinClass : availableFare.getCabinClasses()) {
                                if (cabinClass.getCabinClass().getName().equalsIgnoreCase(koltukTipi)) {
                                    availableSeats = cabinClass.getAvailabilityCount();
                                }
                            }
                        }

                        // ✅ Eğer istenilen koltuk tipinde boş yer varsa listeye ekleyelim
                        if (availableSeats > 0) {
                            log.info("✅ Tren {} uygun, {} sınıfında {} boş koltuk var.", train.getName(), koltukTipi, availableSeats);

                            Sefer sefer = new Sefer(
                                    train.getName(),
                                    kalkisTarihi,
                                    binisIstasyonu,
                                    inisIstasyonu,
                                    koltukTipi,
                                    availableSeats
                            );

                            seferListesi.add(sefer);
                        }
                    }
                }
            }

            // Eğer uygun tren bulunamazsa log ekleyelim
            if (seferListesi.isEmpty()) {
                log.warn("⚠️ Uygun tren bulunamadı: {} -> {}", binisIstasyonuId, inisIstasyonuId);
            } else {
                log.info("✅ {} adet uygun tren bulundu.", seferListesi.size());
                emailService.sendTrainScheduleEmail(seferListesi, "cemlevent54@gmail.com", "cemlevent54@gmail.com");

            }

            return ResponseEntity.ok(seferListesi);

        } catch (Exception e) {
            log.error("❌ TCDD API isteği başarısız: {}", e.getMessage());
            throw new RuntimeException("TCDD API isteği başarısız: " + e.getMessage());
        }
    }

}

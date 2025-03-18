package passenger.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClient;
import passenger.dto.SeferRequestDto;
import passenger.dto.seferResponse.SeferResponseDto;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.List;

@Service
@Slf4j
public class CreateSeferRequestService {
    private final RestClient restClient;
    private final ObjectMapper objectMapper;
    private static final String API_URL = "https://web-api-prod-ytp.tcddtasimacilik.gov.tr/tms/train/train-availability?environment=dev&userId=1";
    private static final String AUTH_TOKEN = "Bearer eyJhbGciOiJSUzI1NiIsInR5cCIgOiAiSldUIiwia2lkIiA6ICJlVFFicDhDMmpiakp1cnUzQVk2a0ZnV196U29MQXZIMmJ5bTJ2OUg5THhRIn0.eyJleHAiOjE3MjEzODQ0NzAsImlhdCI6MTcyMTM4NDQxMCwianRpIjoiYWFlNjVkNzgtNmRkZS00ZGY4LWEwZWYtYjRkNzZiYjZlODNjIiwiaXNzIjoiaHR0cDovL3l0cC1wcm9kLW1hc3RlcjEudGNkZHRhc2ltYWNpbGlrLmdvdi50cjo4MDgwL3JlYWxtcy9tYXN0ZXIiLCJhdWQiOiJhY2NvdW50Iiwic3ViIjoiMDAzNDI3MmMtNTc2Yi00OTBlLWJhOTgtNTFkMzc1NWNhYjA3IiwidHlwIjoiQmVhcmVyIiwiYXpwIjoidG1zIiwic2Vzc2lvbl9zdGF0ZSI6IjAwYzM4NTJiLTg1YjEtNDMxNS04OGIwLWQ0MWMxMTcyYzA0MSIsImFjciI6IjEiLCJyZWFsbV9hY2Nlc3MiOnsicm9sZXMiOlsiZGVmYXVsdC1yb2xlcy1tYXN0ZXIiLCJvZmZsaW5lX2FjY2VzcyIsInVtYV9hdXRob3JpemF0aW9uIl19LCJyZXNvdXJjZV9hY2Nlc3MiOnsiYWNjb3VudCI6eyJyb2xlcyI6WyJtYW5hZ2UtYWNjb3VudCIsIm1hbmFnZS1hY2NvdW50LWxpbmtzIiwidmlldy1wcm9maWxlIl19fSwic2NvcGUiOiJvcGVuaWQgZW1haWwgcHJvZmlsZSIsInNpZCI6IjAwYzM4NTJiLTg1YjEtNDMxNS04OGIwLWQ0MWMxMTcyYzA0MSIsImVtYWlsX3ZlcmlmaWVkIjpmYWxzZSwicHJlZmVycmVkX3VzZXJuYW1lIjoid2ViIiwiZ2l2ZW5fbmFtZSI6IiIsImZhbWlseV9uYW1lIjoiIn0.AIW_4Qws2wfwxyVg8dgHRT9jB3qNavob2C4mEQIQGl3urzW2jALPx-e51ZwHUb-TXB-X2RPHakonxKnWG6tDIP5aKhiidzXDcr6pDDoYU5DnQhMg1kywyOaMXsjLFjuYN5PAyGUMh6YSOVsg1PzNh-5GrJF44pS47JnB9zk03Pr08napjsZPoRB-5N4GQ49cnx7ePC82Y7YIc-gTew2baqKQPz9_v381Gbm2V38PZDH9KldlcWut7kqQYJFMJ7dkM_entPJn9lFk7R5h5j_06OlQEpWRMQTn9SQ1AYxxmZxBu5XYMKDkn4rzIIVCkdTPJNCt5PvjENjClKFeUA1DOg";

    private final InformationService informationService;

    public CreateSeferRequestService(RestClient restClient, ObjectMapper objectMapper, InformationService informationService) {
        this.restClient = restClient;
        this.objectMapper = objectMapper;
        this.informationService = informationService;
    }

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

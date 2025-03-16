package passenger.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;

@Data
@Schema(description = "TCDD Sefer Arama Request DTO")
public class SeferRequestDto {
    @Schema(description = "Arama tipi (DOMESTIC veya INTERNATIONAL)", example = "DOMESTIC")
    private String searchType = "DOMESTIC";

    @Schema(description = "Rezervasyon için mi arama yapılıyor?", example = "false")
    private boolean searchReservation = false;

    @Schema(description = "Yolcu türleri ve sayıları")
    List<PassengerTypeCount> passengerTypeCounts;

    @Schema(description = "Aranacak güzergah bilgileri")
    List<SearchRoutes> searchRoutes;

    public static record PassengerTypeCount(int id, int count) {}

    public static record SearchRoutes(
            int departureStationId,
            String departureStationName,
            int arrivalStationId,
            String arrivalStationName,

            @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd-MM-yyyy HH:mm:ss")
            @Schema(description = "Kalkış tarihi ve saati", example = "11-03-2025 21:00:00")
            LocalDateTime departureDate) { // 🛠️ LocalDateTime kullanıldı
    }
}

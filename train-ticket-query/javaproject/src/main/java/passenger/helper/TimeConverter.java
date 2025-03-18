package passenger.helper;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.*;
import java.time.format.DateTimeFormatter;

@Slf4j
@Service
public class TimeConverter {

    /**
     * String tarih değerini LocalDateTime formatına çevirir.
     *
     * Desteklenen formatlar:
     * 1. "dd-MM-yyyy HH:mm:ss" -> 18-03-2025 12:00:00
     * 2. "ISO 8601 formatı" -> +57182-05-25T21:20:00Z
     */
    public static LocalDateTime convertToLocalDateTime(String dateStr) {
        try {
            if (dateStr != null && dateStr.contains("T")) {
                // Eğer ISO 8601 formatında tarih geldiyse
                ZonedDateTime zonedDateTime = ZonedDateTime.parse(dateStr);

                // Eğer yıl değeri makul bir tarih aralığında değilse, şimdiki yıl ile değiştir
                if (zonedDateTime.getYear() > 3000) {
                    zonedDateTime = zonedDateTime.withYear(LocalDate.now().getYear());
                }

                return zonedDateTime.toLocalDateTime(); // UTC offset'i kaldır ve LocalDateTime döndür
            } else {
                // Geleneksel tarih formatı
                DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-uuuu HH:mm:ss");
                return LocalDateTime.parse(dateStr, formatter);
            }
        } catch (Exception e) {
            log.error("❌ Hata! Tarih dönüştürülemedi: {}, Hata: {}", dateStr, e.getMessage());
            return null;
        }
    }

    /**
     * LocalDateTime değerini "yyyy-MM-dd'T'HH:mm" formatına çevirir.
     */
    public static String formatLocalDateTime(LocalDateTime dateTime) {
        if (dateTime == null) return "Geçersiz Tarih";
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm");
        return dateTime.format(formatter);
    }
}

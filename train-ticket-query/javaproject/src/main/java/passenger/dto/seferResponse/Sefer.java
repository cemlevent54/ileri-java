package passenger.dto.seferResponse;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
public class Sefer {
    @JsonProperty("trenAdi")
    private String trenAdi;          // Tren Adı
    @JsonProperty("kalkisTarihi")
    private LocalDateTime kalkisTarihi; // Kalkış Tarihi ve Saati
    @JsonProperty("binisIstasyonu")
    private String binisIstasyonu;   // Biniş İstasyonu Adı
    @JsonProperty("varisIstasyonu")
    private String varisIstasyonu;   // Varış İstasyonu Adı
    @JsonProperty("koltukTipi")
    private String koltukTipi;       // Koltuk Tipi (Ekonomi, Business vb.)
    @JsonProperty("bosYerSayisi")
    private int bosYerSayisi;        // Boş Koltuk Sayısı

}

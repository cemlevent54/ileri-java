package passenger.dto.seferResponse;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import org.springframework.cglib.core.Local;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
public class BasePriceDto {
    @JsonProperty("id")
    private int id;
    @JsonProperty("additionalServiceId")
    private int additionalServiceId;
    @JsonProperty("type")
    private String type;
    @JsonProperty("priceAmount")
    private int priceAmount;
    @JsonProperty("priceCurrency")
    private String priceCurrency;
    @JsonProperty("startDate")
    private LocalDate startDate;
    @JsonProperty("endDate")
    private LocalDate endDate;
}

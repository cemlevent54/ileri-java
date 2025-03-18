package passenger.dto.seferResponse;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class MinPriceDto {
    @JsonProperty("type")
    private String type;
    
    @JsonProperty("priceAmount")
    private int priceAmount;
    
    @JsonProperty("priceCurrency")
    private String priceCurrency;
}

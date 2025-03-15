package passenger.dto.seferResponse;

import com.fasterxml.jackson.annotation.JsonProperty;

public class PriceDto {
    @JsonProperty("type")
    private String type;
    @JsonProperty("priceAmount")
    private int priceAmount;
    @JsonProperty("priceCurrency")
    private String priceCurrency;
}

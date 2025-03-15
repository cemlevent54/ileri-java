package passenger.dto.seferResponse;

import com.fasterxml.jackson.annotation.JsonProperty;

public class FareBasisDto {
    @JsonProperty("code")
    private String code;
    @JsonProperty("factor")
    private int factor;
    @JsonProperty("price")
    private PriceDto price;


}

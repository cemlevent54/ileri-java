package passenger.dto.seferResponse;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

public class AdditionalServices {
    @JsonProperty("id")
    private int id;
    @JsonProperty("additionalServiceTypeId")
    private int additionalServiceTypeId;
    @JsonProperty("name")
    private String name;
    @JsonProperty("description")
    private String description;
    @JsonProperty("code")
    private String code;
    @JsonProperty("active")
    private boolean active;
    @JsonProperty("freeForPermi")
    private boolean freeForPermi;
    @JsonProperty("actAsGroup")
    private boolean actAsGroup;
    @JsonProperty("basePrice")
    private List<BasePriceDto> basePrice;
    @JsonProperty("pricingPeriods")
    private String pricingPeriods;
}

package passenger.dto.seferResponse;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.util.List;

@Data
public class AvailabilitiesDto {

    @JsonProperty("trainCarId")
    private int trainCarId;
    @JsonProperty("trainCarName")
    private int trainCarName;
    @JsonProperty("cabinClass")
    private CabinClass cabinClass;
    @JsonProperty("availability")
    private int availability;
    @JsonProperty("pricingList")
    private List<PricingListDto> pricingList;
    @JsonProperty("additionalServices")
    private List<AdditionalServices> additionalServices;



}

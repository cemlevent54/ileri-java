package passenger.dto.seferResponse;

import com.fasterxml.jackson.annotation.JsonProperty;

public class CabinClassAvailabilitiesDto {
    @JsonProperty("cabinClass")
    private CabinClass cabinClass;
    @JsonProperty("availabilityCount")
    private int availabilityCount;
}

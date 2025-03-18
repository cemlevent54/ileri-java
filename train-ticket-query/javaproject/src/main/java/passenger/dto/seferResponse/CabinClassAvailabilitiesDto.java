package passenger.dto.seferResponse;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class CabinClassAvailabilitiesDto {
    @JsonProperty("cabinClass")
    private CabinClass cabinClass;
    @JsonProperty("availabilityCount")
    private int availabilityCount;
}

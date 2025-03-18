package passenger.dto.seferResponse;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.util.List;

@Data
public class AvailabilityFareInfoDto {
    @JsonProperty("fareFamily")
    private FareFamilyDto fareFamily;
    @JsonProperty("cabinClasses")
    private List<CabinClassesDto> cabinClasses;
}

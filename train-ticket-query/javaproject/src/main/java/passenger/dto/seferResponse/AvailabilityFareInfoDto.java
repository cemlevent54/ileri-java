package passenger.dto.seferResponse;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

public class AvailabilityFareInfoDto {
    @JsonProperty("fareFamily")
    private FareFamilyDto fareFamily;
    @JsonProperty("cabinClasses")
    private List<CabinClassesDto> cabinClasses;
}

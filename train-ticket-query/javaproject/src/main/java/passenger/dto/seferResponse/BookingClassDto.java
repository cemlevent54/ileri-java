package passenger.dto.seferResponse;

import com.fasterxml.jackson.annotation.JsonProperty;

public class BookingClassDto {
    @JsonProperty("id")
    private int id;
    @JsonProperty("code")
    private String code;
    @JsonProperty("name")
    private String name;
    @JsonProperty("cabinClass")
    private CabinClass cabinClass;
    @JsonProperty("fareFamily")
    private FareFamilyDto fareFamily;
}

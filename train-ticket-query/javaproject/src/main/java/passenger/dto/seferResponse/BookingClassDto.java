package passenger.dto.seferResponse;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
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

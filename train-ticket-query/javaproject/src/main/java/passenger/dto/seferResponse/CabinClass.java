package passenger.dto.seferResponse;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.util.List;

@Data
public class CabinClass {
    @JsonProperty("id")
    private int id;
    @JsonProperty("code")
    private String code;
    @JsonProperty("name")
    private String name;
//    @JsonProperty("additionalServices")
//    private List<AdditionalServices> additionalServices;
//    @JsonProperty("bookingClassModels")
//    private String bookingClassModels;
//    @JsonProperty("showAvailabilityOnQuery")
//    private boolean showAvailabilityOnQuery;

}

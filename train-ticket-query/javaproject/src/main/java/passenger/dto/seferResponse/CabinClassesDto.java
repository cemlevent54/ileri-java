package passenger.dto.seferResponse;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.util.List;

@Data
public class CabinClassesDto {
    @JsonProperty("cabinClass")
    private CabinClass cabinClass;
    @JsonProperty("availabilityCount")
    private int availabilityCount;
    @JsonProperty("minPrice")
    private int minPrice;
    @JsonProperty("minPriceCurrency")
    private String minPriceCurrency;
    @JsonProperty("bookingClassAvailabilities")
    private List<BookingClassAvailabilitiesDto>  bookingClassAvailabilities;

}

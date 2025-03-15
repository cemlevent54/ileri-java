package passenger.dto.seferResponse;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

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

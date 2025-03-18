package passenger.dto.seferResponse;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class PricingListDto {
    @JsonProperty("basePricingId")
    private int basePricingId;
    @JsonProperty("bookingClass")
    private BookingClassDto bookingClass;
    @JsonProperty("cabinClassId")
    private int cabinClassId;
    @JsonProperty("basePricingType")
    private String basePricingType;
    @JsonProperty("fareBasis")
    private FareBasisDto fareBasis;
    @JsonProperty("basePrice")
    private PriceDto basePrice;
    @JsonProperty("crudePrice")
    private PriceDto crudePrice;
    @JsonProperty("baseTransportationCost")
    private PriceDto baseTransportationCost;
    @JsonProperty("availability")
    private int availability;
}

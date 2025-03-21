package passenger.dto.seferResponse;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class BookingClassAvailabilitiesDto {
    @JsonProperty("bookingClass")
    private BookingClassDto bookingClass;
    @JsonProperty("price")
    private int price;
    @JsonProperty("currency")
    private String currency;
    @JsonProperty("availability")
    private int availability;
}

package passenger.dto.seferResponse;

import com.fasterxml.jackson.annotation.JsonProperty;
import jdk.jfr.DataAmount;
import lombok.Data;

@Data
public class BookingClassCapacities {
    @JsonProperty("id")
    private int id;
    @JsonProperty("trainId")
    private int trainId;
    @JsonProperty("bookingClassId")
    private int bookingClassId;
    @JsonProperty("capacity")
    private int capacity;
}

package passenger.dto.seferResponse;

import com.fasterxml.jackson.annotation.JsonProperty;

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

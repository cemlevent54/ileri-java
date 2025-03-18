package passenger.dto.seferResponse;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.util.List;

@Data
public class TrainAvailabilityDto {

    @JsonProperty("trains")
    private List<TrainsDto> trains;

    @JsonProperty("totalTripTime")
    private int TotalTripTime;

    @JsonProperty("minPrice")
    private int minPrice;

    @JsonProperty("connection")
    private boolean connection;

    @JsonProperty("dayChanged")
    private boolean dayChanged;
}

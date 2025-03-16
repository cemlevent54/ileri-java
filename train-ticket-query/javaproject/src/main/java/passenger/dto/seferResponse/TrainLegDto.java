package passenger.dto.seferResponse;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

public class TrainLegDto {

    @JsonProperty("trainAvailabilities")
    private List<TrainAvailabilityDto> trainAvailabilities;

    @JsonProperty("resultCount")
    private int resultCount;
}

package passenger.dto.seferResponse;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.util.List;

@Data
public class TrainLegDto {

    @JsonProperty("trainAvailabilities")
    private List<TrainAvailabilityDto> trainAvailabilities;

    @JsonProperty("resultCount")
    private int resultCount;
}

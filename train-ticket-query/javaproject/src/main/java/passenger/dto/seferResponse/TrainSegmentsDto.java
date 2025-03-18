package passenger.dto.seferResponse;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class TrainSegmentsDto {
    @JsonProperty("departureStationId")
    private int departureStationId;
    @JsonProperty("arrivalStationId")
    private int arrivalStationId;
    @JsonProperty("departureTime")
    private LocalDateTime departureTime;
    @JsonProperty("arrivalTime")
    private LocalDateTime arrivalTime;
}

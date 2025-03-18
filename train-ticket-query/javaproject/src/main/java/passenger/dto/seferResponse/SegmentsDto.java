package passenger.dto.seferResponse;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.time.Instant;
import java.time.LocalDateTime;

@Data
public class SegmentsDto {
    @JsonProperty("id")
    private int id;
    @JsonProperty("departureTime")
//    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss.SSS")
    private Instant departureTime;
    @JsonProperty("arrivalTime")
//    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss.SSS")
    private Instant arrivalTime;
    @JsonProperty("stops")
    private boolean stops;
    @JsonProperty("duration")
    private int duration;
    @JsonProperty("stopDuration")
    private int stopDuration;
    @JsonProperty("distance")
    private Long distance;
    @JsonProperty("segment")
    private SegmentDto segment;

}

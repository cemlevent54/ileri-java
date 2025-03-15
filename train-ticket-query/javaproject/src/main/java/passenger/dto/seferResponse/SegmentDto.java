package passenger.dto.seferResponse;

import com.fasterxml.jackson.annotation.JsonProperty;

public class SegmentDto {
    @JsonProperty("id")
    private int id;
    @JsonProperty("name")
    private String name;
    @JsonProperty("departureStation")
    private StationDto departureStation;
    @JsonProperty("arrivalStation")
    private StationDto arrivalStation;
    @JsonProperty("lineId")
    private int lineId;
    @JsonProperty("lineOrder")
    private int lineOrder;

}

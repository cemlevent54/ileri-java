package passenger.dto.seferResponse;

import com.fasterxml.jackson.annotation.JsonProperty;

public class StationStatus {
    @JsonProperty("id")
    private int id;
    @JsonProperty("name")
    private String name;
    @JsonProperty("detail")
    private String detail;
}

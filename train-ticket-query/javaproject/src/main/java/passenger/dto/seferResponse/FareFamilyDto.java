package passenger.dto.seferResponse;

import com.fasterxml.jackson.annotation.JsonProperty;

public class FareFamilyDto {
    @JsonProperty("id")
    private int id;
    @JsonProperty("name")
    private String name;
}

package passenger.dto.seferResponse;

import com.fasterxml.jackson.annotation.JsonProperty;
import jdk.jfr.DataAmount;
import lombok.Data;

@Data
public class FareFamilyDto {
    @JsonProperty("id")
    private int id;
    @JsonProperty("name")
    private String name;
}

package passenger.dto.seferResponse;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonPOJOBuilder;
import lombok.Data;

import java.util.List;

@Data
public class CarsDto {
    @JsonProperty("id")
    private int id;
    @JsonProperty("name")
    private String name;
    @JsonProperty("trainId")
    private int trainId;
    @JsonProperty("templateId")
    private int templateId;
    @JsonProperty("carIndex")
    private int carIndex;
    @JsonProperty("unlabeled")
    private boolean unlabeled;
    @JsonProperty("capacity")
    private int capacity;
    @JsonProperty("cabinClassId")
    private int cabinClassId;
    @JsonProperty("availabilities")
    private List<AvailabilitiesDto> availabilities;
    @JsonProperty("trainSegments")
    private List<TrainSegmentsDto> trainSegments;
    @JsonProperty("totalDistance")
    private Long totalDistance;
    @JsonProperty("availabilityFareInfo")
    private AvailabilityFareInfoDto availabilityFareInfo;

}

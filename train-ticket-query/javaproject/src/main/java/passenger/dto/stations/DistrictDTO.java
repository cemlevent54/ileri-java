package passenger.dto.stations;

import lombok.Data;

@Data
public class DistrictDTO {
    private int id;
    private String name;
    private CityDTO city;
}

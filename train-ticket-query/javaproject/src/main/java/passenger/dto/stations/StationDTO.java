package passenger.dto.stations;

import lombok.Data;
import passenger.dto.stations.DistrictDTO;

@Data
public class StationDTO {
    private int id;
    private int unitId;
    private int areaCode;
    private String name;
    private String stationCode;
    private DistrictDTO district;
}

package passenger.dto.stations;

import lombok.Data;

@Data
public class CityDTO {
    private int id;
    private String name;
    private CountryDTO country;
}

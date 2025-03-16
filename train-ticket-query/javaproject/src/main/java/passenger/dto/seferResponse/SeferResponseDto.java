package passenger.dto.seferResponse;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.List;
import java.util.Optional;

@Data
public class SeferResponseDto {

    @JsonProperty("trainLegs")
    private List<TrainLegDto> trainLegs;

    @JsonProperty("legCount")
    private int legCount;

    @JsonProperty("roundTripDiscount")
    private int roundTripDiscount;

    @JsonProperty("maxRegionalTrainsRoundTripDays")
    private long maxRegionalTrainsRoundTripDays;
}

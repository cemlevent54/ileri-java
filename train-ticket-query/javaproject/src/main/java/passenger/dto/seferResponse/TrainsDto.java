package passenger.dto.seferResponse;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import org.springframework.cglib.core.Local;

import java.time.Instant;
import java.time.LocalDateTime;
import java.util.List;

@Data
public class TrainsDto {
    @JsonProperty("id")
    private int id;

    @JsonProperty("number")
    private int number;

    @JsonProperty("name")
    private String name;

    @JsonProperty("commercialName")
    private String type;

    @JsonProperty("line")
    private String line;

    @JsonProperty("reversed")
    private Boolean reversed;

    @JsonProperty("scheduleId")
    private int scheduleId;

    @JsonProperty("departureStationId")
    private int departureStationId;

    @JsonProperty("arrivalStationId")
    private int arrivalStationId;

    @JsonProperty("minPrice")
    private MinPriceDto minPrice;

    @JsonProperty("reservationLockTime")
    private int reservationLockTime;

    @JsonProperty("reservable")
    private boolean reservable;

    @JsonProperty("bookingClassCapacities")
    private List<BookingClassCapacities> bookingClassCapacities;

    @JsonProperty("segments")
    private List<SegmentsDto> segments;

    @JsonProperty("cars")
    private List<CarsDto> cars;

    @JsonProperty("trainSegments")
    private List<TrainSegmentsDto> trainSegments;

    @JsonProperty("totalDistance")
    private Long totalDistance;

    @JsonProperty("availableFareInfo")
    private List<AvailabilityFareInfoDto> availableFareInfo;

    @JsonProperty("cabinClassAvailabilities")
    private List<CabinClassAvailabilitiesDto> cabinClassAvailabilities;

    @JsonProperty("trainDate")
    private Instant trainDate;
    @JsonProperty("trainNumber")
    private String trainNumber;
    @JsonProperty("skipsDay")
    private boolean skipsDay;
}


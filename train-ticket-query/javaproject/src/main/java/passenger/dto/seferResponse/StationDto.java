package passenger.dto.seferResponse;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class StationDto {
    @JsonProperty("id")
    private int id;
    @JsonProperty("stationNumber")
    private String stationNumber;
    @JsonProperty("areaCode")
    private int areaCode;
    @JsonProperty("name")
    private String name;
    @JsonProperty("stationStatus")
    private StationStatus stationStatus;
    @JsonProperty("stationType")
    private StationType stationType;
    @JsonProperty("unitId")
    private int unitId;
    @JsonProperty("cityId")
    private int cityId;
    @JsonProperty("districtId")
    private int districtId;
    @JsonProperty("neighbourhoodId")
    private int neighbourhoodId;
    @JsonProperty("uicCode")
    private String uicCode;
    @JsonProperty("technicalUnit")
    private String technicalUnit;
    @JsonProperty("stationChefId")
    private int stationChefId;
    @JsonProperty("detail")
    private String detail;
    @JsonProperty("showOnQuery")
    private boolean showOnQuery;
    @JsonProperty("passengerDrop")
    private boolean passengerDrop;
    @JsonProperty("ticketSaleActive")
    private boolean ticketSaleActive;
    @JsonProperty("active")
    private boolean active;
    @JsonProperty("email")
    private String email;
    @JsonProperty("orangeDeskEmail")
    private String orangeDeskEmail;
    @JsonProperty("address")
    private String address;
    @JsonProperty("longitude")
    private Long longitude;
    @JsonProperty("latitude")
    private Long latitude;
    @JsonProperty("altitude")
    private Long altitude;
    @JsonProperty("startKm")
    private int startKm;
    @JsonProperty("endKm")
    private int endKm;
    @JsonProperty("showOnMap")
    private boolean showOnMap;
    @JsonProperty("passengerAdmission")
    private boolean passengerAdmission;
    @JsonProperty("disabledAccessibility")
    private boolean disabledAccessibility;
    @JsonProperty("phones")
    private String phones;
    @JsonProperty("workingDays")
    private String workingDays;
    @JsonProperty("hardwares")
    private String hardwares;
    @JsonProperty("physicalProperties")
    private String physicalProperties;
    @JsonProperty("stationPlatforms")
    private String stationPlatforms;
    @JsonProperty("salesChannels")
    private String salesChannels;
    @JsonProperty("IATACode")
    private String IATACode;

}

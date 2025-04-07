package passenger.service.interfaces;

import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import passenger.dto.SeferRequestDto;
import passenger.dto.seferResponse.Sefer;
import passenger.dto.seferResponse.SeferResponseDto;

import java.time.LocalDateTime;
import java.util.List;

public interface PassengerService {
    ResponseEntity<SeferResponseDto> getTrains(SeferRequestDto seferRequestDto);

    ResponseEntity<List<Sefer>> getTrain(LocalDateTime gidisTarih, LocalDateTime gidisTarihSon,
                                                String binisIstasyonu, String inisIstasyonu, String koltukTipi, boolean sendEmail);

    ResponseEntity<Page<Sefer>> getTrainWithPaging(LocalDateTime gidisTarih, LocalDateTime gidisTarihSon,
                                                          String binisIstasyonu, String inisIstasyonu,
                                                          String koltukTipi, int page, int size);

}

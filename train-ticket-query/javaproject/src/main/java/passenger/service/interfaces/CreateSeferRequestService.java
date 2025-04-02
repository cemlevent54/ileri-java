package passenger.service.interfaces;

import passenger.dto.SeferRequestDto;

import java.time.LocalDateTime;

public interface CreateSeferRequestService {
    SeferRequestDto createSeferRequest(LocalDateTime gidisTarih, LocalDateTime gidisTarihSon, String binisIstasyonu, String inisIstasyonu,
                                       int binisIstasyonuId, int inisIstasyonuId, String koltukTipi);
}

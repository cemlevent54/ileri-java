package passenger.service.interfaces;

import passenger.dto.seferResponse.Sefer;

import java.util.List;

public interface EmailService {
    void sendTrainScheduleEmail(List<Sefer> seferListesi, String from, String to);

    String formatTrainSchedules(List<Sefer> seferListesi);

    String escapeHtml(String input);
}

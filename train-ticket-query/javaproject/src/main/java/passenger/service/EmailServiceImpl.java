package passenger.service;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.mail.MailException;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import passenger.dto.seferResponse.Sefer;
import passenger.service.interfaces.EmailService;
import org.springframework.mail.javamail.JavaMailSender;

import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class EmailServiceImpl implements EmailService {

    private final JavaMailSender mailSender;

    @Override
    public void sendTrainScheduleEmail(List<Sefer> seferListesi, String from, String to) {
        try {
            // 📨 E-posta gönderim başlıyor
            log.info("📧 E-posta gönderimi başlatılıyor: {} -> {}", from, to);

            MimeMessage message = mailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(message, true, "UTF-8");

            helper.setFrom(from);
            helper.setTo(to);
            helper.setSubject("🚆 Uygun Tren Seferleri Bilgilendirmesi");

            // 📝 E-posta içeriğini oluştur
            String emailContent = formatTrainSchedules(seferListesi);
            helper.setText(emailContent, true); // HTML formatında gönder

            // 📤 E-posta gönder
            mailSender.send(message);
            log.info("✅ E-posta başarıyla gönderildi: {}", to);

        } catch (MailException e) {
            log.error("❌ Mail sunucusuna bağlanırken hata oluştu: {}", e.getMessage(), e);
            throw new RuntimeException("Mail sunucusuna bağlanırken hata oluştu.");
        } catch (MessagingException e) {
            log.error("❌ E-posta mesajı oluşturulurken hata oluştu: {}", e.getMessage(), e);
            throw new RuntimeException("E-posta mesajı oluşturulurken hata oluştu.");
        } catch (Exception e) {
            log.error("❌ Beklenmeyen hata: {}", e.getMessage(), e);
            throw new RuntimeException("E-posta gönderimi sırasında beklenmeyen bir hata oluştu.");
        }
    }

    @Override
    public String formatTrainSchedules(List<Sefer> seferListesi) {
        StringBuilder emailBody = new StringBuilder();

        emailBody.append("<h2>🚆 Uygun Tren Seferleri</h2>");
        emailBody.append("<table border='1' cellpadding='5' cellspacing='0' style='border-collapse: collapse;'>");
        emailBody.append("<tr><th>Tren Adı</th><th>Kalkış Tarihi</th><th>Biniş</th><th>Varış</th><th>Koltuk Tipi</th><th>Boş Koltuk</th></tr>");

        for (Sefer sefer : seferListesi) {
            emailBody.append("<tr>")
                    .append("<td>").append(escapeHtml(sefer.getTrenAdi())).append("</td>")
                    .append("<td>").append(escapeHtml(sefer.getKalkisTarihi().toString())).append("</td>")
                    .append("<td>").append(escapeHtml(sefer.getBinisIstasyonu())).append("</td>")
                    .append("<td>").append(escapeHtml(sefer.getVarisIstasyonu())).append("</td>")
                    .append("<td>").append(escapeHtml(sefer.getKoltukTipi())).append("</td>")
                    .append("<td>").append(sefer.getBosYerSayisi()).append("</td>")
                    .append("</tr>");
        }

        emailBody.append("</table>");
        return emailBody.toString();
    }

    @Override
    public String escapeHtml(String input) {
        if (input == null) return "";
        return input.replace("&", "&amp;")
                .replace("<", "&lt;")
                .replace(">", "&gt;")
                .replace("\"", "&quot;")
                .replace("'", "&#39;");
    }

}

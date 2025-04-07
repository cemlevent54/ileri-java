package com.example.javaproject.passenger.service;


import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.mail.javamail.JavaMailSender;
import passenger.dto.seferResponse.Sefer;
import passenger.service.EmailServiceImpl;
import passenger.service.interfaces.EmailService;

import java.time.LocalDateTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class EmailServiceImplTest {

    @Mock
    private JavaMailSender mailSender;

    @InjectMocks
    private EmailServiceImpl emailService;

    @BeforeEach
    public void setUp() {
        // MockitoAnnotations açılır
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testEscapeHtml_shouldEscapeSpecialCharacters() {
        // test escapeHtml metodunu
        String input = "<div>Test & \"email\"</div>";
        String expected = "&lt;div&gt;Test &amp; &quot;email&quot;&lt;/div&gt;";

        String result = emailService.escapeHtml(input);

        assertEquals(expected, result);
    }

    @Test
    public void testFormatTrainSchedules_shouldIncludeTrainInfo() {
        // test formatTrainSchedules metodunu
        List<Sefer> seferList = List.of(new Sefer(
                "Doğu Ekspresi",
                LocalDateTime.of(2025, 4, 20, 8, 0),
                "ARİFİYE",
                "ESKİŞEHİR",
                "EKONOMİ",
                5
        ));

        String result = emailService.formatTrainSchedules(seferList);

        // Testin başarısı
        assertTrue(result.contains("Doğu Ekspresi"));
        assertTrue(result.contains("ARİFİYE"));
        assertTrue(result.contains("ESKİŞEHİR"));
        assertTrue(result.contains("EKONOMİ"));
        assertTrue(result.contains("5"));
    }

    @Test
    public void testSendTrainScheduleEmail_shouldSendEmail() throws MessagingException {
        // Mock nesnesi
        MimeMessage mimeMessage = mock(MimeMessage.class);
        when(mailSender.createMimeMessage()).thenReturn(mimeMessage);

        // Test için sefer verisi
        List<Sefer> seferList = List.of(new Sefer(
                "Tren1",
                LocalDateTime.of(2025, 4, 20, 9, 0),
                "İSTANBUL",
                "ANKARA",
                "YATAKLI",
                0
        ));

        // Metodu çalıştır
        emailService.sendTrainScheduleEmail(seferList, "from@example.com", "to@example.com");

        // E-posta gönderimi kontrolü
        verify(mailSender, times(1)).send(mimeMessage);
    }


}

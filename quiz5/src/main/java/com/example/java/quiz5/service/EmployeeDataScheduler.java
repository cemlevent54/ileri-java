package com.example.java.quiz5.service;

import com.example.java.quiz5.dto.EmployeeResponseDto;
import com.example.java.quiz5.entity.Employee;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.retry.annotation.Backoff;
import org.springframework.retry.annotation.Retryable;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClient;
import org.springframework.web.client.RestClientException;

import java.io.IOException;

@Service
@RequiredArgsConstructor
public class EmployeeDataScheduler {

    private final EmployeeServiceImpl employeeService;
    private final RestClient restClient;
    private final ObjectMapper objectMapper;

    private final String baseurl = "https://dummy.restapiexample.com/api/v1/employees";  // API'nin tam URL'si

    @Scheduled(fixedRate = 60000)
    @Retryable(value = {RestClientException.class}, maxAttempts = 3, backoff = @Backoff(delay = 5000))
    public void fetchAndSaveEmployeeData() {
        try {
            // GET isteği ile veriyi çekiyoruz
            String rawResponse = restClient.get()
                    .uri(baseurl)  // API'nin URL'si
                    .retrieve()
                    .body(String.class)
                    ;

            if (rawResponse == null || rawResponse.isBlank()) {
                throw new RuntimeException("API'den geçerli bir JSON yanıtı alınamadı.");
            }

            // JSON yanıtını EmployeeResponseDto'ya deserialize ediyoruz
            EmployeeResponseDto employeeResponseDto = objectMapper.readValue(rawResponse, EmployeeResponseDto.class);

            if (employeeResponseDto != null && employeeResponseDto.getData() != null) {
                // Employee nesnelerini veritabanına kaydediyoruz
                for (Employee employee : employeeResponseDto.getData()) {
                    employeeService.saveEmployee(employee);
                }
            }

        } catch (RestClientException | IOException e) {
            throw new RuntimeException("API isteği sırasında bir hata oluştu: " + e.getMessage(), e);
        }
    }
}

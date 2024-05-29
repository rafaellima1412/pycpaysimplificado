package com.example.pycpaysimplificado.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.example.pycpaysimplificado.domain.User;
import com.example.pycpaysimplificado.dtos.NotificationDTO;

@Service
public class NotificationService {
  @Autowired
  private RestTemplate restTemplate;

  public void sendNotiication(User user, String message) throws Exception {
    String email = user.getEmail();
    NotificationDTO notificationRequest = new NotificationDTO(email, message);

    ResponseEntity<String> notificationResponse = restTemplate.postForEntity("https://util.devi.tools/api/v2/authorize",
        notificationRequest, String.class);

    if (!(notificationResponse.getStatusCode() == HttpStatus.OK)) {
      throw new Exception("serviço fora do ar!");
    }
  }
}

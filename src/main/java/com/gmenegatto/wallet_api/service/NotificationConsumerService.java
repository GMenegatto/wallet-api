package com.gmenegatto.wallet_api.service;

import com.gmenegatto.wallet_api.domain.dto.TransactionNotificationDTO;
import com.gmenegatto.wallet_api.domain.notification.Notification;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClient;

import java.util.Objects;

@Service
public class NotificationConsumerService {

    private final RestClient restClient = RestClient.create();

    @KafkaListener(topics = "transaction-template", groupId = "wallet-api")
    public void receiveNotification(TransactionNotificationDTO transaction) {
        var response = restClient.post()
                .uri("https://util.devi.tools/api/v1/notify")
                .retrieve()
                .toEntity(Notification.class);

        if (response.getStatusCode().isError()) {
            throw new RuntimeException("Error sending notification");
        }
    }
}

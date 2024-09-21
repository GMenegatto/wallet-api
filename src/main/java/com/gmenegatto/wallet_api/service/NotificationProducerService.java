package com.gmenegatto.wallet_api.service;

import com.gmenegatto.wallet_api.domain.dto.TransactionNotificationDTO;
import com.gmenegatto.wallet_api.domain.wallet.Transaction;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
public class NotificationProducerService {

    private final KafkaTemplate<String, TransactionNotificationDTO> template;

    public NotificationProducerService(KafkaTemplate<String, TransactionNotificationDTO> template) {
        this.template = template;
    }


    public void sendNotification(Transaction transaction) {
        template.send("transaction-template", TransactionNotificationDTO.from(transaction));
    }
}

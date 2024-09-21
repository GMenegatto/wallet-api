package com.gmenegatto.wallet_api.service;

import com.gmenegatto.wallet_api.domain.wallet.Transaction;
import org.springframework.stereotype.Service;

@Service
public class NotificationService {
    private final NotificationProducerService notificationProducerService;

    public NotificationService(NotificationProducerService notificationProducerService) {
        this.notificationProducerService = notificationProducerService;
    }

    public void notify(Transaction transaction) {
        notificationProducerService.sendNotification(transaction);
    }
}

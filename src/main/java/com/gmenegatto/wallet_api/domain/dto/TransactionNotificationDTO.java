package com.gmenegatto.wallet_api.domain.dto;

import com.gmenegatto.wallet_api.domain.wallet.Transaction;

import java.math.BigDecimal;

public record TransactionNotificationDTO(Long id, BigDecimal value) {


    public static TransactionNotificationDTO from(Transaction transaction) {
        return new TransactionNotificationDTO(transaction.getId(), transaction.getValue());
    }
}

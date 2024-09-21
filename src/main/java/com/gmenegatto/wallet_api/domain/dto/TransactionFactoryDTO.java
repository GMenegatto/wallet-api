package com.gmenegatto.wallet_api.domain.dto;

import com.gmenegatto.wallet_api.domain.wallet.Wallet;

import java.math.BigDecimal;

public record TransactionFactoryDTO(Wallet payee, Wallet payer, BigDecimal value) {
}

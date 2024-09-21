package com.gmenegatto.wallet_api.domain.dto;

import java.math.BigDecimal;

public record TransactionRequestDTO(Long payee, Long payer, BigDecimal value) {
}

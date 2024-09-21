package com.gmenegatto.wallet_api.service;

import com.gmenegatto.wallet_api.domain.dto.BalanceResponseDTO;
import com.gmenegatto.wallet_api.repository.WalletRepository;
import org.springframework.stereotype.Service;

@Service
public class WalletService {

    final WalletRepository walletRepository;

    public WalletService(WalletRepository walletRepository) {
        this.walletRepository = walletRepository;
    }

    public BalanceResponseDTO getBalance(final Long userId) {
        return new BalanceResponseDTO(walletRepository.getBalance(walletRepository.findActiveWalletByUserId(userId)
                .orElseThrow(() -> new RuntimeException("Wallet nof found")).getId()));
    }
}

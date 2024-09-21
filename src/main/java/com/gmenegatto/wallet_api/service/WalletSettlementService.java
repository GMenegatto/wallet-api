package com.gmenegatto.wallet_api.service;

import com.gmenegatto.wallet_api.domain.wallet.Transaction;
import com.gmenegatto.wallet_api.domain.wallet.WalletSettlement;
import com.gmenegatto.wallet_api.repository.WalletSettlementRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class WalletSettlementService {

    final WalletSettlementRepository walletSettlementRepository;

    public WalletSettlementService(WalletSettlementRepository walletSettlementRepository) {
        this.walletSettlementRepository = walletSettlementRepository;
    }


    void saveAllAndFlush(final List<WalletSettlement> settlementList, final Transaction transaction) {

        settlementList.forEach(settlement -> {
            settlement.setTransaction(transaction);
            save(settlement);
        });
    }

    Optional<WalletSettlement> save(final WalletSettlement wallet) {
        return Optional.of(walletSettlementRepository.saveAndFlush(wallet));
    }
}

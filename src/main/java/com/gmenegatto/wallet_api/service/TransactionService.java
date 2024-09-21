package com.gmenegatto.wallet_api.service;

import com.gmenegatto.wallet_api.domain.dto.TransactionFactoryDTO;
import com.gmenegatto.wallet_api.domain.dto.TransactionRequestDTO;
import com.gmenegatto.wallet_api.domain.wallet.Transaction;
import com.gmenegatto.wallet_api.domain.wallet.Wallet;
import com.gmenegatto.wallet_api.domain.wallet.WalletSettlement;
import com.gmenegatto.wallet_api.repository.TransactionRepository;
import com.gmenegatto.wallet_api.repository.WalletRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class TransactionService {

    final WalletRepository walletRepository;
    final TransactionRepository transactionRepository;
    final WalletSettlementService walletSettlementService;

    public TransactionService(WalletRepository walletRepository, TransactionRepository transactionRepository, WalletSettlementService walletSettlementService) {
        this.walletRepository = walletRepository;
        this.transactionRepository = transactionRepository;
        this.walletSettlementService = walletSettlementService;
    }

    public Optional<Transaction> createTransaction(final TransactionRequestDTO dto) {

        final Wallet payerWallet = walletRepository.findActiveWalletByUserId(dto.payer()).orElseThrow(() -> new RuntimeException("TODO CHANGE"));
        final Wallet payeeWallet = walletRepository.findActiveWalletByUserId(dto.payee()).orElseThrow(() -> new RuntimeException("TODO CHANGE"));

        final TransactionFactoryDTO factoryDTO = new TransactionFactoryDTO(payeeWallet, payerWallet, dto.value());

        final Transaction transaction = Transaction.from(factoryDTO);

        final List<WalletSettlement> newSettlements = transaction.getSettlements().stream().toList();

        return save(transaction)
                .map(savedTransaction -> {
                    walletSettlementService.saveAllAndFlush(newSettlements, savedTransaction);
                    return savedTransaction;
                })
                .map(savedTransaction -> transactionRepository.findById(savedTransaction.getId()))
                .orElseThrow(() -> new RuntimeException("TODO"));
    }

    Optional<Transaction> save(final Transaction transaction) {
        return Optional.of(transactionRepository.saveAndFlush(transaction));
    }
}

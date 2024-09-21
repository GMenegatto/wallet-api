package com.gmenegatto.wallet_api.service;

import com.gmenegatto.wallet_api.domain.dto.TransactionFactoryDTO;
import com.gmenegatto.wallet_api.domain.dto.TransactionRequestDTO;
import com.gmenegatto.wallet_api.domain.wallet.Transaction;
import com.gmenegatto.wallet_api.domain.wallet.Wallet;
import com.gmenegatto.wallet_api.domain.wallet.WalletSettlement;
import com.gmenegatto.wallet_api.exception.InvalidTransactionException;
import com.gmenegatto.wallet_api.repository.TransactionRepository;
import com.gmenegatto.wallet_api.repository.WalletRepository;
import com.gmenegatto.wallet_api.service.authorization.AuthorizerService;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
public class TransactionService {

    final WalletRepository walletRepository;
    final TransactionRepository transactionRepository;
    final WalletSettlementService walletSettlementService;
    final AuthorizerService authorizerService;

    public TransactionService(WalletRepository walletRepository, TransactionRepository transactionRepository, WalletSettlementService walletSettlementService, AuthorizerService authorizerService) {
        this.walletRepository = walletRepository;
        this.transactionRepository = transactionRepository;
        this.walletSettlementService = walletSettlementService;
        this.authorizerService = authorizerService;
    }

    public Optional<Transaction> create(final TransactionRequestDTO dto) {

        if (Objects.isNull(dto.payee()) || Objects.isNull(dto.payer())) {
            throw new InvalidTransactionException("The transaction needs a payee and a payer: ");
        }

        final Transaction transaction = Transaction.from(createTransactionFactoryDTO(dto));

        validateCreation(transaction);

        final List<WalletSettlement> newSettlements = Optional.ofNullable(transaction.getSettlements())
                .map(walletSettlements -> walletSettlements.stream().toList()).map(list -> {
                    transaction.setSettlements(null);
                    return list;
                })
                .filter(a -> a.size() == 2)
                .orElseThrow(() -> new InvalidTransactionException("A transaction needs 2 settlements"));


        Transaction newTransaction = save(transaction)
                .map(savedTransaction -> {
                    walletSettlementService.saveAllAndFlush(newSettlements, savedTransaction);
                    return savedTransaction;
                })
                .map(savedTransaction -> transactionRepository.findById(savedTransaction.getId()))
                .orElseThrow(() -> new RuntimeException("TODO")).orElseThrow();

        authorizerService.authorize();

        return Optional.of(newTransaction);
    }

    public void validateCreation(final Transaction transaction) {

        if (Objects.isNull(transaction.getPayee()) || Objects.isNull(transaction.getPayer())) {
            throw new InvalidTransactionException("The transaction needs a payee and a payer: ".concat(transaction.toString()));
        }
        if (!isPayeeDifferentFromPayer(transaction)) {
            throw new InvalidTransactionException("Payee need to be different from payer".concat(transaction.toString()));
        }
        if (!isPayerCommonUser(transaction)) {
            throw new InvalidTransactionException("Payer can not be a seller".concat(transaction.toString()));
        }
        if (!payeeHasBalance(transaction)) {
            throw new InvalidTransactionException("Payee does not have enough balance from transaction".concat(transaction.toString()));
        }
        if (!areTheSettlementsValid(transaction)) {
            throw new RuntimeException("Transaction with wrong settlements quantity".concat(transaction.toString()));
        }

    }

    public boolean isPayerCommonUser(final Transaction transaction) {
        return walletRepository.isSeller(Optional.ofNullable(transaction.getPayer())
                .map(Wallet::getId)
                .orElseThrow(() -> new RuntimeException("")));
    }

    public boolean payeeHasBalance(final Transaction transaction) {
        BigDecimal payerBalance = walletRepository.getBalance(
                Optional.ofNullable(transaction.getPayer())
                        .map(Wallet::getId)
                        .orElseThrow(() -> new RuntimeException("Payer wallet not found"))
        );

        return payerBalance.compareTo(transaction.getValue()) >= 0;
    }

    public boolean areTheSettlementsValid(final Transaction transaction) {

        int validSettlementsAmount = 2;

        return Optional.ofNullable(transaction.getSettlements())
                .orElseThrow(() -> new InvalidTransactionException("A transaction needs Settlements")).size() == validSettlementsAmount;
    }

    public boolean isPayeeDifferentFromPayer(final Transaction transaction) {
        return Optional.ofNullable(transaction.getPayer())
                .map(payer -> !payer.equals(transaction.getPayee())).orElse(false);
    }

    public TransactionFactoryDTO createTransactionFactoryDTO(final TransactionRequestDTO dto) {

        final Wallet payerWallet = walletRepository.findActiveWalletByUserId(dto.payer()).orElseThrow(() -> new RuntimeException("TODO CHANGE"));
        final Wallet payeeWallet = walletRepository.findActiveWalletByUserId(dto.payee()).orElseThrow(() -> new RuntimeException("TODO CHANGE"));

        return new TransactionFactoryDTO(payeeWallet, payerWallet, dto.value());
    }

    Optional<Transaction> save(final Transaction transaction) {
        return Optional.of(transactionRepository.saveAndFlush(transaction));
    }
}

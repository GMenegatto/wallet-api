package com.gmenegatto.wallet_api.domain.wallet;

import com.gmenegatto.wallet_api.domain.base.BaseEntity;
import com.gmenegatto.wallet_api.domain.dto.TransactionFactoryDTO;
import com.gmenegatto.wallet_api.enumeration.SettlementTypeEnum;
import jakarta.persistence.*;

import java.math.BigDecimal;
import java.util.HashSet;

@Table(name = "wlt_wallets_settlements")
@Entity
public class WalletSettlement extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "wlt_wallet_id", nullable = false)
    private Wallet wallet;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "wlt_transaction_id", nullable = false)
    private Transaction transaction;

    @Enumerated(EnumType.STRING)
    private SettlementTypeEnum type;

    private BigDecimal value;

    public WalletSettlement() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Wallet getWallet() {
        return wallet;
    }

    public void setWallet(Wallet wallet) {
        this.wallet = wallet;
    }

    public Transaction getTransaction() {
        return transaction;
    }

    public void setTransaction(Transaction transaction) {
        this.transaction = transaction;
    }

    public SettlementTypeEnum getType() {
        return type;
    }

    public void setType(SettlementTypeEnum type) {
        this.type = type;
    }

    public BigDecimal getValue() {
        return value;
    }

    public void setValue(BigDecimal value) {
        this.value = value;
    }

    public static WalletSettlement fromDebit(final Wallet wallet, final BigDecimal value) {
        final WalletSettlement newSettlement = new WalletSettlement();

        newSettlement.setType(SettlementTypeEnum.DEBIT);
        newSettlement.setWallet(wallet);
        newSettlement.setValue(value);

        return newSettlement;
    }

    public static WalletSettlement fromCredit(final Wallet wallet, final BigDecimal value) {
        final WalletSettlement newSettlement = new WalletSettlement();

        newSettlement.setType(SettlementTypeEnum.CREDIT);
        newSettlement.setWallet(wallet);
        newSettlement.setValue(value);

        return newSettlement;
    }
}

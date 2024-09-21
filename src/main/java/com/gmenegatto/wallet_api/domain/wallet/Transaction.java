package com.gmenegatto.wallet_api.domain.wallet;

import com.gmenegatto.wallet_api.domain.base.BaseEntity;
import com.gmenegatto.wallet_api.domain.dto.TransactionFactoryDTO;
import jakarta.persistence.*;

import java.math.BigDecimal;
import java.util.HashSet;
import java.util.Set;

@Table(name = "wlt_transactions")
@Entity
public class Transaction extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "wlt_payee_wallet_id", nullable = false)
    private Wallet payee;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "wlt_payer_wallet_id", nullable = false)
    private Wallet payer;

    @Column(nullable = false)
    private BigDecimal value;

    @OneToMany(mappedBy = "transaction", fetch = FetchType.LAZY)
    private Set<WalletSettlement> settlements;

    public Transaction() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }


    public BigDecimal getValue() {
        return value;
    }

    public void setValue(BigDecimal value) {
        this.value = value;
    }

    public Wallet getPayee() {
        return payee;
    }

    public void setPayee(Wallet payee) {
        this.payee = payee;
    }

    public Wallet getPayer() {
        return payer;
    }

    public void setPayer(Wallet payer) {
        this.payer = payer;
    }

    public Set<WalletSettlement> getSettlements() {
        return settlements;
    }

    public void setSettlements(Set<WalletSettlement> settlements) {
        this.settlements = settlements;
    }

    public static Transaction from(final TransactionFactoryDTO dto) {
        final Transaction newTransaction = new Transaction();

        newTransaction.setPayee(dto.payee());
        newTransaction.setPayer(dto.payer());
        newTransaction.setValue(dto.value());

        newTransaction.setSettlements(new HashSet<>());
        newTransaction.getSettlements().add(WalletSettlement.fromDebit(dto.payer(), dto.value()));
        newTransaction.getSettlements().add(WalletSettlement.fromCredit(dto.payee(), dto.value()));

        return newTransaction;
    }

    @Override
    public String toString() {
        return "Transaction{" +
                "id=" + id +
                ", value=" + value +
                '}';
    }
}

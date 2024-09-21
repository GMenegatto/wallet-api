package com.gmenegatto.wallet_api.repository;

import com.gmenegatto.wallet_api.domain.wallet.Wallet;
import com.gmenegatto.wallet_api.domain.wallet.WalletSettlement;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface WalletSettlementRepository extends JpaRepository<WalletSettlement, Long> {


}

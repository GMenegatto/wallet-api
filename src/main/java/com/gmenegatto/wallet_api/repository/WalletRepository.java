package com.gmenegatto.wallet_api.repository;

import com.gmenegatto.wallet_api.domain.wallet.Wallet;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface WalletRepository extends JpaRepository<Wallet, Long> {

    @Query(
            nativeQuery = true,
            value = "select w.* from wlt_wallets w " +
                    "       join usr_users u on u.id = w.usr_user_id " +
                    "   where u.id = :userId " +
                    "       and w.active = true " +
                    "       and u.active = true"
    )
    Optional<Wallet> findActiveWalletByUserId(@Param("userId") Long userId);

}

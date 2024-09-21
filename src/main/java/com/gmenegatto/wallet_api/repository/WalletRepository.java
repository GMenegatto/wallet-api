package com.gmenegatto.wallet_api.repository;

import com.gmenegatto.wallet_api.domain.wallet.Wallet;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.math.BigDecimal;
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

    @Query(
            nativeQuery = true,
            value = "select coalesce ((select true from wlt_wallets w " +
                    "       join usr_users u on u.id = w.usr_user_id " +
                    "   where w.id = :walletId " +
                    "       and w.active = true " +
                    "       and u.type = 'COMMON' " +
                    "       and u.active = true), false)"
    )
    boolean isCommon(@Param("walletId") Long walletId);

    @Query(
            nativeQuery = true,
            value = "select coalesce ((select true from wlt_wallets w " + //
                    "       join usr_users u on u.id = w.usr_user_id " + //
                    "   where w.id = :walletId " + //
                    "       and w.active = true " + //
                    "       and u.type = 'SELLER' " + //
                    "       and u.active = true), false)"
    )
    boolean isSeller(@Param("walletId") Long walletId);

    @Query(
            nativeQuery = true,
            value = "select sum(case when wa.type = 'CREDIT' then value " + //
                    "                when wa.type = 'DEBIT' then - value " + //
                    "                else 0" + //
                    "           end) from wlt_wallets w " + //
                    "       join usr_users u on u.id = w.usr_user_id " + //
                    "       join wlt_wallets_settlements wa on w.id = wa.wlt_wallet_id " + //
                    "   where w.id = :walletId " + //
                    "       and w.active = true " + //
                    "       and u.active = true")
    BigDecimal getBalance(@Param("walletId") Long walletId);

}

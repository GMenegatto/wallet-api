package com.gmenegatto.wallet_api.service;

import com.gmenegatto.wallet_api.repository.UserRepository;
import com.gmenegatto.wallet_api.repository.WalletRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class WalletServiceTest {

    @Mock
    private WalletRepository walletRepository;

    @InjectMocks
    private WalletService walletService;

    @Nested
    class getBalance {

        @Test
        @DisplayName("Test error when not finding wallet")
        void shouldNotFindBalance() {

            assertThrowsExactly(ExceptionInInitializerError.class, () -> walletService.getBalance(0L));

        }
    }



}
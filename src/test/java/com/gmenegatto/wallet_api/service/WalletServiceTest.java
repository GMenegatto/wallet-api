package com.gmenegatto.wallet_api.service;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class WalletServiceTest {

    @Autowired
    private WalletService walletService;

    @Test
    void getBalance() {
        assertThrows(RuntimeException.class, () -> walletService.getBalance(0L));
   }
}
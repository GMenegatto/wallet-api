package com.gmenegatto.wallet_api.controller;

import com.gmenegatto.wallet_api.domain.dto.BalanceResponseDTO;
import com.gmenegatto.wallet_api.service.WalletService;
import jakarta.transaction.Transactional;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Transactional
@RequestMapping("/users")
public class UserController {

    private final WalletService walletService;

    public UserController(WalletService walletService) {
        this.walletService = walletService;
    }

    @GetMapping("/{userId}/balance")
    public ResponseEntity<BalanceResponseDTO> getBalance(@PathVariable Long userId) {

        return ResponseEntity.ok(walletService.getBalance(userId));
    }
}

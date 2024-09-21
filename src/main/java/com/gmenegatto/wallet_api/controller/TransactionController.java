package com.gmenegatto.wallet_api.controller;

import com.gmenegatto.wallet_api.domain.dto.TransactionRequestDTO;
import com.gmenegatto.wallet_api.domain.user.User;
import com.gmenegatto.wallet_api.service.TransactionService;
import com.gmenegatto.wallet_api.service.UserService;
import jakarta.transaction.Transactional;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Transactional
public class TransactionController {

    final TransactionService transactionService;

    public TransactionController(TransactionService transactionService) {
        this.transactionService = transactionService;
    }

    @PostMapping("/transaction")
    public ResponseEntity<User> createTransaction(@RequestBody TransactionRequestDTO dto) {

        transactionService.createTransaction(dto);

        return ResponseEntity.ok(new User());
    }
}

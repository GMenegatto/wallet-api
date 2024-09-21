package com.gmenegatto.wallet_api.controller;

import com.gmenegatto.wallet_api.domain.dto.TransactionRequestDTO;
import com.gmenegatto.wallet_api.domain.user.User;
import com.gmenegatto.wallet_api.service.TransactionService;
import jakarta.transaction.Transactional;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Transactional
@RequestMapping("/transaction")
public class TransactionController {

    final TransactionService transactionService;

    public TransactionController(TransactionService transactionService) {
        this.transactionService = transactionService;
    }

    @PostMapping("")
    public ResponseEntity<User> createTransaction(@RequestBody TransactionRequestDTO dto) {

        transactionService.create(dto);

        return ResponseEntity.ok(new User());
    }
}

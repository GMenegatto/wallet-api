package com.gmenegatto.wallet_api.controller;

import com.gmenegatto.wallet_api.domain.dto.TransactionRequestDTO;
import com.gmenegatto.wallet_api.domain.user.User;
import com.gmenegatto.wallet_api.service.TransactionService;
import com.gmenegatto.wallet_api.service.idempotency.IdempotencyService;
import com.google.common.hash.Hashing;
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
    final IdempotencyService idempotencyService;

    public TransactionController(TransactionService transactionService, IdempotencyService idempotencyService) {
        this.transactionService = transactionService;
        this.idempotencyService = idempotencyService;
    }

    @PostMapping("")
    public ResponseEntity<?> createTransaction(@RequestBody TransactionRequestDTO dto) {

        if (idempotencyService.isRequestProcessed(dto.generateIdempotencyKey())) {
            return ResponseEntity.ok("Request already processed");
        }

       transactionService.create(dto);

        idempotencyService.markRequestAsProcessed(dto.generateIdempotencyKey());

        return ResponseEntity.ok(new User());
    }
}

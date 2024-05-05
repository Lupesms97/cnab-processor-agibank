package org.processor.cnab.presentation.controller;


import lombok.RequiredArgsConstructor;
import org.processor.cnab.application.service.TransactionService;
import org.processor.cnab.domain.entity.TransactionEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RequiredArgsConstructor
@RequestMapping("/v1/transaction")
@RestController
public class TransactionController {

    private final TransactionService transactionService;

    @GetMapping("/list")
    public ResponseEntity<List<TransactionEntity>> listTransactions() {
        List<TransactionEntity> transactions = transactionService.findAll();
        return ResponseEntity.ok(transactions);
    }
}

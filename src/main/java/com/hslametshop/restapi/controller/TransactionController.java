package com.hslametshop.restapi.controller;

import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.hslametshop.restapi.helper.requests.CheckoutRequest;
import com.hslametshop.restapi.model.entities.Transaction;
import com.hslametshop.restapi.service.TransactionService;

@RestController
@RequestMapping("/api/transaction")
public class TransactionController {

    @Autowired
    private TransactionService transactionService;

    @GetMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<List<Transaction>> getAllTransactions() {
        List<Transaction> transactions = transactionService.findAllTransaction();
        if (transactions.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok().body(transactions);
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Transaction> getTransactionById(@PathVariable("id") UUID id) {
        Transaction transaction = transactionService.findTransactionById(id);
        if (transaction == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok().body(transaction);
    }

    @PostMapping("/checkout")
    @PreAuthorize("hasRole('MEMBER')")
    public ResponseEntity<Transaction> checkoutTransaction(@RequestBody CheckoutRequest checkoutRequest) {
        Transaction transaction = transactionService.createTransaction(checkoutRequest);
        if (transaction == null) {
            return ResponseEntity.badRequest().build();
        }
        return ResponseEntity.ok().body(transaction);
    }

    @PutMapping("/{id}/status")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Transaction> checkoutTransaction(@PathVariable("id") UUID id) {
        Transaction sts = transactionService.updateStatus(id);
        if (sts == null) {
            return ResponseEntity.badRequest().build();
        }
        return ResponseEntity.ok().body(sts);
    }
}

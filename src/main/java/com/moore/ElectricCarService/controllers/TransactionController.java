package com.moore.ElectricCarService.controllers;

import com.moore.ElectricCarService.dtos.TransactionInfo;
import com.moore.ElectricCarService.entities.Transaction;
import com.moore.ElectricCarService.repos.TransactionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/transaction")
@CrossOrigin(origins = "*", allowedHeaders = "*")
public class TransactionController {
    @Autowired
    TransactionRepository transactionRepository;

    @GetMapping("/{id}")
    public ResponseEntity<TransactionInfo> getTransactionById(@PathVariable int id){
        Optional<Transaction> optionalTransaction = transactionRepository.findById(id);
        System.out.println(optionalTransaction.get().getChargeList());
        return optionalTransaction
                .map(transaction -> ResponseEntity.ok().body(new TransactionInfo(transaction)))
                .orElseGet(() -> ResponseEntity.notFound().build());
    }
}

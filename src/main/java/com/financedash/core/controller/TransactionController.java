package com.financedash.core.controller;

import com.financedash.core.exception.TransactionNotFoundException;
import com.financedash.core.model.Transaction;
import com.financedash.core.service.TransactionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class TransactionController {
//    GET http://localhost:8080/transaction/<id> - gets the transaction json object
//    GET http://localhost:8080/transaction?userId=<userId> - gets all the transactions of a user
//    POST http://localhost:8080/transaction - creates a transaction (Request body =  json object)
//    DELETE http://localhost:8080/transaction/<id> - deletes the transaction

    @Autowired
    private TransactionService transactionService;

    @GetMapping("/api/v1/transaction/{id}")
    public Transaction getTransaction(@PathVariable String id){
        try{
            return transactionService.getTransactionById(id);
        }catch (TransactionNotFoundException e){
            return new Transaction("565", "123", 12);
            // {"id":"565","userId":"123","sum":12.0}
        }
    }

    @GetMapping("/api/v1/transaction")
    public List<Transaction> getAllTransactions(@RequestParam String userId){
        return transactionService.getAllTransactionByUserId(userId);
    }

    @PostMapping("/api/v1/transaction")
    public Transaction createTransaction(@RequestBody Transaction transaction){
            return transactionService.addTransaction(transaction);
    }

    @DeleteMapping("/api/v1/transaction/{id}")
    public void deleteTransaction(@PathVariable String id){
        transactionService.deleteTransactionById(id);
    }



}

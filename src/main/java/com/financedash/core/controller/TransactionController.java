package com.financedash.core.controller;

import com.financedash.core.exception.TransactionNotFoundException;
import com.financedash.core.model.Transaction;
import com.financedash.core.service.TransactionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
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
    public ResponseEntity<Transaction> getTransaction(@PathVariable String id){
        try{
            return new ResponseEntity<>(transactionService.getTransactionById(id), HttpStatus.OK);
        }catch (TransactionNotFoundException e){
            return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
            // {"id":"565","userId":"123","sum":12.0}
        }
    }

    @GetMapping("/api/v1/transaction")
    public ResponseEntity<List<Transaction>> getAllTransactions(@RequestParam String userId, @AuthenticationPrincipal Principal principal){
        return new ResponseEntity<>(transactionService.getAllTransactionByUserId(userId), HttpStatus.OK);
    }

    @PostMapping("/api/v1/transaction")
    public ResponseEntity<Transaction> createTransaction(@RequestBody Transaction transaction){
            return new ResponseEntity<>(transactionService.addTransaction(transaction), HttpStatus.OK);
    }

    @DeleteMapping("/api/v1/transaction/{id}")
    public ResponseEntity<Transaction> deleteTransaction(@PathVariable String id){
        try{
            transactionService.deleteTransactionById(id);
            return new ResponseEntity<>(null, HttpStatus.OK);
        }catch (TransactionNotFoundException e){
            return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
        }

    }



}

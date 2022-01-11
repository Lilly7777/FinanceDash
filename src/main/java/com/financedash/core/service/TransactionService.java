package com.financedash.core.service;

import com.financedash.core.model.Transaction;
import com.financedash.core.repository.TransactionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TransactionService {

    @Autowired
    private TransactionRepository transactionRepository;

    public void addTransaction(Transaction transaction){
       transactionRepository.save(transaction);
    }

    public List<Transaction> getAllTransactionByUserId(String userId){
        return transactionRepository.findByUserId(userId);
    }
}

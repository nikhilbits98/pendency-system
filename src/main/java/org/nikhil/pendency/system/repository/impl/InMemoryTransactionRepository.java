package org.nikhil.pendency.system.repository.impl;

import org.nikhil.pendency.system.exceptions.DuplicateEntityException;
import org.nikhil.pendency.system.exceptions.NotFoundException;
import org.nikhil.pendency.system.models.Transaction;
import org.nikhil.pendency.system.repository.TransactionRepository;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class InMemoryTransactionRepository implements TransactionRepository {

    private static volatile InMemoryTransactionRepository instance;
    private static Map<Integer, Transaction> transactions;

    private InMemoryTransactionRepository(){
        transactions = new HashMap<>();
    }

    public static InMemoryTransactionRepository getInstance(){
        if(instance == null){
            synchronized (InMemoryTransactionRepository.class){
                if(instance == null){
                    instance = new InMemoryTransactionRepository();
                }
            }
        }
        return instance;
    }

    @Override
    public Transaction create(Integer transactionId, List<String> allTags){
        if(transactions.containsKey(transactionId)){
            throw new DuplicateEntityException("Transaction already exists");
        }
        Transaction transaction = new Transaction(transactionId, allTags);
        transactions.put(transaction.getId(), transaction);
        return transaction;
    }

    @Override
    public Transaction get(Integer id){
        if(!transactions.containsKey(id)){
            throw new NotFoundException("Transaction not found");
        }
        return transactions.get(id);
    }

    @Override
    public void remove(Integer id){
        if(!transactions.containsKey(id)){
            throw new NotFoundException("Transaction not found");
        }
        transactions.remove(id);
    }
}

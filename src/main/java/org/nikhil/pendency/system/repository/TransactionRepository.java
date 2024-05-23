package org.nikhil.pendency.system.repository;

import org.nikhil.pendency.system.models.Transaction;

import java.util.List;

public interface TransactionRepository {

    Transaction create(Integer transactionId, List<String> allTags);
    Transaction get(Integer id);
    void remove(Integer id);
}

package org.nikhil.pendency.system.models;

import lombok.Getter;

import java.util.List;

@Getter
public class Transaction {
    private final Integer id;
    private final List<String> tags;

    public Transaction(Integer id, List<String> tags) {
        this.id = id;
        this.tags = tags;
    }
}

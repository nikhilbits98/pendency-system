package org.nikhil.pendency.system.models;

import lombok.Getter;

import java.util.HashMap;
import java.util.Map;

@Getter
public class Tag {
    private final String name;
    private final Map<String, Tag> childTags;
    private Integer count;

    public Tag(String name) {
        this.name = name;
        this.childTags = new HashMap<>();
        this.count = 0;
    }

    public void incrementCount(){
        this.count++;
    }

    public void decrementCount(){
        this.count--;
    }
}

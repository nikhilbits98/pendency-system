package org.nikhil.pendency.system.repository;

import org.nikhil.pendency.system.models.Tag;

import java.util.List;

public interface TagRepository {

    void createTags(List<String> tags);
    void deleteTransactionFromTags(List<String> tags);
    Tag getLeafTag(List<String> tags);
}

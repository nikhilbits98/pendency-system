package org.nikhil.pendency.system.repository.impl;

import org.nikhil.pendency.system.exceptions.NotFoundException;
import org.nikhil.pendency.system.models.Tag;
import org.nikhil.pendency.system.repository.TagRepository;

import java.util.List;
import java.util.Map;

public class InMemoryTagRepository implements TagRepository {

    private static volatile InMemoryTagRepository instance;
    private static Tag rootTag;

    private InMemoryTagRepository() {
        rootTag = new Tag("ROOT");
    }

    public static InMemoryTagRepository getInstance(){
        if(instance == null){
            synchronized (InMemoryTagRepository.class){
                if(instance == null){
                    instance = new InMemoryTagRepository();
                }
            }
        }
        return instance;
    }

    @Override
    public void createTags(List<String> tags) {
        Tag currentTag = rootTag;
        for(String tagName: tags){
            Map<String, Tag> childTags = currentTag.getChildTags();
            Tag tag = null;
            if(childTags.containsKey(tagName)){
                tag = childTags.get(tagName);
            }else{
                tag = new Tag(tagName);
                childTags.put(tagName, tag);
            }
            tag.incrementCount();
            currentTag = tag;
        }
    }

    @Override
    public void deleteTransactionFromTags(List<String> tags){
        // This method is sort of validating that hierarchy of tags exists.
        getLeafTag(tags);
        Tag currentTag = rootTag;
        for(String tagName: tags){
            Map<String, Tag> childTags = currentTag.getChildTags();
            if(!childTags.containsKey(tagName)){
                throw new NotFoundException("Tag with name " + tagName + " not found.");
            }
            Tag tag = childTags.get(tagName);
            tag.decrementCount();
            currentTag = tag;
        }
    }

    @Override
    public Tag getLeafTag(List<String> tags) {
        Tag currentTag = rootTag;
        for(String tagName: tags){
            Map<String, Tag> childTags = currentTag.getChildTags();
            if(!childTags.containsKey(tagName)){
                throw new NotFoundException("Tag with name " + tagName + " not found.");
            }
            currentTag = childTags.get(tagName);
        }
        return currentTag;
    }
}

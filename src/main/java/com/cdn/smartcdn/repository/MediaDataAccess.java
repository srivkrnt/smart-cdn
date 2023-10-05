package com.cdn.smartcdn.repository;

import com.cdn.smartcdn.dto.helper.SizeAwareLink;
import com.cdn.smartcdn.entity.MediaEntity;
import com.cdn.smartcdn.repository.mongo.MediaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Objects;

@Component
public class MediaDataAccess {

    @Autowired
    MediaRepository mediaRepository;

    @Autowired
    MongoTemplate mongoTemplate;

    public List<MediaEntity> list(String name){
        Query query = new Query();

        if(Objects.nonNull(name)) {
            query.addCriteria(Criteria.where("name").is(name));
        }

        return mongoTemplate.find(query, MediaEntity.class);
    }

    public MediaEntity retrieve(String name){
        return mediaRepository.findByName(name);
    }

    public void updateLinks(MediaEntity mediaEntity){
        Query query = new Query().addCriteria(Criteria.where("name").is(mediaEntity.getName()));
        Update updateDefinition = new Update().set("links", mediaEntity.getLinks());

        mongoTemplate.updateFirst(query, updateDefinition, MediaEntity.class);

    }
    public void createOrUpdate(MediaEntity mediaEntity){
        MediaEntity existingEntry = retrieve(mediaEntity.getName());
        if(Objects.nonNull(existingEntry)){
            updateLinks(mediaEntity);
        } else {
            mediaRepository.save(mediaEntity);
        }
    }
}

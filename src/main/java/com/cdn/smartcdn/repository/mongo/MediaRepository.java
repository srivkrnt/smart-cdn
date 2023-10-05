package com.cdn.smartcdn.repository.mongo;

import com.cdn.smartcdn.entity.MediaEntity;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;


@Repository
public interface MediaRepository extends MongoRepository<MediaEntity, String> {

    @Query("{name:'?0'}")
    MediaEntity findByName(String name);

}

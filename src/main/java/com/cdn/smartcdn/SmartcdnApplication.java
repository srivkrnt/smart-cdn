package com.cdn.smartcdn;

import com.cdn.smartcdn.repository.mongo.MediaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;

@SpringBootApplication
@EnableMongoRepositories
public class SmartcdnApplication {

	@Autowired
	MediaRepository mediaRepository;

	public static void main(String[] args) {
		SpringApplication.run(SmartcdnApplication.class, args);
	}

}

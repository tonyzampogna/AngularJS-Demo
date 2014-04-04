package com.tonyzampogna.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.mongodb.MongoDbFactory;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.SimpleMongoDbFactory;

import com.mongodb.MongoClient;

@Configuration
public class MongoConfiguration {

	public @Bean
	MongoDbFactory adminMongoDbFactory() throws Exception {
		return new SimpleMongoDbFactory(new MongoClient("127.0.0.1"), "mynames");
	}

	public @Bean
	MongoTemplate adminMongoTemplate() throws Exception {
		MongoTemplate mongoTemplate = new MongoTemplate(adminMongoDbFactory());
		return mongoTemplate;
	}

}
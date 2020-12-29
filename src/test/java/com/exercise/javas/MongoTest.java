package com.exercise.javas;

import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class MongoTest {
    Logger logger = LoggerFactory.getLogger(MongoTest.class);

    @Test
    void T1() {
        MongoClient mongoClient = MongoClients.create("mongodb://localhost:27017");
        logger.info("MongoConn Status :::: {} ::::",mongoClient.toString());
    }
}

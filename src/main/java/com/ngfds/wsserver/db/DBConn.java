package com.ngfds.wsserver.db;

import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import org.bson.Document;

public class DBConn {

    private static final MongoClient client = MongoClients.create("mongodb://db:27017/");
    private static final MongoDatabase database;

    static {
        database = client.getDatabase("chatapp");
    }

    public static MongoCollection<Document> getCollection(String collectionName) {
        return database.getCollection(collectionName);
    }

}

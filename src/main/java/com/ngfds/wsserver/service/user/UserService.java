package com.ngfds.wsserver.service.user;

import com.mongodb.client.MongoCollection;
import com.ngfds.wsserver.dao.DBConn;
import org.bson.Document;
import org.json.JSONObject;

import java.util.UUID;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

import static com.mongodb.client.model.Filters.eq;

public class UserService {

    private static final MongoCollection<Document> usersCollection = DBConn.getCollection("users");
    private static final BlockingQueue<JSONObject> usersToAdd = new LinkedBlockingQueue<>();

    public UserService() {
        Thread userCreationThread = new Thread(this::handleUsersInQueue);
        userCreationThread.start();
    }

    public void createUser(JSONObject payload) {
        usersToAdd.offer(payload);
    }

    public Document getUserByEmail(String email) {
        return usersCollection.find(eq("email", email)).first();
    }

    private void handleUsersInQueue() {
        try {
            while(!Thread.currentThread().isInterrupted()){
                JSONObject payload = usersToAdd.take();
                insertUser(payload);
            }
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }

    private void insertUser(JSONObject payload) {
        Document newUser = new Document();

        newUser.put("_id", UUID.randomUUID().toString());
        newUser.put("name", payload.get("name"));
        newUser.put("email", payload.get("email"));
        newUser.put("password", payload.get("password"));

        usersCollection.insertOne(newUser);
    }

}

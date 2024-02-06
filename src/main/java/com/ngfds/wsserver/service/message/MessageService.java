package com.ngfds.wsserver.service.message;

import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoIterable;
import com.ngfds.wsserver.dao.DBConn;
import org.bson.Document;
import org.json.JSONObject;

import java.util.UUID;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

import static com.mongodb.client.model.Filters.eq;

public class MessageService {

    private static final MongoCollection<Document> messagesCollection = DBConn.getCollection("messages");
    private static final BlockingQueue<JSONObject> messagesToInsert = new LinkedBlockingQueue<>();

    public MessageService() {
        Thread messagesInsertionThread = new Thread(this::handleMessagesInsertion);
        messagesInsertionThread.start();
    }

    public static MongoIterable<JSONObject> getMessagesFromRoom(String roomId) {
        return messagesCollection.find(eq("roomId", roomId)).map(JSONObject::new);
    }

    public JSONObject createMessage(JSONObject payload){
        // add with uuid so it comes in the response
        boolean added = messagesToInsert.offer(
                payload.put("_id", UUID.randomUUID().toString())
        );

        if (added) return payload;
        else return new JSONObject().put("error", "An error occurred when trying to insert the image");
    }

    private static void insertMessage(JSONObject payload) {
        Document newMessage = new Document();

        newMessage.put("_id", payload.get("_id"));
        newMessage.put("roomId", payload.get("roomId"));
        newMessage.put("authorId", payload.get("authorId"));
        newMessage.put("message", payload.get("message"));

        messagesCollection.insertOne(newMessage);
    }

    private void handleMessagesInsertion() {
        try  {
            while(!Thread.currentThread().isInterrupted()) {
                JSONObject payload = messagesToInsert.take();
                insertMessage(payload);
            }
        } catch (InterruptedException e){
            Thread.currentThread().interrupt();
        }
    }

}

package com.ngfds.wsserver.service.message;

import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoIterable;
import com.ngfds.wsserver.dao.DBConn;
import org.bson.Document;
import org.json.JSONObject;

import java.util.UUID;

import static com.mongodb.client.model.Filters.eq;

public class MessageService {

    private static final MongoCollection<Document> messagesCollection = DBConn.getCollection("messages");

    public static Document storeMessage(String message, String authorId, String roomId){

        Document newMessage = new Document();

        newMessage.put("_id", UUID.randomUUID().toString());
        newMessage.put("roomId", roomId);
        newMessage.put("authorId", authorId);
        newMessage.put("message", message);

        messagesCollection.insertOne(newMessage);

        return newMessage;
    }

    public static MongoIterable<JSONObject> getMessagesFromRoom(String roomId) {

        return messagesCollection.find(eq("roomId", roomId)).map(JSONObject::new);

    }


}

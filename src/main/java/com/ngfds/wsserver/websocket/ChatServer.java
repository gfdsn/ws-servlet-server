package com.ngfds.wsserver.websocket;

import com.mongodb.client.MongoIterable;
import com.ngfds.wsserver.utils.MessageBroadcaster;
import com.ngfds.wsserver.service.message.MessageService;
import com.ngfds.wsserver.websocket.room.Room;
import com.ngfds.wsserver.service.room.RoomService;
import jakarta.websocket.*;
import jakarta.websocket.server.PathParam;
import jakarta.websocket.server.ServerEndpoint;
import org.bson.Document;
import org.json.JSONArray;
import org.json.JSONObject;

@ServerEndpoint("/room/{id}")
public class ChatServer {

    @OnOpen
    public void onOpen(@PathParam("id") String id, Session session) {
        Room room = RoomService.createRoom(id);
        room.addSession(session);

        MongoIterable<JSONObject> iterableMessages = MessageService.getMessagesFromRoom(room.getId());

        JSONArray messages = new JSONArray(iterableMessages);

        MessageBroadcaster.sendMessageToRoom(id, messages.toString());
    }

    @OnMessage
    public void onMessage(@PathParam("id") String id, Session session, String message) {
        Room room = RoomService.getRoomByID(id);

        Document newMessage = MessageService.storeMessage(message, "c6e34242-5c3b-42e8-af1f-95e5a168c35d", room.getId());

        MessageBroadcaster.sendMessageToRoom(id, newMessage.toJson());
    }

    @OnClose
    public void onClose(@PathParam("id") String id, Session session) {
        Room room = RoomService.getRoomByID(id);
        room.removeSession(session);
    }

    private void sendMessageToRoom(String roomId, String message) {
        Room room = RoomService.getRoomByID(roomId);

        for (Session s : room.getSessions()){
            s.getAsyncRemote().sendText(message);
        }
    }
}

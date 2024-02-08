package com.ngfds.wsserver.websocket;

import com.mongodb.client.MongoIterable;
import com.ngfds.wsserver.controller.message.MessageController;
import com.ngfds.wsserver.service.message.MessageService;
import com.ngfds.wsserver.websocket.room.Room;
import com.ngfds.wsserver.service.room.RoomService;
import jakarta.websocket.*;
import jakarta.websocket.server.PathParam;
import jakarta.websocket.server.ServerEndpoint;
import org.json.JSONArray;
import org.json.JSONObject;

@ServerEndpoint("/room/{id}")
public class ChatServer {


    private final MessageController messageController;

    public ChatServer() {
        messageController = new MessageController();
    }

    @OnOpen
    public void onOpen(@PathParam("id") String id, Session session) {
        Room room = RoomService.createRoom(id);
        room.addSession(session);

        MongoIterable<JSONObject> iterableMessages = MessageService.getMessagesFromRoom(room.getId());

        JSONArray messages = new JSONArray(iterableMessages);

        messageController.sendMessageToRoom(id, messages.toString());
    }

    @OnMessage
    public void onMessage(@PathParam("id") String id, Session session, String message) {

        JSONObject receivedMessaged = new JSONObject(message);

        JSONObject newMessage = messageController.createMessage(
            new JSONObject()
                .put("message", receivedMessaged.get("message"))
                .put("authorId", receivedMessaged.get("authorId"))
                .put("roomId", id)
                .put("created_at", receivedMessaged.get("created_at"))
        );

        messageController.sendMessageToRoom(id, "[" + newMessage.toString() + "]");
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

package com.ngfds.wsserver.controller.message;

import com.ngfds.wsserver.service.message.MessageService;
import com.ngfds.wsserver.service.room.RoomService;
import com.ngfds.wsserver.websocket.room.Room;
import jakarta.websocket.Session;
import org.bson.Document;
import org.json.JSONObject;

public class MessageController {

    private final MessageService messageService;

    public MessageController() {
        messageService =  new MessageService();
    }

    public JSONObject createMessage(JSONObject payload){

        return messageService.createMessage(payload);

    }

    public void sendMessageToRoom(String roomId, String message) {
        Room room = RoomService.getRoomByID(roomId);

        for (Session s : room.getSessions()){
            s.getAsyncRemote().sendText(message);
        }
    }

}

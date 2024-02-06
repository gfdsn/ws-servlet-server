package com.ngfds.wsserver.message;

import com.ngfds.wsserver.room.Room;
import com.ngfds.wsserver.room.RoomService;
import jakarta.websocket.Session;
import org.json.JSONObject;

public class MessageBroadcaster {

    public static void sendMessageToRoom(String roomId, String message) {
        Room room = RoomService.getRoomByID(roomId);

        for (Session s : room.getSessions()){
            s.getAsyncRemote().sendText(message);
        }
    }

}

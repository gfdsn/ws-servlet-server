package com.ngfds.wsserver.utils;

import com.ngfds.wsserver.websocket.room.Room;
import com.ngfds.wsserver.service.room.RoomService;
import jakarta.websocket.Session;

public class MessageBroadcaster {

    public static void sendMessageToRoom(String roomId, String message) {
        Room room = RoomService.getRoomByID(roomId);

        for (Session s : room.getSessions()){
            s.getAsyncRemote().sendText(message);
        }
    }

}

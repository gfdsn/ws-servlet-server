package com.ngfds.wsserver;

import com.ngfds.wsserver.room.Room;
import com.ngfds.wsserver.room.RoomService;
import jakarta.websocket.*;
import jakarta.websocket.server.PathParam;
import jakarta.websocket.server.ServerEndpoint;

@ServerEndpoint("/room/{id}")
public class ChatServer {

    @OnOpen
    public void onOpen(@PathParam("id") String id, Session session) {
        Room room = RoomService.createRoom(id);
        room.addSession(session);

        System.out.println(room);
    }

    @OnMessage
    public void onMessage(@PathParam("id") String id, Session session, String message) {
        Room room = RoomService.getRoomByID(id);

        for (Session s : room.getSessions()){
            s.getAsyncRemote().sendText(message);
        }
    }

    @OnClose
    public void onClose(@PathParam("id") String id, Session session) {
        Room room = RoomService.getRoomByID(id);
        room.removeSession(session);
    }

}

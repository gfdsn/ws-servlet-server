package com.ngfds.wsserver.service.room;

import com.ngfds.wsserver.websocket.room.Room;

import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

public class RoomService {

    private static final Set<Room> rooms = new HashSet<>();

    public static Room createRoom(String id) {
        Room room = getRoomByID(id);
        if (room != null) return room;

        room = new Room(id);
        rooms.add(room);

        return room;
    }

    public static Room getRoomByID(String id) {
        return rooms.stream()
            .filter(r -> Objects.equals(r.getId(), id))
            .findFirst().orElse(null);
    }

}

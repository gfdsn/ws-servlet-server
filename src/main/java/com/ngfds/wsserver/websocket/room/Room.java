package com.ngfds.wsserver.websocket.room;

import jakarta.websocket.Session;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Stream;

public class Room {

    private final String id;
    private final Set<Session> sessions = new HashSet<>();

    public Room(String id) { this.id = id; }

    public Set<Session> getSessions(){
        return sessions;
    }

    public void addSession(Session session){
        sessions.add(session);
    }

    public void removeSession(Session session){
        sessions.remove(session);
    }

    public String getId() { return id; }

    @Override
    public int hashCode() {
        return id.hashCode();
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();

        Stream<String> sessionIds = sessions.stream().map(Session::getId);

        sb.append(System.lineSeparator());
        sb.append("Room #").append(id);
        sb.append(System.lineSeparator());
        sb.append("Sessions connected: ");
        sb.append(Arrays.toString(sessionIds.toArray()));

        return sb.toString();
    }
}

package com.ngfds.wsserver.dto;

import org.bson.Document;
import org.json.JSONObject;

public class UserDto {
    private final String id;
    private final String name;
    private final String email;

    public UserDto(Document user) {
        this.id = user.getString("_id");
        this.name = user.getString("name");
        this.email = user.getString("email");
    }

    public JSONObject toJson(){
        return new JSONObject()
            .put("_id", this.id)
            .put("name", this.name)
            .put("email", this.email);
    }

}

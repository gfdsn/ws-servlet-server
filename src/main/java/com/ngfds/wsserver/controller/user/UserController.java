package com.ngfds.wsserver.controller.user;

import com.ngfds.wsserver.service.user.UserService;
import org.bson.Document;
import org.json.JSONObject;

public class UserController {

    private final UserService userService;

    public UserController() {
        this.userService = new UserService();
    }

    public void createUser(JSONObject payload) {
        this.userService.createUser(payload);
    }

    public Document getUserById(String id){
        return this.userService.getUserById(id);
    }

    public Document getUserByEmail(String email) {
        return this.userService.getUserByEmail(email);
    }



}

package com.ngfds.wsserver.service.auth;

import com.ngfds.wsserver.dto.UserDto;
import com.ngfds.wsserver.service.user.UserService;
import com.ngfds.wsserver.utils.TokenGenerator;
import org.bson.Document;
import org.json.JSONObject;

import java.security.NoSuchAlgorithmException;

public class AuthService {

    private final UserService userService;

    public AuthService() throws NoSuchAlgorithmException { this.userService = new UserService(); }

    public void createUser(JSONObject payload) {
        userService.createUser(payload);
    }

    public JSONObject loginUser(JSONObject payload) throws NoSuchAlgorithmException {
        String email = payload.getString("email");
        String password = payload.getString("password");

        JSONObject res = new JSONObject();
        Document user = userService.getUserByEmail(email);

        if (user != null
                && verifyPassword(user, password)) {

            String token = TokenGenerator.generateToken(user);

            res.put("token", token);
            res.put("user", new UserDto(user).toJson());

        } else res.put("error", "Invalid credentials");

        return res;
    }

    public boolean verifyPassword(Document user, String password) {
        return user.get("password").equals(password);
    }

}

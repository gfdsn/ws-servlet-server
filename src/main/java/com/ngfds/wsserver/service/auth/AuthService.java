package com.ngfds.wsserver.service.auth;

import com.ngfds.wsserver.controller.user.UserController;
import com.ngfds.wsserver.dto.UserDto;
import com.ngfds.wsserver.service.user.UserService;
import com.ngfds.wsserver.utils.ResponseInfo;
import com.ngfds.wsserver.utils.TokenGenerator;
import org.bson.Document;
import org.json.JSONObject;

import java.security.NoSuchAlgorithmException;

public class AuthService {

    private final UserController userController;

    public AuthService() { this.userController = new UserController(); }

    public JSONObject loginUser(JSONObject payload) throws NoSuchAlgorithmException {
        String email = payload.getString("email");
        String password = payload.getString("password");

        JSONObject res = new JSONObject();
        Document user = userController.getUserByEmail(email);

        if (user != null
                && verifyPassword(user, password)) {

            String token = TokenGenerator.generateToken(user);

            res.put("token", token);
            res.put("user", new UserDto(user).toJson());

        } else res.put("error", ResponseInfo.AUTH_INVALID_CREDENTIALS.value());

        return res;
    }

    private boolean verifyPassword(Document user, String password) {
        return user.get("password").equals(password);
    }

}

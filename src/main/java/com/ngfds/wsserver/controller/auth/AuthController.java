package com.ngfds.wsserver.controller.auth;

import com.ngfds.wsserver.service.auth.AuthService;
import org.json.JSONObject;

import java.security.NoSuchAlgorithmException;

public class AuthController {

    private final AuthService authService;

    public AuthController() {
        this.authService = new AuthService();
    }

    public JSONObject login(JSONObject payload) throws NoSuchAlgorithmException {
        return this.authService.loginUser(payload);
    }

}

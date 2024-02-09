package com.ngfds.wsserver.controller.auth;

import com.ngfds.wsserver.service.auth.AuthService;
import com.ngfds.wsserver.utils.CorsHandler;
import com.ngfds.wsserver.utils.ResponseInfo;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.json.JSONObject;

import java.io.IOException;
import java.security.NoSuchAlgorithmException;
import java.util.stream.Collectors;

@WebServlet("/register")
public class RegisterServlet extends HttpServlet {

    /* Example of basic register */
    private final AuthService authService;

    public RegisterServlet() throws NoSuchAlgorithmException { this.authService = new AuthService(); }

    @Override
    protected void doOptions(HttpServletRequest request, HttpServletResponse response) {
        CorsHandler.handleCorsHeaders(response);
    }

    @Override
    public void doPost(HttpServletRequest request, HttpServletResponse response)
        throws IOException {
        CorsHandler.handleCorsHeaders(response);

        String requestBody = request.getReader().lines().collect(Collectors.joining());

        JSONObject data = new JSONObject(requestBody);
        authService.createUser(data);

        JSONObject jsonRes = new JSONObject();
        jsonRes.put("message", ResponseInfo.USER_CREATED.value());

        response.setStatus(201);
        response.getWriter().write(jsonRes.toString());
    }

}

package com.ngfds.wsserver.controller.auth.api;

import com.ngfds.wsserver.controller.user.UserController;
import com.ngfds.wsserver.utils.CorsHandler;
import com.ngfds.wsserver.utils.ResponseInfo;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.json.JSONObject;

import java.io.IOException;
import java.util.stream.Collectors;

@WebServlet("/register")
public class RegisterServlet extends HttpServlet {

    /* Example of basic register */
    private final UserController userController;

    public RegisterServlet() { this.userController = new UserController(); }

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
        userController.createUser(data);

        JSONObject jsonRes = new JSONObject();
        jsonRes.put("message", ResponseInfo.USER_CREATED.value());

        response.setStatus(201);
        response.getWriter().write(jsonRes.toString());
    }

}

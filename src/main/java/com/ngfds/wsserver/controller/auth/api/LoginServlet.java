package com.ngfds.wsserver.controller.auth.api;

import com.ngfds.wsserver.controller.auth.AuthController;
import com.ngfds.wsserver.utils.CorsHandler;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.json.JSONObject;

import java.io.IOException;
import java.security.NoSuchAlgorithmException;
import java.util.stream.Collectors;

@WebServlet("/login")
public class LoginServlet extends HttpServlet {

    /* Example of basic login */
    private final AuthController authController;

    public LoginServlet() {
        this.authController = new AuthController();
    }

    @Override
    protected void doOptions(HttpServletRequest request, HttpServletResponse response) {
        CorsHandler.handleCorsHeaders(response);
    }

    @Override
    public void doPost(HttpServletRequest request, HttpServletResponse response)
            throws IOException {
        CorsHandler.handleCorsHeaders(response);

        String requestData = request.getReader().lines().collect(Collectors.joining());
        JSONObject data = new JSONObject(requestData);

        JSONObject jsonRes;

        try {
            jsonRes = authController.login(data);

            response.setStatus(200);
            response.getWriter().write(jsonRes.toString());

        } catch (NoSuchAlgorithmException e) {
            response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "An internal error occurred");
        }

    }

}

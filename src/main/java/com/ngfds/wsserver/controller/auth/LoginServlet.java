package com.ngfds.wsserver.controller.auth;

import com.ngfds.wsserver.service.auth.AuthService;
import com.ngfds.wsserver.utils.CorsHandler;
import com.ngfds.wsserver.utils.TokenGenerator;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.json.JSONObject;

import javax.crypto.SecretKey;
import java.io.IOException;
import java.security.NoSuchAlgorithmException;
import java.util.stream.Collectors;

import static com.mongodb.client.model.Filters.eq;

@WebServlet("/login")
public class LoginServlet extends HttpServlet {

    /* Example of basic login */
    private final AuthService authService;

    public LoginServlet() throws NoSuchAlgorithmException {
        this.authService = new AuthService();
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
            jsonRes = authService.loginUser(data);

            response.setStatus(200);
            response.getWriter().write(jsonRes.toString());

        } catch (NoSuchAlgorithmException e) {
            response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "An internal error occurred");
        }

    }

}

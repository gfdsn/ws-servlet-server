package com.ngfds.wsserver.auth;

import com.mongodb.client.MongoCollection;
import com.ngfds.wsserver.db.DBConn;
import com.ngfds.wsserver.util.CorsHandler;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.bson.Document;
import org.json.JSONObject;

import java.io.IOException;
import java.util.UUID;
import java.util.stream.Collectors;

@WebServlet("/register")
public class RegisterServlet extends HttpServlet {

    /* Example of basic register */

    @Override
    protected void doOptions(HttpServletRequest request, HttpServletResponse response) {
        CorsHandler.handleCorsHeaders(response);
    }

    @Override
    public void doPost(HttpServletRequest request, HttpServletResponse response)
        throws IOException {

        CorsHandler.handleCorsHeaders(response);

        MongoCollection<Document> users = DBConn.getCollection("users");
        String requestBody = request.getReader().lines().collect(Collectors.joining());
        JSONObject data = new JSONObject(requestBody);
        Document user = new Document();

        user.put("_id", UUID.randomUUID().toString());
        user.put("name", data.get("name"));
        user.put("email", data.get("email"));
        user.put("password", data.get("password"));

        users.insertOne(user);

        response.setStatus(201);
        response.getWriter().write(new JSONObject(user).toString());

    }

}

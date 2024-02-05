package com.ngfds.wsserver.auth;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.mongodb.client.MongoCollection;
import com.ngfds.wsserver.db.DBConn;
import com.ngfds.wsserver.util.SecretKeyGenerator;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.bson.Document;
import org.json.JSONObject;

import javax.crypto.SecretKey;
import java.io.IOException;
import java.security.NoSuchAlgorithmException;
import java.util.Date;
import java.util.stream.Collectors;

import static com.mongodb.client.model.Filters.eq;

@WebServlet("/login")
public class LoginServlet extends HttpServlet {

    private final SecretKey superSecretKey = SecretKeyGenerator.generateKey("HmacSHA256");

    public LoginServlet() throws NoSuchAlgorithmException {}

    public void doPost(HttpServletRequest request, HttpServletResponse response)
            throws IOException {

        MongoCollection<Document> usersCollection = DBConn.getCollection("users");

        String requestData = request.getReader().lines().collect(Collectors.joining());
        JSONObject data = new JSONObject(requestData);

        Document user = usersCollection.find(eq("email", data.get("email"))).first();

        if (user != null && user.get("password").equals(data.get("password"))) {

            Algorithm signer = Algorithm.HMAC256(superSecretKey.getEncoded());
            String token = JWT.create()
                    .withIssuer("chatapp")
                    .withClaim("userId", user.getString("_id"))
                    .withIssuedAt(new Date())
                    .sign(signer);

            JSONObject tokenRes = new JSONObject();
            tokenRes.put("token", token);

            response.setStatus(200);
            response.getWriter().write(tokenRes.toString());

        } else {

            response.setStatus(404);

            response.getWriter().write(new JSONObject("{ error: 'User not found.' }").toString());

        }

    }

}

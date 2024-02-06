package com.ngfds.wsserver.utils;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import org.bson.Document;

import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import java.security.NoSuchAlgorithmException;
import java.util.Date;

public class TokenGenerator {

    public static String generateToken(Document user) throws NoSuchAlgorithmException {

        final SecretKey superSecretKey = TokenGenerator.generateKey("HmacSHA256");

        Algorithm signer = Algorithm.HMAC256(superSecretKey.getEncoded());

        return JWT.create()
                .withIssuer("chatapp")
                .withClaim("userId", user.getString("_id"))
                .withIssuedAt(new Date())
                .sign(signer);
    }

    private static SecretKey generateKey(String algorithm) throws NoSuchAlgorithmException {

        KeyGenerator superSecretKey = KeyGenerator.getInstance(algorithm);
        return superSecretKey.generateKey();

    }

}

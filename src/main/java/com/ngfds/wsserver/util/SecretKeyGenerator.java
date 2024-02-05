package com.ngfds.wsserver.util;

import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import java.security.NoSuchAlgorithmException;

public class SecretKeyGenerator {

    public static SecretKey generateKey(String algorithm) throws NoSuchAlgorithmException {

        KeyGenerator superSecretKey = KeyGenerator.getInstance(algorithm);
        return superSecretKey.generateKey();

    }

}

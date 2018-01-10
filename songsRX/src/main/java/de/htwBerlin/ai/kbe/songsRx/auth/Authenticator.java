package de.htwBerlin.ai.kbe.songsRx.auth;

import java.security.SecureRandom;
import java.util.HashMap;
import java.util.Map;

public class Authenticator implements IAuthenticator {

    private static Map<String, String> tokenList = new HashMap<>();
    private String token;

    @Override
    public String get(String userId) {
        return null;
    }

    public String generateToken(String userId) {

        //generate token
        SecureRandom random = new SecureRandom();
        byte bytes[] = new byte[20];
        random.nextBytes(bytes);
        token = bytes.toString();

        //add to hashmap
        tokenList.put(userId, token);

        return token;
    }

    public boolean authenticate(String token) {
        if (tokenList.containsValue(token))
            return true;
        return false;
    }
}

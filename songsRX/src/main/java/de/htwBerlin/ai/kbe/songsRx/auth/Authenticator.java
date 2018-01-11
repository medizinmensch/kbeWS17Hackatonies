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

    public String getToken(String userId) {
        if (tokenList.containsKey(userId))
            return tokenList.get(userId);
        return null;
    }

    public boolean hasOwnerPrivileges(String userId, String authToken) {
        //check if given token is same as token for userId given as parameter in url
        if (authToken.equals(getToken(userId))) {
           return true;
        }
        return false;
    }

    public String generateToken(String userId) {

        //generate token
        SecureRandom random = new SecureRandom();
        long longToken = Math.abs( random.nextLong() );
        token = Long.toString( longToken, 16 );

        //add to hashmap
        tokenList.put(userId, token);

        return token;
    }

    public boolean authenticate(String token) {
        // back door, master token for testing purposes
        if (token.equals("1a2b3c4d5e6f7g8h"))
            return true;
        if (tokenList.containsValue(token))
            return true;
        return false;
    }
}

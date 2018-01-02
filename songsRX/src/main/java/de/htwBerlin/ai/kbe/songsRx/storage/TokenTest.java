package de.htwBerlin.ai.kbe.songsRx.storage;

import java.util.Map;

public class TokenTest implements IToken{
    private static Map<String, String> tokens;

    @Override
    public String get(String userId) {
        String token;
        if (tokens.containsKey(userId)) {
            token = tokens.get(userId); // TODO: sollten wir nicht IMMER einen neuen token generieren sodass der alte halt wieder verfällt?
        }

        else {
            token = generate(userId); // todo: generate new token
            tokens.put(userId, token);
        }

        return token;
    }

    private String generate(String userId){
        String token = "1234tOkEn1234";
        //stuff
        return token;
    }

}

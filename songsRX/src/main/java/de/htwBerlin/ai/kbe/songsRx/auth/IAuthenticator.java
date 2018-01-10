package de.htwBerlin.ai.kbe.songsRx.auth;

public interface IAuthenticator {

    public String get(String userId);
    public boolean authenticate(String token);
    public String generateToken(String userId);
}

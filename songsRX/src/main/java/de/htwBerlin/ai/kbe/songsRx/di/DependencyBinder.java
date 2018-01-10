package de.htwBerlin.ai.kbe.songsRx.di;

import de.htwBerlin.ai.kbe.songsRx.auth.Authenticator;
import de.htwBerlin.ai.kbe.songsRx.auth.IAuthenticator;
import org.glassfish.hk2.utilities.binding.AbstractBinder;

import javax.inject.Singleton;

public class DependencyBinder extends AbstractBinder {

    @Override
    protected void configure() {
        bind(Authenticator.class).to(IAuthenticator.class).in(Singleton.class);
    }
}




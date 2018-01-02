package de.htwBerlin.ai.kbe.songsRx.di;

import de.htwBerlin.ai.kbe.songsRx.storage.IToken;
import de.htwBerlin.ai.kbe.songsRx.storage.Token;
import org.glassfish.hk2.utilities.binding.AbstractBinder;

import javax.inject.Singleton;

public class DependencyBinder extends AbstractBinder {

    @Override
    protected void configure() {
        bind(Token.class).to(IToken.class).in(Singleton.class);
    }
}




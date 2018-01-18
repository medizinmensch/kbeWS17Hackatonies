package de.htwBerlin.ai.kbe.songsRx.di;

import de.htwBerlin.ai.kbe.songsRx.auth.Authenticator;
import de.htwBerlin.ai.kbe.songsRx.auth.IAuthenticator;
import de.htwBerlin.ai.kbe.songsRx.storage.*;
import org.glassfish.hk2.utilities.binding.AbstractBinder;

import javax.inject.Singleton;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

public class DependencyBinder extends AbstractBinder {

    @Override
    protected void configure() {
        bind(Persistence.createEntityManagerFactory("songsRX-PU")).to(EntityManagerFactory.class);
        bind(SongDao.class).to(ISongDao.class).in(Singleton.class);
        bind(SonglistDao.class).to(ISonglistDao.class).in(Singleton.class);
        bind(UserDao.class).to(IUserDao.class).in(Singleton.class);
        bind(Authenticator.class).to(IAuthenticator.class).in(Singleton.class);
    }
}




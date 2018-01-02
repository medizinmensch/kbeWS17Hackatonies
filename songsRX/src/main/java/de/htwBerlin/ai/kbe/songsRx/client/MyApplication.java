package de.htwBerlin.ai.kbe.songsRx.client;

import de.htwBerlin.ai.kbe.songsRx.di.DependencyBinder;
import org.glassfish.jersey.server.ResourceConfig;

public class MyApplication extends ResourceConfig {

    public MyApplication() {
        register(new DependencyBinder());
        packages("de.htwBerlin.ai.kbe.songsRx.services"); }
}

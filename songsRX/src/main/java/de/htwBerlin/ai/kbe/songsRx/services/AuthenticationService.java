package de.htwBerlin.ai.kbe.songsRx.services;

import javax.inject.Inject;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import de.htwBerlin.ai.kbe.songsRx.auth.IAuthenticator;
import de.htwBerlin.ai.kbe.songsRx.beans.User;
import de.htwBerlin.ai.kbe.songsRx.storage.UserStorage;

import java.util.Collection;

@Path("/auth")
public class AuthenticationService { // schaut in die Map, (injiziertes POJO (ist leer beim ersten Req(->Singleton.class in den DependencyBinder?))) legt den Token an


    @Inject
    private IAuthenticator authenticator;

    @GET
    @Produces({MediaType.TEXT_PLAIN})
    public Response getUserToken(@QueryParam("userId") String userId) {
        if (userId == null || userId.equals(""))
            return Response.status(Response.Status.BAD_REQUEST).build();

        boolean userExists = UserStorage.getInstance().userExists(userId);
        if (userExists) {
            //generate token
            String token = authenticator.generateToken(userId);
            return Response.status(Response.Status.OK).entity((token)).build();
        }

        //user does not exist
        return Response.status(Response.Status.FORBIDDEN).build();

    }
}

package de.htwBerlin.ai.kbe.songsRx.services;

import javax.inject.Inject;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import de.htwBerlin.ai.kbe.songsRx.beans.User;
import de.htwBerlin.ai.kbe.songsRx.storage.IToken;
import de.htwBerlin.ai.kbe.songsRx.storage.UserStorage;

import java.util.Map;

@Path("/auth")
public class AuthenticationService { // schaut in die Map, (injiziertes POJO (ist leer beim ersten Req(->Singleton.class in den DependencyBinder?))) legt den Token an


    // filter Singleton + dependency injection
    //

    private IToken token;

    @Inject
    public AuthenticationService(IToken actualToken){
        token = actualToken;
    }

    @GET
    //@QueryParam("userId")
    @Produces({MediaType.TEXT_PLAIN})
    public Response getUserToken(@QueryParam("userId") String userId) {
        if (userId.equals("")) {
            Response.status(Response.Status.BAD_REQUEST).build();
        }

        String token = token.get(); //das ist noch ein problem - weiß nich nicht wie wir das lösen könnten.
        // token already exists, what do to here? i guess: give token back
        return Response.status(Response.Status.OK).entity(token).build();

        //generate
        // klasse guckt sich header an und lässt in den club oder nicht
        // instgesammt
        // token zu hashmap adden

    }
}

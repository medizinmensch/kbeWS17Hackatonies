package de.htwBerlin.ai.kbe.songsRx.services;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import de.htwBerlin.ai.kbe.songsRx.beans.User;
import de.htwBerlin.ai.kbe.songsRx.storage.UserStorage;

@Path("/auth")
public class AuthenticationService { // schaut in die Map, (injiziertes POJO (ist leer beim ersten Req)) legt den Token an
	
    @GET
    //@QueryParam("userId")
    @Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON })
    public Response getUserToken(@QueryParam("userId") String userId) {
        User user = UserStorage.getInstance().getUser(userId);
        
        
        	
        if (user != null) {
            // tokengenerierung + Rückgabe
        	// filter Singelton + dependency invjection
        	// klasse guckt sich header an und lässt in den club oder nicht
        	// instgesammt 
        	// token in hashmap pumpen
        	return Response.status(Response.Status.OK).build();
        } else {
            return Response.status(Response.Status.FORBIDDEN).build();
        }
        }
}

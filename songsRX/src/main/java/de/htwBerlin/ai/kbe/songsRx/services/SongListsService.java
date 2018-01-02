package de.htwBerlin.ai.kbe.songsRx.services;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

@Path("/userId/{someUserId}/songLists/{someSongListId}")
public class SongListsService {

    @GET
    @Produces({ MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON })
    public Response getSong(@PathParam("someUserId") Integer id, @PathParam("someSongListId") Integer id2 ) {
        if (id == 2 && id2 == 3 )
            return Response.ok().build();
        else
            return Response.status(Response.Status.NOT_FOUND).build();
    }
}

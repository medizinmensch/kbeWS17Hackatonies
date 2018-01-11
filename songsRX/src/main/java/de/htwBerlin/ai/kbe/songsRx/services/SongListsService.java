package de.htwBerlin.ai.kbe.songsRx.services;

import de.htwBerlin.ai.kbe.songsRx.auth.IAuthenticator;

import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.core.*;

@Path("/userId/{userId}/songLists")
public class SongListsService {


    @Inject
    private IAuthenticator authenticator;

    @Context
    HttpHeaders headers;


    @GET
    @Produces({ MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON })
    public Response getAllSonglists(@PathParam("userId") String userId) {
        String authToken = headers.getRequestHeader("Authorization").get(0);

        if (authenticator.hasOwnerPrivileges(userId, authToken)) {
            //return public & private playlists
            //Response.ok(songDao.getPlaylist())
            return Response.ok("public & private").build();
        } else {
            //return only public playlists
            return Response.ok("public").build();
        }
    }

    @GET
    @Path("/{songListId}")
    @Produces({ MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON })
    public Response getSingleSonglist(@PathParam("userId") String userId, @PathParam("songListId") Integer songListId) {
        String authToken = headers.getRequestHeader("Authorization").get(0);

        //if songlist private
        boolean isPrivate = true;
        if (isPrivate && authenticator.hasOwnerPrivileges(userId, authToken)) {
            //return private playlist
        } else {
            //return playlist
        }
        return Response.ok().build();
    }


    @POST
    @Produces({MediaType.TEXT_PLAIN})
    public Response createSongList(@PathParam("userId") String userId) {
        String authToken = headers.getRequestHeader("Authorization").get(0);

        //only create playlist if userId from url matches userId from authToken
        if (authenticator.hasOwnerPrivileges(userId, authToken)) {
            //create playlist, songlist id gets created from dao
            return Response.ok().build();
        } else {
            return Response.status(Response.Status.UNAUTHORIZED).build();
        }
    }

    @DELETE
    @Path("/{songListId}")
    public Response deletePlaylist(@PathParam("userId") String userId, @PathParam("songListId") Integer songListId) {
        String authToken = headers.getRequestHeader("Authorization").get(0);

        //only delete playlist if userId from url matches userId from authToken
        if (authenticator.hasOwnerPrivileges(userId, authToken)) {
            //delete playlist
            return Response.ok().build();
        } else {
            return Response.status(Response.Status.UNAUTHORIZED).build();
        }
    }

}

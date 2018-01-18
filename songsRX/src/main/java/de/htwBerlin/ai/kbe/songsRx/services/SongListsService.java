package de.htwBerlin.ai.kbe.songsRx.services;

import de.htwBerlin.ai.kbe.songsRx.auth.IAuthenticator;
import de.htwBerlin.ai.kbe.songsRx.beans.Song;
import de.htwBerlin.ai.kbe.songsRx.storage.ISonglistDao;

import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.core.*;
import java.util.Collection;

@Path("/userId/{userId}/songLists")
public class SongListsService {

    @Inject
    private IAuthenticator authenticator;

    @Inject
    private ISonglistDao songlistDao;

    @Context
    HttpHeaders headers;


    @GET
    @Produces({ MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON })
    public Response getAllSonglists(@PathParam("userId") String userId) {
        String authToken = headers.getRequestHeader("Authorization").get(0);

        if (authenticator.hasOwnerPrivileges(userId, authToken)) {
            //return public & private playlists
            //Response.ok(songDao.getPlaylist())
            return Response.ok(songlistDao.getAllSonglistsOfUser(userId)).build();
        } else {
            //return only public playlists
            return Response.ok(songlistDao.getAllPublicSonglistsOfUser(userId)).build();
        }
    }

    @GET
    @Path("/{songListId}")
    @Produces({ MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON })
    public Response getSingleSonglist(@PathParam("userId") String userId, @PathParam("songListId") Integer songListId) {
        String authToken = headers.getRequestHeader("Authorization").get(0);

        //if songlist private
        boolean isPrivate = songlistDao.songlistIsPrivate(songListId);
        if (authenticator.hasOwnerPrivileges(userId, authToken)) {
            //return private playlist
            return Response.ok(songlistDao.getSonglistOfUser(songListId, userId)).build();
        } else if (isPrivate) {
            //return error
            return Response.status(Response.Status.FORBIDDEN).build();
        } else {
            //return public playlist
            return Response.ok(songlistDao.getSonglistOfUser(songListId, userId)).build();
        }

    }


    @POST
    @Produces({MediaType.TEXT_PLAIN})
    public Response createSongList(@PathParam("userId") String userId, Collection<Song> songs) {
        String authToken = headers.getRequestHeader("Authorization").get(0);

        //only create playlist if userId from url matches userId from authToken
        if (authenticator.hasOwnerPrivileges(userId, authToken)) {
            //create playlist, songlist id gets created from dao
            Integer songlistId = songlistDao.createNewSongListOfUser(userId, songs);
            return Response.ok(songlistId).build();
        } else {
            return Response.status(Response.Status.FORBIDDEN).build();
        }
    }

    @PUT
    @Path("/{songListId}")
    public Response.

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

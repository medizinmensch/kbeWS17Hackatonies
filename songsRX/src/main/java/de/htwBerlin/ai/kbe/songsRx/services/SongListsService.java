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
            return Response.ok(songlistDao.getSonglistsOfUser(userId)).build();
        } else {
            //return only public playlists
            return Response.ok(songlistDao.getPublicSonglistsOfUser(userId)).build();
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
            //todo: what if songList from id is not the songlist of user
            return Response.ok(songlistDao.getSonglist(songListId)).build();
        } else if (isPrivate) {
            //return error
            return Response.status(Response.Status.FORBIDDEN).build();
        } else {
            //return public playlist
            //todo: is songlist public?
            return Response.ok(songlistDao.getSonglist(songListId)).build();
        }

    }


    @POST
    @Produces({MediaType.TEXT_PLAIN})
    public Response createSongList(@PathParam("userId") String userId, @QueryParam("songlistName") String songlistName, @QueryParam("isPublic") boolean isPublic) {
        String authToken = headers.getRequestHeader("Authorization").get(0);

        //only create playlist if userId from url matches userId from authToken
        if (authenticator.hasOwnerPrivileges(userId, authToken)) {
            //create playlist, songlist id gets created from dao
            Integer songlistId = songlistDao.createNewSongList(userId, songlistName, isPublic);
            return Response.ok(songlistId).build();
        } else {
            return Response.status(Response.Status.FORBIDDEN).build();
        }
    }

    //create post method to add song to playlist

    @PUT
    @Path("/{songListId}")
    public Response addSongsToSongList(@PathParam("userId") String userId, @PathParam("songListId") Integer songListId, Collection<Song> songs) {
        String authToken = headers.getRequestHeader("Authorization").get(0);

        //only update playlist if userId from url matches userId from authToken
        if (authenticator.hasOwnerPrivileges(userId, authToken)) {
            //add songs to song playlist
            //boolean success = songlistDao.addSongsToSonglist(songListId, songs);
            return Response.ok(false).build();
        } else {
            return Response.status(Response.Status.FORBIDDEN).build();
        }
    }

    @PUT
    @Path("/{songListId}/overwrite")
    public Response updateSongList(@PathParam("userId") String userId, @PathParam("songListId") Integer songListId) {
        String authToken = headers.getRequestHeader("Authorization").get(0);

        //only update playlist if userId from url matches userId from authToken
        if (authenticator.hasOwnerPrivileges(userId, authToken)) {
            //update playlist
            //boolean success = songlistDao.updateSongListOfUser(userId, songListId, songs);
            return Response.ok(false).build();
        } else {
            return Response.status(Response.Status.FORBIDDEN).build();
        }
    }

    @DELETE
    @Path("/{songListId}")
    public Response deletePlaylist(@PathParam("userId") String userId, @PathParam("songListId") Integer songListId) {
        String authToken = headers.getRequestHeader("Authorization").get(0);

        //only delete playlist if userId from url matches userId from authToken
        if (authenticator.hasOwnerPrivileges(userId, authToken)) {
            //delete playlist
            //todo: check if songlist is from user
            boolean success = songlistDao.deleteSonglist(songListId);
            if (success)
                return Response.ok("Successfully deleted playlist with id:" + songListId).build();

            return Response.status(Response.Status.NOT_FOUND).build();
        } else {
            return Response.status(Response.Status.UNAUTHORIZED).build();
        }
    }

}

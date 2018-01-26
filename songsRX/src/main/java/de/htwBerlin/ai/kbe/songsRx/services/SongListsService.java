package de.htwBerlin.ai.kbe.songsRx.services;

import de.htwBerlin.ai.kbe.songsRx.auth.IAuthenticator;
import de.htwBerlin.ai.kbe.songsRx.beans.Song;
import de.htwBerlin.ai.kbe.songsRx.beans.Songlist;
import de.htwBerlin.ai.kbe.songsRx.beans.User;
import de.htwBerlin.ai.kbe.songsRx.storage.ISonglistDao;
import de.htwBerlin.ai.kbe.songsRx.storage.IUserDao;
import jdk.nashorn.internal.ir.annotations.Ignore;

import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.core.*;
import java.util.Collection;
import java.util.List;
import java.util.Map;

@Path("/userId/{userId}/songLists")
public class SongListsService {

    @Inject
    private IAuthenticator authenticator;

    @Inject
    private ISonglistDao songlistDao;

    @Inject
    private IUserDao userDao;

    @Context
    HttpHeaders headers;


    @GET
    @Produces({ MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML })
    public Response getAllSonglists(@PathParam("userId") String userId) {
        String authToken = headers.getRequestHeader("Authorization").get(0);
        if (userId == null)
            return Response.status(Response.Status.BAD_REQUEST).build();

        User user = userDao.getUser(userId);

        if (authenticator.hasOwnerPrivileges(userId, authToken)) {
            //return public & private playlists
            return Response.ok(songlistDao.getSonglistsOfUser(user)).build();
        } else {
            //return only public playlists
            return Response.ok(songlistDao.getPublicSonglistsOfUser(user)).build();
        }
    }

    @GET
    @Path("/{songListId}")
    @Produces({ MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON })
    public Response getSingleSonglist(@PathParam("userId") String userId, @PathParam("songListId") Integer songListId) {
        String authToken = headers.getRequestHeader("Authorization").get(0);

        //if songlist private
        boolean isPrivate = songlistDao.songlistIsPrivate(songListId);
        User user = userDao.getUser(userId);

        if (authenticator.hasOwnerPrivileges(userId, authToken)) {
            //return private playlist
            Songlist songlist = songlistDao.getSonglist(songListId, user);
            if (songlist == null)
                return Response.status(Response.Status.FORBIDDEN).build();

            return Response.ok(songlist).build();
        } else if (isPrivate) {
            //return error
            return Response.status(Response.Status.FORBIDDEN).build();
        } else {
            //return public playlist
            return Response.ok(songlistDao.getSonglist(songListId, user)).build();
        }

    }

    @POST
    @Consumes({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
    @Produces({MediaType.TEXT_PLAIN})
    public Response createSongListWithPayload(@PathParam("userId") String userId, Songlist songlist, @Context UriInfo uriInfo) {
        String authToken = headers.getRequestHeader("Authorization").get(0);

        if (songlist == null)
            return Response.status(Response.Status.BAD_REQUEST).build();

        //only create playlist if userId from url matches userId from authToken
        if (authenticator.hasOwnerPrivileges(userId, authToken)) {
            //create playlist, songlist id gets created from dao
            User user = userDao.getUser(userId);
            Integer songlistId = songlistDao.createNewSongListWithPayload(user, songlist);

            if (songlistId.equals(0))
                return Response.status(Response.Status.BAD_REQUEST).build();

            //setting location header with new id 
            UriBuilder uriBuilder = uriInfo.getAbsolutePathBuilder();
            uriBuilder.path(Integer.toString(songlistId));
            return Response.status(Response.Status.CREATED).header("Location-Header", uriBuilder.build()).build();
        } else {
            return Response.status(Response.Status.FORBIDDEN).build();
        }
    }

    @DELETE
    @Path("/{songListId}")
    public Response deletePlaylist(@PathParam("userId") String userId, @PathParam("songListId") Integer songListId) {
        String authToken = headers.getRequestHeader("Authorization").get(0);

        if (!songlistDao.songlistExists(songListId))
            return Response.status(Response.Status.NOT_FOUND).build();

        //only delete playlist if userId from url matches userId from authToken
        if (authenticator.hasOwnerPrivileges(userId, authToken)) {
            //delete playlist
            User user = userDao.getUser(userId);
            boolean success = songlistDao.deleteSonglist(songListId, user);
            if (success)
                return Response.ok("Successfully deleted playlist with id: " + songListId).build();

            return Response.status(Response.Status.FORBIDDEN).build();
        } else {
            return Response.status(Response.Status.UNAUTHORIZED).build();
        }
    }

    // more methods for higher usability. not yet finished and tested
    // also not neccessary for abgabe 5

       /*
    @POST
    @Consumes({MediaType.TEXT_PLAIN})
    @Produces({MediaType.TEXT_PLAIN})
    public Response createSongList(@PathParam("userId") String userId, @QueryParam("songlistName") String songlistName, @QueryParam("isPublic") boolean isPublic) {
        String authToken = headers.getRequestHeader("Authorization").get(0);

        //only create playlist if userId from url matches userId from authToken
        if (authenticator.hasOwnerPrivileges(userId, authToken)) {
            //create playlist, songlist id gets created from dao
            User user = userDao.getUser(userId);
            Integer songlistId = songlistDao.createNewSongList(user, songlistName, isPublic);
            return Response.ok(songlistId).build();
        } else {
            return Response.status(Response.Status.FORBIDDEN).build();
        }
    }
    */

    @PUT
    @Path("/{songListId}")
    public Response addSongsToSongList(@PathParam("userId") String userId, @PathParam("songListId") Integer songListId, Collection<Song> songs) {
        String authToken = headers.getRequestHeader("Authorization").get(0);

        //only update playlist if userId from url matches userId from authToken
        if (authenticator.hasOwnerPrivileges(userId, authToken)) {
            //add songs to song playlist
            boolean success = songlistDao.addSongsToSonglist(songListId, songs);
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



}

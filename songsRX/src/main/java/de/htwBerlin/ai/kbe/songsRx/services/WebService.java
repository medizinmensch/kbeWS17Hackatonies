package de.htwBerlin.ai.kbe.songsRx.services;

import de.htwBerlin.ai.kbe.songsRx.beans.Song;
import de.htwBerlin.ai.kbe.songsRx.storage.SongStorage;

import java.util.Collection;

import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

@Path("/songs")
public class WebService {

    @GET
    @Produces({ MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML })
    public Collection<Song> getAllSongs() {
        return SongStorage.getInstance().getAllSongs();
    }

    @GET
    @Path("/{id}")
    @Produces({ MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON })
    public Response getSong(@PathParam("id") Integer id) {
        Song song = SongStorage.getInstance().getSong(id);
        if (song != null) {
            return Response.ok(song).build();
        } else {
            return Response.status(Response.Status.NOT_FOUND).entity("No song found with id " + id).build();
        }
    }

    @POST
    @Consumes({ MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML })
    @Produces(MediaType.TEXT_PLAIN)
    public Response createSong(Song song) {
        if (song.getTitle() == null || song.getAlbum() == null || song.getArtist() == null || song.getReleased() == null
                || song.getTitle().equals("") || song.getAlbum().equals("") || song.getArtist().equals(""))
        {
            return Response.status(Response.Status.BAD_REQUEST).build();
        }
        else {
            int id = SongStorage.getInstance().addSong(song);
            return Response.status(Response.Status.CREATED).entity(id).build();
        }
    }

    @PUT
    @Consumes({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
    @Path("/{id}")
    public Response updateSong(@PathParam("id") Integer id, Song song) {

        //if id exists

        if (song.getTitle() == null || song.getAlbum() == null || song.getArtist() == null || song.getReleased() == null
                || song.getTitle().equals("") || song.getAlbum().equals("") || song.getArtist().equals(""))
        {
            return Response.status(Response.Status.BAD_REQUEST).build();
        }
        else {
            boolean success = SongStorage.getInstance().updateSong(song, id);
            if (success)
                return Response.status(Response.Status.NO_CONTENT).build();
            else
                return Response.status(Response.Status.NOT_FOUND).build();

        }
    }

    @DELETE
    @Path("/{id}")
    public Response delete(@PathParam("id") Integer id) {
        boolean deleted = SongStorage.getInstance().deleteSong(id);
        if (deleted)
            return Response.status(Response.Status.NO_CONTENT).build();
        else
            return Response.status(Response.Status.NOT_FOUND).build();
    }

}

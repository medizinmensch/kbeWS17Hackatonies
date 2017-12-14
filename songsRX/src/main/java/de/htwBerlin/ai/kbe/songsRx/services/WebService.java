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


//7 units tests für put (5): jeweils für json und xml 204 & 404 und delete, id existiert nicht
// delete(2): (204 und 404)
//id existiert nicht in db -> 404
    /* wenn id nicht übereinstimmt die aus der url nehmen, nicht id die aus der payload kommt -> id in der payload ändern

    */

@Path("/songs")
public class WebService {

    @GET
    @Produces({ MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML })
    public Collection<Song> getAllSongs() {
        return SongStorage.getInstance().getAllSongs();
    }

    @GET
    @Path("/{id}")
    @Produces({ MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML })
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
        //todo gibts schon oder hinzugefügt
        return Response.status(Response.Status.CREATED).entity(SongStorage.getInstance().addSong(song)).build();
    }

    @PUT
    @Consumes({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
    @Path("/{id}")
    public Response updateSong(@PathParam("id") Integer id, Song song) {
        return Response.status(Response.Status.NO_CONTENT).entity(SongStorage.getInstance().updateSong(song, id)).build();
    }

    @DELETE
    @Path("/{id}")
    public Response delete(@PathParam("id") Integer id) {
        return Response.status(Response.Status.NO_CONTENT).entity(SongStorage.getInstance().deleteSong(id)).build();
    }

}

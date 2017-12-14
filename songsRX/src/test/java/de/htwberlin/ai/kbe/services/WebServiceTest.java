package de.htwberlin.ai.kbe.services;

import static org.junit.Assert.*;

import javax.ws.rs.core.MediaType;

import javax.ws.rs.client.Entity;
import javax.ws.rs.core.Response;

import org.glassfish.jersey.server.ResourceConfig;
import org.glassfish.jersey.test.JerseyTest;
import org.junit.Test;

import de.htwBerlin.ai.kbe.songsRx.beans.Song;
import de.htwBerlin.ai.kbe.songsRx.beans.Song.Builder;
import de.htwBerlin.ai.kbe.songsRx.services.WebService;

/* 
 * 7 units tests für put (5): jeweils für json und xml 204 & 404 und delete, id existiert nicht
 * delete(2): (204 und 404)
 * id existiert nicht in db -> 404
 * wenn id nicht übereinstimmt die aus der url nehmen, nicht id die aus der payload kommt -> id in der payload ändern
 */


public class WebServiceTest extends JerseyTest{

    @Override
    protected javax.ws.rs.core.Application configure() {
        return new ResourceConfig(WebService.class);
    }

    
    // PUT 2XX
    @Test // working XML Update
    public void doPutUpdateSongWithXmlShouldReturn204() {
    	Song mySong = new Song(1, "Title", "artist", "album", 2000);
        Response output = target("/songs/1")
                .request()
                .put(Entity.entity(mySong, MediaType.APPLICATION_JSON));
        assertEquals(204, output.getStatus());
    }
    
    @Test // working JSON Update
    public void doPutUpdateSongWithJsonShouldReturn204() {
    	Song mySong = new Song(1, "Title", "artist", "album", 2000);
        Response output = target("/songs/1")
                .request()
                .put(Entity.entity(mySong, MediaType.APPLICATION_XML));
        assertEquals(204, output.getStatus());
    }
    
    // PUT 4XX
    @Test // ID not found
    public void doPutUpdateSongWithNonExistingIdShouldReturn404() {
    	Song mySong = new Song(-5, "Title", "artist", "album", 2000);
        Response output = target("/songs/-5")
                .request()
                .put(Entity.entity(mySong, MediaType.APPLICATION_XML));
        
        assertEquals(404, output.getStatus());
    }
    
    @Test
    public void doPutUpdateSongWithInvalidFormatShouldReturn400() {
    	Song mySong = new Song(1, "Title", "artist", "album", 2000);
        Response output = target("/songs/1")
                .request()
                .put(Entity.entity(mySong, MediaType.TEXT_HTML));
        
        assertEquals(415, output.getStatus());
    }
    
    // PUT MISC
    @Test // Id in Uri must override ID in json file -> Check with get
    public void doPutUpdateSongWithExistingIdShouldOverrideJsonId() {
    	Song songToSend = new Song(43, "Title", "artist", "album", 2000);
    	
    	System.out.println("1");
    	
       target("/songs/42")
        .request()
        .put(Entity.entity(songToSend, MediaType.APPLICATION_JSON));
       
       System.out.println("2");
       
        Song responseSong = target("/songs/42")
        		.request(MediaType.APPLICATION_JSON)
        		.get(Song.class);
        
        System.out.println("3");
        
        Song changedSong = new Song(42, "Title", "artist", "album", 2000);
        
        System.out.println("4");
        
        System.out.println(responseSong.toString());
        System.out.println(changedSong.toString());
        assertEquals(responseSong, changedSong);
    }
    
    // DELETE
    @Test // Working Delete
    public void doDeleteExistingIdShouldReturn204() {
    	
    	Response response = target("/songs/73")
    			.request()
    			.delete();
    	
    	assertEquals(204, response.getStatus());
    	
    }
    
    @Test // BadRequest Delete
    public void doDeleteNonExistingIdShouldReturn404() {
    	
    	Response response = target("/songs/-1")
    			.request()
    			.delete();
    	
    	assertEquals(404, response.getStatus());
    }
    
	
}

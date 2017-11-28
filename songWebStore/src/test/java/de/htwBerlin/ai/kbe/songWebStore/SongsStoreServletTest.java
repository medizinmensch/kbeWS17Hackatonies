package de.htwBerlin.ai.kbe.songWebStore;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;

import javax.servlet.ServletException;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.mock.web.MockServletConfig;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

public class SongsStoreServletTest {
	
    private SongsStoreServlet servlet;
    private MockServletConfig config;
    private MockHttpServletRequest request;
    private MockHttpServletResponse response;
    private ObjectMapper objectMapper;
    
    @Before
    public void setUp() throws ServletException {
        objectMapper = new ObjectMapper();

        servlet = new SongsStoreServlet();
        request = new MockHttpServletRequest();
        response = new MockHttpServletResponse();
        config = new MockServletConfig();
        config.addInitParameter("filename", "testSongs.json");
        servlet.init(config); //throws ServletException
    }
    
    @SuppressWarnings("unchecked")
	@Test
    public void playingWithJackson() throws IOException {
        // songs.json and testSongs.json contain songs from this Top-10:
        // http://time.com/collection-post/4577404/the-top-10-worst-songs/

        // Read a JSON file and create song list:
        InputStream input = this.getClass().getClassLoader().getResourceAsStream("testSongs.json");

        List<Song> songList = (List<Song>) objectMapper.readValue(input, new TypeReference<List<Song>>() {
        });

        // write a list of objects to a JSON-String with prettyPrinter
        String json = objectMapper.writerWithDefaultPrettyPrinter().writeValueAsString(songList);

        // Create a song and write to JSON
        Song song = new Song(11, "Ferryman - Serenity Mix", "Koan", "Serenity Side A.", 2017);
        byte[] jsonBytes = objectMapper.writeValueAsBytes(song);
        songList.add(song);

        // write a list of objects to an outputStream in JSON format
        objectMapper.writerWithDefaultPrettyPrinter().writeValue(new FileOutputStream("output.json"), songList);

        // Read JSON from byte[] into Object
        Song newSong = (Song) objectMapper.readValue(jsonBytes, Song.class);
        assertEquals(song.getTitle(), newSong.getTitle());
        assertEquals(song.getArtist(), newSong.getArtist());
    }
    
    @Test
    public void initShouldLoadSongList() {
        ConcurrentHashMap<Integer,Song> songStore = new ConcurrentHashMap<Integer, Song>();
        List<Song> songList;
        objectMapper = new ObjectMapper();
        InputStream input = this.getClass().getClassLoader().getResourceAsStream("testSongs.json");
        try {
            //read json into list
            songList = objectMapper.readValue(input, new TypeReference<List<Song>>() {
            });

            assertTrue(songList.size() != 0);
            //songList.contains()

        } catch (IOException e) {
            System.out.println("could not read json file");
        }
    }



	@Test
    public void doGetAllShouldReturnAllSongs() throws ServletException, IOException {
        request.setParameter("all");
        servlet.doGet(request, response);

        List<Song> songList = (List<Song>) objectMapper.readValue(response.getContentAsByteArray(), new TypeReference<List<Song>>() {
        });

        //assert with testSongList
    }

    @Test
    public void doGetSpecificIdShouldReturnOneSong() throws ServletException, IOException {
        request.setParameter("songId", "2");
        servlet.doGet(request, response);

        Song mySong = (Song) objectMapper.readValue(response.getContentAsByteArray(), new TypeReference<Song>() {
        });

        Song expected = new Song(2,"Mom","Meghan Trainor, Kelli Trainor","Thank You",2016);
        Assert.assertTrue(mySong.equals(expected));
    }

    @Test
    public void doGetMissingIdShouldReturnErrorMessage() throws ServletException, IOException {
        request.setParameter("songId", "-1");
        servlet.doGet(request, response);

        String expectedResponseMessage = "Unfortunately we could not find your song.";

        Assert.assertTrue(response.getContentAsString().contains(expectedResponseMessage));
    }

    @Test
    public void doGetNoIdShouldReturnErrorMessage() throws ServletException, IOException {
        request.setParameter("songId", "");
        servlet.doGet(request, response);

        String expectedResponseMessage = "Your Parameter \"songId\" did not contain an Id.";

        Assert.assertTrue(response.getContentAsString().contains(expectedResponseMessage));
    }


    
    @Test
    public void doPostShouldSetNewEntry() throws ServletException, IOException {
        //give test json as input
        InputStream input = this.getClass().getClassLoader().getResourceAsStream("testSong.json");
        Song mySong = objectMapper.readValue(input, new TypeReference<Song>() {
        });
        request.setContent(objectMapper.writeValueAsBytes(mySong));

        servlet.doPost(request, response);

        //is this sufficiently as test?
        String responseString = response.getContentAsString();
        Assert.assertTrue(responseString.contains("Added Song"));
        Assert.assertTrue(responseString.contains("OMG"));

        //we could also doGet to get total songList and then check if that list contains the new added song

    }

    @Test
    public void doPostWithEmptyValueShouldReturnErrorMessage() throws ServletException, IOException {
        InputStream input = this.getClass().getClassLoader().getResourceAsStream("testSongWithEmptyValue.json");
        Song mySong = objectMapper.readValue(input, new TypeReference<Song>() {
        });
        request.setContent(objectMapper.writeValueAsBytes(mySong));

        servlet.doPost(request, response);

        //is this sufficiently as test?
        String expectedResponseString = "One or more of your song attributes were empty.";
        String responseString = response.getContentAsString();
        Assert.assertTrue(responseString.contains(expectedResponseString));
    }

    @Test
    public void doPostWithMissingAttributeShouldReturnErrorMessage() throws ServletException, IOException {
        InputStream input = this.getClass().getClassLoader().getResourceAsStream("testSongMissingAttribute.json");
        Song mySong = objectMapper.readValue(input, new TypeReference<Song>() {
        });
        request.setContent(objectMapper.writeValueAsBytes(mySong));

        servlet.doPost(request, response);

        //is this sufficiently as test?
        String expectedResponseString = "You did not gave all the necessary information for your song.";
        String responseString = response.getContentAsString();
        Assert.assertTrue(responseString.contains(expectedResponseString));
    }
}

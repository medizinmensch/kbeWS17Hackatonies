package de.htwBerlin.ai.kbe.songWebStore;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.net.URL;
import java.util.Enumeration;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.annotation.WebInitParam;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

/* debugging options
export JPDA_ADDRESS=9999
export JPDA_TRANSPORT=dt_socket
bin/catalina.sh jpda start
 */

@WebServlet(
	name = "SongsServlet", 
	urlPatterns = "/*",
	initParams = {
			@WebInitParam(name="filename",
					value="songs.json")
	}
)
public class SongsStoreServlet extends HttpServlet {

	private static final long serialVersionUID = 1L;
	
	private static final String APPLICATION_JSON = "application/json";
	
	private static final String TEXT_PLAIN = "text/plain";
	
    private String songFilename = null;
	
    private Map<Integer, Song> songStore = null;

	private ObjectMapper objectMapper;

	private List<Song> songList;

    private AtomicInteger currentID = new AtomicInteger();

    private URL jsonSavePath;

    private Song recommendedSong = new Song(0, "Shivaya", "Spirit Architect", "Moonshine", 2017);

	// load songStore from JSON file and set currentID
	public void init(ServletConfig servletConfig) throws ServletException {
		songStore = new ConcurrentHashMap<Integer, Song>();
		String jsonFile = servletConfig.getInitParameter("filename");
		objectMapper = new ObjectMapper();
		InputStream input = this.getClass().getClassLoader().getResourceAsStream(jsonFile);
		jsonSavePath = this.getClass().getClassLoader().getResource(jsonFile);
		try {
			songList = objectMapper.readValue(input, new TypeReference<List<Song>>() {
			});
		} catch (IOException e) {
			System.out.println("could not read json file.");
		}

		for (Song s: songList) {
			songStore.put(s.getId(), s);
		}
		currentID.set(songStore.size());
	}

    
	@Override
	public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
		//parameter?all -> give all songs
		//parameter?songId=6 -> give single Song with id=6

		StringBuilder responseStr = new StringBuilder("");
		// alle Parameter (keys)
		Enumeration<String> paramNames = request.getParameterNames();
		
		String param;
		String paramValue;
		while (paramNames.hasMoreElements()) {
			param = paramNames.nextElement();
			paramValue = request.getParameter(param);
			if (param.equals("all")) {
				responseStr.append(objectMapper.writerWithDefaultPrettyPrinter().writeValueAsString(songStore.values()));
                response.setHeader("Accept", APPLICATION_JSON);
                break;
			}
			else if (param.equals("songId")) {
				if (paramValue.equals("")) {
					responseStr.append("Your Parameter \"songId\" did not contain an Id.\n");
					responseStr.append("Our Database contains Songs until Id: ");
					responseStr.append(songStore.size());
				}
				else {
					int paramValueInt = Integer.parseInt(paramValue);
					if (songStore.containsKey(paramValueInt)) {
					    response.setHeader("Accept", APPLICATION_JSON);
						Song mySong = (Song) songStore.get(paramValueInt);
						System.out.println(mySong.getId() + ", " + mySong.getTitle());
						responseStr.append(objectMapper.writerWithDefaultPrettyPrinter().writeValueAsString(mySong));
					}
					else {
						//songId does not exist
						responseStr.append("Unfortunately we could not find your song.\n");
                        responseStr.append("Our Database contains Songs until Id: ");
                        responseStr.append(songStore.size());
						responseStr.append("\n\nBut what about this song?:\n\n");
						responseStr.append(objectMapper.writerWithDefaultPrettyPrinter().writeValueAsString(recommendedSong));
					}
				}
				break;
			}
		}

		//write string
		try (PrintWriter out = response.getWriter()) {
			out.println(responseStr.toString());
		}
	}

	
	@Override
	public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		//insert payload which contains new song into database

		StringBuilder responseStr = new StringBuilder("");
		InputStream inputStream = request.getInputStream();
		Song newSong = (Song) objectMapper.readValue(inputStream, new TypeReference<Song>() {
		});

		try {
            //test for valid entries: title, artist, album, released
            if (!newSong.getTitle().equals("") && !newSong.getAlbum().equals("") && !newSong.getArtist().equals("") && newSong.getReleased() != 0) {
                //add song to database
                newSong.setId(currentID.incrementAndGet());
                songStore.put(currentID.get(), newSong);
                responseStr.append("Added Song \"");
                responseStr.append(newSong.getTitle());
                responseStr.append("\" to Database with Id: ");
                responseStr.append(currentID.get());
            }
            else {
                responseStr.append("One or more of your song attributes were empty.");
            }
        } catch (NullPointerException e) {
		    responseStr.append("You did not gave all the necessary information for your song.\n");
		    responseStr.append("Please give the following parameters for your song as json format:\n");
		    responseStr.append("title, artist, album & released (year)");
        }


		try (PrintWriter out = response.getWriter()) {
			out.println(responseStr);
		}
	}
	
//	@Override
//	public void doPut(HttpServletRequest request, HttpServletResponse response) throws IOException {
//	}	
	
//	@Override
//	public void doDelete(HttpServletRequest request, HttpServletResponse response) throws IOException {
//	}

	@Override
	public void destroy() {
		//save songStore(HashMap) to songs.json
		try {
			FileOutputStream stream = new FileOutputStream(jsonSavePath.getPath());
			objectMapper.writerWithDefaultPrettyPrinter().writeValue(stream, songStore.values());
		}
		catch (IOException e) {
			System.out.println("songs.json could not be found. songs was not saved.");
			e.printStackTrace();
		}
	}
}

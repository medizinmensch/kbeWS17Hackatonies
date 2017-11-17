package de.htwBerlin.ai.kbe.songWebStore;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.OptionalInt;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletInputStream;
import javax.servlet.annotation.WebServlet;
import javax.servlet.annotation.WebInitParam;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.io.IOUtils;

import com.fasterxml.jackson.core.JsonGenerationException;
import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;



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

	//todo should be atomic
    private AtomicInteger currentID = new AtomicInteger();

    private Song recommendedSong = new Song(0, "Shivaya", "Spirit Architect", "Moonshine", 2017);

	// load songStore from JSON file and set currentID
	public void init(ServletConfig servletConfig) throws ServletException {
		System.out.println("In init");
		songStore = new ConcurrentHashMap<Integer, Song>();
		String jsonFile = servletConfig.getInitParameter("filename");
		objectMapper = new ObjectMapper();
		InputStream input = this.getClass().getClassLoader().getResourceAsStream(jsonFile);
		try {
			songList = objectMapper.readValue(input, new TypeReference<List<Song>>() {
			});
		} catch (IOException e) {
			System.out.println("bla");
		}

		for (Song s: songList) {
			songStore.put(s.getId(), s);
		}
		currentID.set(songStore.size());
	}

    
	@Override
	public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
		//parameter?all -> give all songs

		//parameter?songId=6
		//nicht vorhanden, kein value
		//wenn jackson payload nicht lesen kann, bescheid sagen


		StringBuilder responseStr = new StringBuilder("");
		// alle Parameter (keys)
		Enumeration<String> paramNames = request.getParameterNames();

		String param = "";
		String paramValue = "";
		while (paramNames.hasMoreElements()) {
			param = paramNames.nextElement();
			paramValue = request.getParameter(param);
			if (param.equals("all")) {
				responseStr.append(objectMapper.writerWithDefaultPrettyPrinter().writeValueAsString(songStore));
			}
			else if (param.equals("songId")) {
				int paramValueInt = Integer.parseInt(paramValue);
				if (paramValue.equals("")) {
					//todo do different: getting error 500, inetrnal server error, catch numberformatexception
					responseStr.append("Ihr Parameter enthielt keine Id.\n");
					responseStr.append("Die Datenbank enthält Songs bis zur Id: ");
					responseStr.append(songStore.size());
				}
				else if (songStore.containsKey(paramValueInt)) {
					Song mySong = (Song) songStore.get(paramValueInt);
					System.out.println(mySong.getId() + ", " + mySong.getTitle());
					responseStr.append(objectMapper.writerWithDefaultPrettyPrinter().writeValueAsString(mySong));
				}
				else {
					//songId nicht vorhanden
					responseStr.append("Wir konnten leider den Song zu Ihrer SongId nicht finden.\n");
					responseStr.append("Probieren sie doch stattdessen diesen Song:\n\n");
					responseStr.append(objectMapper.writerWithDefaultPrettyPrinter().writeValueAsString(recommendedSong));
				}

			}
		}

		//write json
		try (PrintWriter out = response.getWriter()) {
			out.println(responseStr.toString());
		}
	}

	
	@Override
	public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		//insert payload which contains new song into database

		//check if it has right values: can be casted into song, no values are empty
		//else give format as response that user has to use
		//generate id

		StringBuilder responseStr = new StringBuilder("");

		//todo try catch this
		InputStream inputStream = request.getInputStream();
		Song mySong = (Song) objectMapper.readValue(inputStream, new TypeReference<Song>() {
		});
		if (songStore.containsValue(mySong)) {
			//song already in database
			responseStr.append("Sorry. Song is already in our Database.");
		}
		else {
			//add song to database
			mySong.setId(currentID.incrementAndGet());
			songStore.put(currentID.get(), mySong);
			responseStr.append("Added Song \"");
			responseStr.append(mySong.getTitle());
			responseStr.append("\", Id: ");
			responseStr.append(currentID.get());
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
	
	// save songStore to file
	@Override
	public void destroy() {
		System.out.println("In destroy");
		//todo concurrent hash map speichern in json file
		//achtung: nur die values der hashmap abspeichern, nicht die keys
	}
}

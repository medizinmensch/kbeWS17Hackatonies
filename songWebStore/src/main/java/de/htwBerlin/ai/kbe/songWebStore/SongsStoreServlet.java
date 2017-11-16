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

	// load songStore from JSON file and set currentID
	public void init(ServletConfig servletConfig) throws ServletException {
		System.out.println("In init");
		songStore = new ConcurrentHashMap<Integer, Song>();
		String jsonFile = servletConfig.getInitParameter("filename");
		objectMapper = new ObjectMapper();
		InputStream input = this.getClass().getClassLoader().getResourceAsStream(jsonFile);
		try {
			songList = (List<Song>) objectMapper.readValue(input, new TypeReference<List<Song>>() {
			});
		} catch (IOException e) {
			System.out.println("bla");
		}

		for (Song s: songList) {
			songStore.put(s.getId(), s);
		}
	}

    
	@Override
	public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
		//parameter?all

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
				Enumeration headerNames = request.getHeaderNames();
				while (headerNames.hasMoreElements()) {
					String key = (String) headerNames.nextElement();
					responseStr.append(key + "=");
					String value2 = request.getHeader(key);
					responseStr.append(value2 + "\n");
				}
			}
			else if (param.equals("songId")) {
				Enumeration headerNames = request.getHeaderNames();
				while (headerNames.hasMoreElements()) {
					String headerName = headerNames.nextElement().toString();
					if (headerName.equals(paramValue)) {
						responseStr.append(headerName + "=");
						String value2 = request.getHeader(headerName);
						responseStr.append(value2 + "\n");
					}

				}
			}
		}

		//write json
	}

	
//	@Override
//	public void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
//	}
	
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
		//concurrent hash map speichern in json file
	}
}

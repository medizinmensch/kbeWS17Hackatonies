package de.htwBerlin.ai.kbe.songsRx.models;

import java.io.IOException;
import java.util.Map;

import javax.ws.rs.container.ContainerRequestContext;
import javax.ws.rs.container.ContainerRequestFilter;
import javax.ws.rs.container.PreMatching;
import javax.ws.rs.core.Response;
import javax.ws.rs.ext.Provider;

import de.htwBerlin.ai.kbe.songsRx.beans.User;

@Provider
@PreMatching
public class Filter extends Servlet {
	
	//Hashmap einlesen
	private static Map<String, String> tokens;
	
	public void filter(ContainerRequestContext ctx) throws IOException{
		String token = ctx.getHeaderString("Authorization");
		
		if(token.equals("")) {
			// 401
		}
		else if(!tokens.containsValue(token)) {
			// 401 
		}
		else {
			// success - optional...
		}
		
		/*
		 * TODO Auth-Header aus Request hole und mit Hashmap vergleichen
		 * wenn leer / nicht vorhanden :401; sonst nichts
		 * 
		 */
	}
	
}

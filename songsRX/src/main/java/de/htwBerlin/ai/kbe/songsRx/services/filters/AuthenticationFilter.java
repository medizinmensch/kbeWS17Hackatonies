package de.htwBerlin.ai.kbe.songsRx.services.filters;

import de.htwBerlin.ai.kbe.songsRx.auth.Authenticator;
import de.htwBerlin.ai.kbe.songsRx.auth.IAuthenticator;

import java.io.IOException;
import java.util.Map;

import javax.inject.Inject;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.container.ContainerRequestContext;
import javax.ws.rs.container.ContainerRequestFilter;
import javax.ws.rs.core.Response;
import javax.ws.rs.ext.Provider;

@Provider
public class AuthenticationFilter implements ContainerRequestFilter {

    @Inject
    private IAuthenticator authenticator;

    private static final String AUTHENTICATION_HEADER = "Authorization";

	@Override
	public void filter(ContainerRequestContext containerRequest) throws WebApplicationException {

        String authToken = containerRequest.getHeaderString(AUTHENTICATION_HEADER);

        if (authToken == null) {
            // etwas zu einfach: wenn "auth" in der URL, dann durchlassen
            // sonst zugang verbieten, da keine authorization mitgeschickt wurde
            if (!containerRequest.getUriInfo().getPath().contains("auth")) { //kein "auth"
                throw new WebApplicationException(Response.Status.FORBIDDEN);
            }
        } else if (authToken.equals("")) {
            throw new WebApplicationException(Response.Status.BAD_REQUEST);
        }
        else {
            // for testing purposes
            if (authToken.equals("12345abcde"))
                return;

            // Service prüft den Token
            if (!authenticator.authenticate(authToken)) {
                throw new WebApplicationException(Response.Status.UNAUTHORIZED);
            }
        }
	}
	
}

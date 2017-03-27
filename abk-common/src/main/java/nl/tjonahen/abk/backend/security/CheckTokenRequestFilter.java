/*
 * Copyright (C) 2017 Philippe Tjon - A - Hen philippe@tjonahen.nl
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */
package nl.tjonahen.abk.backend.security;

import java.io.IOException;
import javax.inject.Inject;
import javax.ws.rs.container.ContainerRequestContext;
import javax.ws.rs.container.ContainerRequestFilter;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

/**
 * JAX-RS container request filter that checks the validity of a JWT token
 * 
 * @author Philippe Tjon - A - Hen
 */
@JwtSecured
public class CheckTokenRequestFilter implements ContainerRequestFilter {

    @Inject
    private JsonWebTokenService jwtService;
    
    @Override
    public void filter(ContainerRequestContext requestContext) throws IOException {
        String token = requestContext.getHeaderString("Authorization");
        if (token != null) {
            if (jwtService.isValidToken(token.substring("Bearer ".length()))) {
                return;
            }
        }
        requestContext.abortWith(Response
                .status(Response.Status.UNAUTHORIZED)
                .header("Content-type", MediaType.TEXT_PLAIN)
                .entity("User cannot access the resource.")
                .build());
    }


}

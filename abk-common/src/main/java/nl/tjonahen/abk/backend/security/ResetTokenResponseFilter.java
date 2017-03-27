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
import java.util.logging.Logger;
import javax.inject.Inject;
import javax.ws.rs.container.ContainerRequestContext;
import javax.ws.rs.container.ContainerResponseContext;
import javax.ws.rs.container.ContainerResponseFilter;

/**
 * JAX-RS response filter to update the JWT with a new expiration date.
 * @author Philippe Tjon - A - Hen
 */
@JwtSecured
public class ResetTokenResponseFilter implements ContainerResponseFilter {

    private final static Logger LOGGER = Logger.getLogger(ResetTokenResponseFilter.class.getName());

    @Inject
    private JsonWebTokenService JwtService;
    
    @Override
    public void filter(ContainerRequestContext requestContext, ContainerResponseContext responseContext) throws IOException {
        if (responseContext.getHeaderString("Authorization") == null) {
// only reset if there is no header on response set by application
            String token = requestContext.getHeaderString("Authorization");
            if (token != null) {
                LOGGER.fine(() -> "Reset JWT token");
                String newToken = JwtService.refreshToken(token.substring("Bearer ".length()));
                responseContext.getHeaders().putSingle("Authorization", "Bearer " + newToken);
            }
        }
    }


}

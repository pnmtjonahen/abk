/*
 * Copyright (C) 2015 Philippe Tjon - A - Hen, philippe@tjonahen.nl
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
package nl.tjonahen.abk.backend;

import java.io.IOException;
import java.util.logging.Logger;
import javax.ws.rs.container.ContainerRequestContext;
import javax.ws.rs.container.ContainerRequestFilter;
import javax.ws.rs.ext.Provider;

/**
 *
 * @author Philippe Tjon - A - Hen, philippe@tjonahen.nl
 */
@Provider
public class HttpHeaderLoggingFilter implements ContainerRequestFilter {

    private static final Logger LOGGER = Logger.getLogger(HttpHeaderLoggingFilter.class.getName());

    @Override
    public void filter(ContainerRequestContext requestContext) throws IOException {

        LOGGER.info(() -> {
                StringBuilder sb = new StringBuilder();
                sb.append("Request headers\n");
                sb.append(requestContext.getMethod());
                sb.append(" ");
                sb.append(requestContext.getUriInfo().getRequestUri().toASCIIString());
                sb.append("\n");
                requestContext.getHeaders().forEach((k, v) -> {
                    sb.append(k);
                    sb.append(" : ");
                    sb.append(v.stream().reduce("", (r, s) -> "".equals(r) ? r + s : r + "," + s));
                    sb.append("\n");

                });
                return sb.toString();
            }
        );
    }

}

/*
 * Copyright (C) 2016 Philippe Tjon - A - Hen, philippe@tjonahen.nl
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
import java.net.URI;
import java.net.URISyntaxException;
import javax.ws.rs.container.ContainerRequestContext;
import javax.ws.rs.core.MultivaluedHashMap;
import javax.ws.rs.core.UriInfo;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

/**
 *
 * @author Philippe Tjon - A - Hen, philippe@tjonahen.nl
 */
public class HttpHeaderLoggingFilterTest {

    @Mock
    private ContainerRequestContext requestContext;

    @Mock
    private UriInfo uriInfo;
           
    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
    }
    
    /**
     * Test of filter method, of class HttpHeaderLoggingFilter.
     */
    @Test
    public void testFilter() throws IOException, URISyntaxException  {
        HttpHeaderLoggingFilter instance = new HttpHeaderLoggingFilter();
        Mockito.when(requestContext.getMethod()).thenReturn("GET");
        Mockito.when(requestContext.getUriInfo()).thenReturn(uriInfo);
        Mockito.when(uriInfo.getRequestUri()).thenReturn(new URI("http://sample.nl/sample"));
        final MultivaluedHashMap<String, String> multivaluedHashMap = new MultivaluedHashMap<>();
        multivaluedHashMap.add("header", "sample");
        Mockito.when(requestContext.getHeaders()).thenReturn(multivaluedHashMap);

        instance.filter(requestContext);
    }

}

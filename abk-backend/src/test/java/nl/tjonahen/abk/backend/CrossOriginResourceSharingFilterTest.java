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

import javax.ws.rs.container.ContainerRequestContext;
import javax.ws.rs.container.ContainerResponseContext;
import javax.ws.rs.core.MultivaluedHashMap;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

/**
 *
 * @author Philippe Tjon - A - Hen, philippe@tjonahen.nl
 */
public class CrossOriginResourceSharingFilterTest {
    

    @Mock
    private ContainerRequestContext requestContext;
    
    @Mock
    private ContainerResponseContext response;
            
            
    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
    }        
    /**
     * Test of filter method, of class CrossOriginResourceSharingFilter.
     */
    @Test
    public void testFilter() {
        CrossOriginResourceSharingFilter instance = new CrossOriginResourceSharingFilter();
        final MultivaluedHashMap<String, Object> multivaluedHashMap = new MultivaluedHashMap<>();
        Mockito.when(response.getHeaders()).thenReturn(multivaluedHashMap);
        instance.filter(requestContext, response);
        
        Assert.assertTrue(multivaluedHashMap.containsKey("Access-Control-Allow-Origin"));
        Assert.assertTrue(multivaluedHashMap.containsKey("Access-Control-Allow-Methods"));
        Assert.assertTrue(multivaluedHashMap.containsKey("Access-Control-Allow-Headers"));

        Assert.assertEquals("*", multivaluedHashMap.get("Access-Control-Allow-Origin").get(0));
        Assert.assertEquals("OPTIONS, GET, POST, PUT, DELETE", multivaluedHashMap.get("Access-Control-Allow-Methods").get(0));
        Assert.assertEquals("Content-Type", multivaluedHashMap.get("Access-Control-Allow-Headers").get(0));
    }
    
}

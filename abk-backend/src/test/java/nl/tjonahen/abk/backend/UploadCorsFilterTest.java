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

import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.http.HttpServletResponse;
import org.junit.Test;
import org.junit.Before;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

/**
 *
 * @author Philippe Tjon - A - Hen, philippe@tjonahen.nl
 */
public class UploadCorsFilterTest {

    @Mock
    private FilterConfig filterConfig;

    @Mock
    private HttpServletResponse servletResponse;
   
    @Mock
    private FilterChain filterChain;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
    }

    /**
     * Test of init method, of class UploadCorsFilter.
     */
    @Test
    public void testInit() throws Exception {
        UploadCorsFilter instance = new UploadCorsFilter();
        instance.init(filterConfig);
    }

    /**
     * Test of doFilter method, of class UploadCorsFilter.
     */
    @Test
    public void testDoFilter() throws Exception {
        System.out.println("doFilter");
        UploadCorsFilter instance = new UploadCorsFilter();
        instance.doFilter(null, servletResponse, filterChain);
        Mockito.verify(servletResponse).setHeader("Access-Control-Allow-Origin", "*");
        Mockito.verify(servletResponse).setHeader("Access-Control-Allow-Credentials", "true");
        Mockito.verify(servletResponse).setHeader("Access-Control-Allow-Methods", "POST, GET, HEAD, OPTIONS");
        Mockito.verify(servletResponse).setHeader("Access-Control-Allow-Headers", "Origin, Accept, X-Requested-With, Content-Type, Access-Control-Request-Method, Access-Control-Request-Headers");

        Mockito.verify(filterChain).doFilter(null, servletResponse);
        
    }

    /**
     * Test of destroy method, of class UploadCorsFilter.
     */
    @Test
    public void testDestroy() {
        UploadCorsFilter instance = new UploadCorsFilter();
        instance.destroy();
    }

}

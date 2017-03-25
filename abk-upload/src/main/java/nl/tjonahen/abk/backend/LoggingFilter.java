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
package nl.tjonahen.abk.backend;

import java.io.IOException;
import java.util.Arrays;
import java.util.function.Supplier;
import java.util.logging.Logger;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.ws.rs.core.Response;
import org.jboss.weld.environment.util.Collections;

/**
 *
 * @author Philippe Tjon - A - Hen
 */
@WebFilter(filterName = "LoggingServletFilter", urlPatterns = {"/*"})
public class LoggingFilter implements Filter {

    private final static Logger LOGGER = Logger.getLogger(LoggingFilter.class.getName());

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        LOGGER.info(() -> {
            final HttpServletRequest httpServletRequest = (HttpServletRequest) request;
            final StringBuilder sb = new StringBuilder();
            
            sb.append("Request:\nurl     : ").append(httpServletRequest.getContextPath());
            sb.append("\nMethod  : ").append(httpServletRequest.getMethod());
            sb.append("\nHeaders :\n");
            Collections.asList(httpServletRequest.getHeaderNames()).forEach((n) -> {
                sb.append(n).append(" : ").append(httpServletRequest.getHeader(n)).append("\n");
            });
            if (httpServletRequest.getCookies() != null) {
                sb.append("\nCookies :\n");
                Arrays.asList(httpServletRequest.getCookies()).forEach((c) -> {
                    sb.append(c.getName()).append(" : ").append(c.getValue()).append("\n");
                });
            }
            return sb.toString();
        });
        try {
            chain.doFilter(request, response);
        } finally {
            LOGGER.info(() -> {
                final HttpServletResponse httpServletResponse = (HttpServletResponse) response;
                final HttpServletRequest httpServletRequest = (HttpServletRequest) request;
                final StringBuilder sb = new StringBuilder();
                sb.append("Response:\nurl     : ").append(httpServletRequest.getContextPath());
                sb.append("\nMethod  : ").append(httpServletRequest.getMethod());
                sb.append("\nStatus  : ").append(httpServletResponse.getStatus())
                        .append(" ")
                        .append(Response.Status.fromStatusCode(httpServletResponse.getStatus()));
                sb.append("\nHeaders :\n");
                httpServletResponse.getHeaderNames().forEach((n) -> {
                    sb.append(n).append(" : ").append(httpServletResponse.getHeader(n)).append("\n");
                });
                return sb.toString();
            });
        }
    }

    @Override
    public void destroy() {
    }

}

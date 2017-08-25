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
package nl.tjonahen.abk.backend.boundry.user;

import com.wordnik.swagger.annotations.Api;
import java.io.UnsupportedEncodingException;
import java.util.List;
import java.util.stream.Collectors;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.GenericEntity;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import nl.tjonahen.abk.backend.entity.Gebruiker;
import nl.tjonahen.abk.backend.model.User;
import nl.tjonahen.abk.backend.security.JwtSecured;
import nl.tjonahen.abk.backend.security.JsonWebTokenService;

@Api(value = "User resources")
@Path("/")
public class UserResource {

    @PersistenceContext
    private EntityManager entityManager;

    @Inject
    private JsonWebTokenService jwtService;

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @JwtSecured
    public Response getAllUsers() {
        final GenericEntity<List<String>> genericEntity
                = new GenericEntity<List<String>>(entityManager
                        .createNamedQuery("Gebruiker.findAll", Gebruiker.class)
                        .getResultList()
                        .stream()
                        .map(g -> g.getNaam())
                        .collect(Collectors.toList())) {
        };
        return Response.ok(genericEntity).build();
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @JwtSecured
    @Path("/check")
    public Response check() {
        return Response.ok().build();
    }

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    @Path("/login")
    public Response login(final User user) throws IllegalArgumentException, UnsupportedEncodingException {
        final List<Gebruiker> gebruikers = entityManager.createNamedQuery("Gebruiker.find", Gebruiker.class)
                .setParameter("naam", user.getUsername())
                .setParameter("wachtwoord", user.getPassword())
                .getResultList();
        if (gebruikers.isEmpty()) {
            return Response.status(Response.Status.UNAUTHORIZED).build();
        }
        final String token = jwtService.createToken(gebruikers.get(0).getNaam());
        if (token == null) {
            return Response.status(Response.Status.UNAUTHORIZED).build();
        }
        return Response.ok().header("Authorization", "Bearer " + token).build();
    }

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    @Path("/create")
    @Transactional
    public Response create(User user) throws IllegalArgumentException, UnsupportedEncodingException {
        Gebruiker gebruiker = new Gebruiker();
        gebruiker.setNaam(user.getUsername());
        gebruiker.setWachtwoord(user.getPassword());
        entityManager.persist(gebruiker);
        entityManager.flush();

        final String token = jwtService.createToken(gebruiker.getNaam());
        if (token != null) {
            return Response.ok().header("Authorization", "Bearer " + token).build();
        }
        return Response.status(Response.Status.FORBIDDEN).build();
    }

}

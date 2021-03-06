/*
 * Copyright (C) 2014 Philippe Tjon - A - Hen, philippe@tjonahen.nl
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
package nl.tjonahen.abk.backend.boundry.costcenter;

import com.wordnik.swagger.annotations.Api;
import com.wordnik.swagger.annotations.ApiOperation;
import com.wordnik.swagger.annotations.ApiParam;
import com.wordnik.swagger.annotations.ApiResponse;
import com.wordnik.swagger.annotations.ApiResponses;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.DefaultValue;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;
import nl.tjonahen.abk.backend.model.CostCenter;
import nl.tjonahen.abk.backend.model.CostCenters;
import nl.tjonahen.abk.backend.model.HRef;
import nl.tjonahen.abk.backend.entity.Kostenplaats;
import nl.tjonahen.abk.backend.security.JwtSecured;

/**
 *
 * @author Philippe Tjon - A - Hen, philippe@tjonahen.nl
 */
@Api(value = "Cost centers resource.")
@Path("/")
public class CostCentersResource {

    @PersistenceContext
    private EntityManager entityManager;

    @ApiOperation(value = "Get list of all cost centers", response = CostCenters.class)
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @JwtSecured
    public CostCenters get(@Context UriInfo uriInfo,
            @ApiParam(value = "Level (integer) to expand the result", name = "expand")
            @DefaultValue("0")
            @QueryParam("expand") int expand) {
        return this.get(expand).updateHref(uriInfo.getAbsolutePath().toString());
    }

    @ApiOperation(value = "Get a cost center by id", response = CostCenter.class)
    @ApiResponses(
            @ApiResponse(code = 404, message = "cost center not found")
    )
    @GET
    @Path("/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    @JwtSecured
    public Response get(@Context UriInfo uriInfo,
            @ApiParam(value = "id of the cost center", name = "id")
            @PathParam("id")
            final Long id,
            @ApiParam(value = "Level (integer) to expand the result", name = "expand")
            @DefaultValue("0") @QueryParam("expand") int expand) {
        Optional<CostCenter> optional = this.get(id, expand);
        if (optional.isPresent()) {
            return Response.ok(optional.get().updateHref(new HRef(uriInfo.getAbsolutePath().toString()).stripId()))
                    .build();
        }
        return Response.status(Response.Status.NOT_FOUND).build();
    }

    /**
     * Add new cost center to the collection of cost centers
     *
     * @param uriInfo -
     * @param costCenter -
     * @return -
     */
    @ApiOperation(value = "Add new cost center to the collection of cost centers", response = CostCenter.class)
    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Transactional
    @JwtSecured
    public Response post(@Context UriInfo uriInfo, CostCenter costCenter) {
        return Response.status(Response.Status.CREATED)
                .entity(this.create(costCenter).updateHref(uriInfo.getAbsolutePath().toString()))
                .build();
    }

    @ApiOperation(value = "Replace the collection of cost centers")
    @PUT
    @Consumes(MediaType.APPLICATION_JSON)
    @Transactional
    @JwtSecured
    public Response post(List<CostCenter> costcenters) {
        final EntityTransaction et = entityManager.getTransaction();
        et.begin();
        nullIds(costcenters);
        removeAll();
        insertAll(costcenters);
        et.commit();

        return Response.status(Response.Status.ACCEPTED).build();
    }

    private void nullIds(List<CostCenter> costcenters) {
        costcenters.stream().map(cc -> {
            cc.setId(null);
            return cc;
        }).filter(cc -> cc.getList() != null)
                .forEach(cc -> nullIds(cc.getList()));
    }

    @ApiOperation(value = "replace cost center defined by id")
    @ApiResponses(
            @ApiResponse(code = 304, message = "In case the cost center was not found")
    )
    @PUT
    @Path("/{id}")
    @Consumes(MediaType.APPLICATION_JSON)
    @JwtSecured
    public Response put(
            @ApiParam(value = "id of the cost center", name = "id")
            @PathParam("id")
            final Long id, CostCenter costCenter) {

        if (this.update(id, costCenter)) {
            return Response.ok().build();
        }
        return Response.status(Response.Status.NOT_MODIFIED).build();
    }

    @ApiOperation(value = "Delete cost center by id")
    @ApiResponses(
            @ApiResponse(code = 304, message = "In case the cost center was not found")
    )
    @DELETE
    @Path("/{id}")
    @JwtSecured
    public Response delete(
            @ApiParam(value = "id of the cost center", name = "id")
            @PathParam("id") final Long id) {
        if (this.remove(id)) {
            return Response.status(Response.Status.ACCEPTED).build();
        }
        return Response.status(Response.Status.NOT_MODIFIED).build();
    }

    private CostCenters get(int expand) {
        return new CostCenters(entityManager.createNamedQuery("Kostenplaats.findByRoot", Kostenplaats.class)
                .getResultList()
                .stream()
                .map(new ConvertCostCenter(expand)::convert)
                .collect(Collectors.toList()));
    }

    private Optional<CostCenter> get(Long id, int expand) {
        return entityManager.createNamedQuery("Kostenplaats.findById", Kostenplaats.class)
                .setParameter("id", id)
                .getResultList()
                .stream()
                .map(new ConvertCostCenter(expand)::convert)
                .findFirst();
    }

    private CostCenter create(CostCenter costCenter) {
        final Kostenplaats newKostenplaats = newKostenplaats(costCenter);
        final EntityTransaction et = entityManager.getTransaction();
        et.begin();
        entityManager.persist(newKostenplaats);
        entityManager.flush();
        entityManager.refresh(newKostenplaats);
        et.commit();
        return new ConvertCostCenter(1).convert(newKostenplaats);
    }

    private static Kostenplaats newKostenplaats(CostCenter costCenter) {
        final Kostenplaats kostenplaats = new Kostenplaats();
        kostenplaats.setFilter(costCenter.getFilter());
        kostenplaats.setNaam(costCenter.getName());
        return kostenplaats;
    }

    private boolean update(Long id, CostCenter costCenter) {
        final Kostenplaats current = entityManager.find(Kostenplaats.class, id);
        if (null == current) {
            return false;
        }

        if (costCenter.getParent() != null) {
            Kostenplaats parentCurrent = entityManager.find(Kostenplaats.class, costCenter.getParent().getId());

            current.setParent(parentCurrent);
        }
        current.setFilter(costCenter.getFilter());
        current.setNaam(costCenter.getName());
        final EntityTransaction et = entityManager.getTransaction();
        et.begin();
        entityManager.merge(current);
        entityManager.flush();
        et.commit();
        return true;
    }

    private boolean remove(Long id) {
        Kostenplaats current = entityManager.find(Kostenplaats.class, id);
        if (null == current) {
            return false;
        }
        final EntityTransaction et = entityManager.getTransaction();

        et.begin();
        entityManager.remove(current);
        et.commit();
        return true;
    }

    private void removeAll() {
        entityManager.createNamedQuery("Kostenplaats.deleteAll").executeUpdate();
    }

    private void insertAll(List<CostCenter> costcenters) {
        costcenters.stream().forEach(this::insertNewRoot);
    }

    @SuppressWarnings("squid:UnusedPrivateMethod") // Sonar false positive as the method is used as a lambda
    private void insertNewRoot(CostCenter costCenter) {
        Kostenplaats current = new Kostenplaats();
        current.setFilter(costCenter.getFilter());
        current.setNaam(costCenter.getName());
        current.setKostenplaatsCollection(new ArrayList<>());
        entityManager.persist(current);
        entityManager.flush();

        if (costCenter.getList() != null) {
            costCenter.getList().stream().forEach(cc -> insertNewSub(current, cc));
        }
    }

    private void insertNewSub(Kostenplaats parent, CostCenter costCenter) {
        Kostenplaats current = new Kostenplaats();
        current.setFilter(costCenter.getFilter());
        current.setNaam(costCenter.getName());
        current.setParent(parent);
        current.setKostenplaatsCollection(new ArrayList<>());
        parent.getKostenplaatsCollection().add(current);
        entityManager.persist(current);
        entityManager.flush();

        if (costCenter.getList() != null) {
            costCenter.getList().stream().forEach(cc -> insertNewSub(current, cc));
        }

    }
}

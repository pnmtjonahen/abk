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
package nl.tjonahen.abk.backend.boundry.transaction;

import java.util.Arrays;
import nl.tjonahen.abk.backend.model.OrderBy;
import java.util.Optional;
import java.util.stream.Collectors;
import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.ws.rs.DefaultValue;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;
import nl.tjonahen.abk.backend.ResourcePaths;
import nl.tjonahen.abk.backend.model.Fields;
import nl.tjonahen.abk.backend.model.FinancialTransaction;
import nl.tjonahen.abk.backend.model.FinancialTransactions;
import nl.tjonahen.abk.backend.model.HRef;
import nl.tjonahen.abk.backend.model.Where;
import nl.tjonahen.abk.backend.entity.Fintransactie;

/**
 *
 * @author Philippe Tjon - A - Hen, philippe@tjonahen.nl
 */
@Stateless
@Path(ResourcePaths.TRANSACTIONS_PATH)
public class FinancialTransactionsResource {

    @PersistenceContext
    private EntityManager entityManager;

    @Inject
    private FinancialTransactionQueryBuilder financialTransactionQueryBuilder;

    /**
     * @param uriInfo the uri info for getting the current url
     * @param fields the field filter, a comma separated list of fields.
     * @param orderBy sort ordering
     * @param query the query string
     * @param offset the offset into the result list
     * @param limit the max number of elements to return
     * @return list of financial transactions
     */
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public FinancialTransactions get(@Context UriInfo uriInfo,
            @DefaultValue("") @QueryParam("fields") String fields,
            @DefaultValue("") @QueryParam("orderBy") String orderBy,
            @DefaultValue("") @QueryParam("q") String query,
            @DefaultValue("0") @QueryParam("offset") int offset,
            @DefaultValue("25") @QueryParam("limit") int limit) {
        return this.get(new Fields(fields),
                new Where(query),
                new OrderBy(orderBy),
                offset, limit)
                .updateHref(uriInfo.getAbsolutePath().toString(), fields);
    }

    /**
     *
     * @param id transaction id
     * @param uriInfo the uri info for getting the current url
     * @param fields the field filter, a comma separated list of fields.
     * @return the requested financial transaction or not found.
     */
    @GET
    @Path("/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response get(@Context UriInfo uriInfo,
            @PathParam("id") final Long id,
            @DefaultValue("") @QueryParam("fields") String fields) {
        final Optional<FinancialTransaction> optional = this.get(id, new Fields(fields));
        if (optional.isPresent()) {
            return Response.ok(optional.get()
                    .updateHref(new HRef(uriInfo.getAbsolutePath().toString()).stripId())).build();
        }

        return Response.status(Response.Status.NOT_FOUND).build();
    }

    private FinancialTransactions get(Fields fields, Where where, OrderBy orderBy, int offset, int limit) {
        if (!fields.containsId()) {
            fields.addId();
        }
        return new FinancialTransactions(
                entityManager.createQuery(
                        financialTransactionQueryBuilder.start().where(where).orderBy(orderBy).createSelect())
                .getResultList()
                .stream()
                .skip(offset)
                .limit(limit)
                .map(fintransactie -> createFinTransaction(fields, fintransactie) )
                .collect(Collectors.toList()), offset, limit, count(where));
    }

    private FinancialTransaction createFinTransaction(Fields fields, Fintransactie fintransactie) {
        final FinancialTransaction ft = new FinancialTransaction();
        fields.getList().forEach(f -> FilterField.valueOf(f.toUpperCase()).consume(ft, fintransactie) );
        return ft;
    }

    private int count(Where where) {
        return entityManager.createQuery(
                financialTransactionQueryBuilder.start().where(where).createCount())
                .getSingleResult()
                .intValue();
    }

    private Optional<FinancialTransaction> get(Long id, Fields fields) {
        return entityManager.createNamedQuery("Fintransactie.findById", Fintransactie.class)
                .setParameter("id", id)
                .getResultList()
                .stream()
                .map(fintransactie -> createFullFinancialTransaction(fields, fintransactie))
                .findFirst();
    }

    private FinancialTransaction createFullFinancialTransaction(Fields fields, Fintransactie fintransactie) {
        final FinancialTransaction ft = new FinancialTransaction();
        if (fields.getList().isEmpty()) {
            Arrays.asList(FilterField.values()).forEach(f -> f.consume(ft, fintransactie));
        } else {
            fields.getList().forEach(f -> FilterField.valueOf(f.toUpperCase()).consume(ft, fintransactie) );
        }
        return ft;
    }


}

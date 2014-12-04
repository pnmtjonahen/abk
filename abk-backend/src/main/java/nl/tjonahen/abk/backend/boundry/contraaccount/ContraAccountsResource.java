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
package nl.tjonahen.abk.backend.boundry.contraaccount;

import java.util.stream.Collectors;
import nl.tjonahen.abk.backend.model.ContraAccounts;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import nl.tjonahen.abk.backend.model.ContraAccount;
import nl.tjonahen.abk.backend.entity.Tegenrekening;

/**
 *
 * @author Philippe Tjon - A - Hen, philippe@tjonahen.nl
 */
@Stateless
@Path("/contraaccounts")
public class ContraAccountsResource {

    @PersistenceContext
    private EntityManager entityManager;

    /**
     *
     * @return list of contra accounts (wrapped)
     */
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public ContraAccounts get() {
        return new ContraAccounts(entityManager
                .createNamedQuery("Tegenrekening.findAll", Tegenrekening.class)
                .getResultList()
                .stream()
                .map(this::convert)
                .collect(Collectors.toList()));
    }

    private ContraAccount convert(final Tegenrekening tegenrekening) {
        final ContraAccount contraAccount = new ContraAccount();
        contraAccount.setDescription(tegenrekening.getNaam());
        contraAccount.setNumber(tegenrekening.getRekening());
        return contraAccount;
    }
}

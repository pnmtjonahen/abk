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
package nl.tjonahen.abk.backend.boundry.account;

import java.util.stream.Collectors;
import javax.enterprise.context.RequestScoped;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import nl.tjonahen.abk.backend.model.Account;
import nl.tjonahen.abk.backend.model.Accounts;
import nl.tjonahen.abk.backend.entity.Rekening;

/**
 *
 * @author Philippe Tjon - A - Hen, philippe@tjonahen.nl
 */
//@Api(value = "Account resources")
@Path("/accounts")
@RequestScoped
public class AccountsResource {

    @PersistenceContext(unitName = "abk")
    private EntityManager entityManager;

//    @ApiOperation(value="Get all accounts", response = Accounts.class)
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Accounts get() {
        return new Accounts(entityManager
                .createNamedQuery("Rekening.findAll", Rekening.class)
                .getResultList()
                .stream()
                .map(AccountsResource::convert)
                .collect(Collectors.toList()));
    }

    @SuppressWarnings("squid:UnusedPrivateMethod") // Sonar false positive as the method is used as a lambda
    private static Account convert(final Rekening rekening) {
        final Account account = new Account();
        account.setNumber(rekening.getRekening());
        account.setDescription(rekening.getNaam());
        if (rekening.getBeginsaldo() != null) {
            account.setStartsaldi(rekening.getBeginsaldo().toString());
        }
        return account;
    }
}

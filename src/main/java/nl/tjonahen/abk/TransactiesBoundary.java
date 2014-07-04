/*
 * Copyright (C) 2013 Philippe Tjon-A-Hen philippe@tjonahen.nl
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
package nl.tjonahen.abk;

import java.util.List;
import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.xml.datatype.DatatypeConfigurationException;
import nl.tjonahen.abk.business.ABKBusiness;
import nl.tjonahen.abk.domein.Transacties;
import nl.tjonahen.abk.domein.entity.Transactie;

/**
 *
 * @author Philippe Tjon-A-Hen philippe@tjonahen.nl
 */
@Path("/transacties")
@Stateless
public class TransactiesBoundary {

    @EJB
    private ABKBusiness aBKBusiness;

    /**
     * 
     * @return -
     * @throws DatatypeConfigurationException  -
     */
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Transacties getTransacties() throws DatatypeConfigurationException {
        return aBKBusiness.getTransacties();
    }
    /**
     * @param filter regexp to filter transactions
     * @return -
     * @throws DatatypeConfigurationException  -
     */
    @GET
    @Path("/filter/{filter}")
    @Produces(MediaType.APPLICATION_JSON)
    public List<Transactie> getTransactiesFiltered(@PathParam("filter") final String filter) 
            throws DatatypeConfigurationException 
    {
        return aBKBusiness.filterTransacties(filter);
    }

    /**
     * 
     * @param pagina -
     * @return -
     * @throws DatatypeConfigurationException -
     */
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Path("/{pagina}")
    public Transacties getTransactiesPagina(@PathParam("pagina") final int pagina) 
            throws DatatypeConfigurationException 
    {
        return aBKBusiness.getTransactiesPagina(pagina);
    }
}

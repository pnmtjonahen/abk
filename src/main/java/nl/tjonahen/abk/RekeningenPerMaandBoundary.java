/*
 * Copyright (C) 2014 Philippe Tjon-A-Hen philippe@tjonahen.nl
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

import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;
import java.util.List;
import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import nl.tjonahen.abk.business.ABKBusiness;
import nl.tjonahen.abk.domein.RekeningPerMaandRapport;
import nl.tjonahen.abk.domein.entity.Transactie;
import nl.tjonahen.abk.domein.value.Maand;
import nl.tjonahen.abk.domein.value.Periode;

/**
 * Maand overzicht (pre rekening).
 *
 * Overzicht toon huidige maand uitgave en inkomsten tegen het gemiddelde van het afgelope jaar (op dag basis)
 *
 * @author Philippe Tjon-A-Hen philippe@tjonahen.nl
 */
@Path("/rekeningenpermaand")
@Stateless
public class RekeningenPerMaandBoundary {

    @EJB
    private ABKBusiness aBKBusiness;

    /**
     *
     * @return -
     */
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public RekeningPerMaandRapport get() {
        final RekeningPerMaandRapport maandRapport = new RekeningPerMaandRapport();

        createAverage(maandRapport);
        createCurrent(maandRapport);
        
        return maandRapport;
    }

    private void createAverage(final RekeningPerMaandRapport maandRapport) {
        final Maand current = new Maand(new Date(System.currentTimeMillis()));
        final Periode period = new Periode(current.rollBack(YEAR), current);
        final List<Transactie> transacties = aBKBusiness.findTransacties(period);

        for (Transactie tr : transacties) {
            Instant instant = Instant.ofEpochMilli(tr.getDatum().getTime());
            LocalDate ld = LocalDateTime.ofInstant(instant, ZoneId.systemDefault()).toLocalDate();
            maandRapport.updateKosten(ld.getDayOfMonth(), tr.bepaalBedrag());
        }
        maandRapport.average(YEAR);
    }
    private static final int YEAR = 12;
    private void createCurrent(final RekeningPerMaandRapport maandRapport) {
        final Maand current = new Maand(new Date(System.currentTimeMillis()));
        final Periode period = new Periode(current.rollBack(1), current);
        final List<Transactie> transacties = aBKBusiness.findTransacties(period);

        for (Transactie tr : transacties) {
            Instant instant = Instant.ofEpochMilli(tr.getDatum().getTime());
            LocalDate ld = LocalDateTime.ofInstant(instant, ZoneId.systemDefault()).toLocalDate();
            maandRapport.updateCurrent(ld.getDayOfMonth(), tr.bepaalBedrag());
        }
    }
}

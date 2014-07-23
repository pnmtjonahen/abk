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

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import nl.tjonahen.abk.business.ABKBusiness;
import nl.tjonahen.abk.domein.YearReport;
import nl.tjonahen.abk.domein.entity.Kostenplaats;
import nl.tjonahen.abk.domein.value.Maand;
import nl.tjonahen.abk.domein.value.Periode;
import nl.tjonahen.abk.domein.entity.Transactie;

/**
 *
 * @author Philippe Tjon-A-Hen philippe@tjonahen.nl
 */
@Path("/jaarrapport")
@Stateless
public class JaarRapportBoundary {
    
    @EJB
    private ABKBusiness aBKBusiness;

    /**
     * 
     * @return - 
     */
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public List<YearReport> get() {
        final Maand current = new Maand(new Date(System.currentTimeMillis()));
        final Periode period = new Periode(current.rollBack(12), current);
        final List<YearReport> result = new ArrayList<>();
        final List<Transactie> transacties = aBKBusiness.findTransacties(period);
        for (final Kostenplaats kp : aBKBusiness.getKostenplaatsen().getKostenplaats()) {
            YearReport yearReport = new YearReport();
            yearReport.setName(kp.getNaam());
            addChildren(yearReport, period, kp, transacties);
            Maand start = period.getStart();
            int index = 0;
            while (!start.isAfther(period.getEnd())) {
                yearReport.addKosten(index, kp.bepaalBedrag(start, transacties));
                start = start.next();
                index++;
            }
            yearReport.sum(); 
            result.add(yearReport);
        }
        return result;
    }
    private void addChildren(YearReport yearReport, final Periode period, Kostenplaats kp, 
                List<Transactie> transacties) 
    {
        for (final Kostenplaats sKp : kp.getSubkostenplaatsen()) {
            YearReport subYearReport = new YearReport();
            subYearReport.setName(sKp.getNaam());
            Maand start = period.getStart();
            int index = 0;
            while (!start.isAfther(period.getEnd())) {
                subYearReport.addKosten(index, sKp.bepaalBedrag(start, transacties));
                start = start.next();
                index++;
            }
            yearReport.getChildren().add(subYearReport);
        }
    }
    

}

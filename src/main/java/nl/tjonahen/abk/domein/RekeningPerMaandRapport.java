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

package nl.tjonahen.abk.domein;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

/**
 *
 * @author Philippe Tjon-A-Hen philippe@tjonahen.nl
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "rekeningpermaandrapport", propOrder = {
    "kosten", "current"
})
@XmlRootElement(name = "rekeningpermaandrapport")
public class RekeningPerMaandRapport {
    
    private DagBedrag[] kosten = new DagBedrag[NR_DAYS];
    private static final int NR_DAYS = 32;
    private DagBedrag[] current = new DagBedrag[NR_DAYS];

    /**
     * Constructor
     */
    public RekeningPerMaandRapport() {
        kosten = createDagBedragArray();
        current = createDagBedragArray();
    
    }

    private DagBedrag[] createDagBedragArray() {
        final DagBedrag[] dagArray = new DagBedrag[NR_DAYS];
        for (int i = 0; i < NR_DAYS; i++) {
            dagArray[i] = new DagBedrag(i);
        }
        return dagArray;
    }

    
    public DagBedrag[] getKosten() {
        return kosten;
    }

    public void setKosten(DagBedrag[] kosten) {
        this.kosten = kosten;
    }

    public DagBedrag[] getCurrent() {
        return current;
    }

    public void setCurrent(DagBedrag[] current) {
        this.current = current;
    }
    
    
    /**
     * 
     * @param day -
     * @param bedrag - 
     */
    public void updateKosten(final int day, final double bedrag) {
        kosten[day].add(bedrag);
    }
    
    /**
     * 
     * @param day -
     * @param bedrag - 
     */
    public void updateCurrent(final int day, final double bedrag) {
        current[day].add(bedrag);
    }

    /**
     * 
     * @param nrMonths - 
     */
    public void average(int nrMonths) {
        for (int i = 0; i< NR_DAYS; i++) {
            kosten[i].setBedrag(kosten[i].getBedrag() / nrMonths);
        }
    }
}

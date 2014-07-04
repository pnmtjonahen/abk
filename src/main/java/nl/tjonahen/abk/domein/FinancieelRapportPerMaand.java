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
package nl.tjonahen.abk.domein;

import nl.tjonahen.abk.domein.value.Maand;
import nl.tjonahen.abk.domein.entity.Kostenplaats;
import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

/**
 * 
 * @author Philippe Tjon-A-Hen philippe@tjonahen.nl
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "financieelrapportpermaand", propOrder = {
    "kostenplaats", "kosten", "subfinancieelrapportpermaand"
})
@XmlRootElement(name = "financieelrapportpermaand")
public class FinancieelRapportPerMaand {

    private Kostenplaats kostenPlaats;
    private List<MaandBedrag> kosten = new ArrayList<>();
    private List<FinancieelRapportPerMaand> subFinancieelRapportPerMaand =
            new ArrayList<>();
    
  

    public Kostenplaats getKostenPlaats() {
        return kostenPlaats;
    }

    public void setKostenPlaats(Kostenplaats kostenPlaats) {
        this.kostenPlaats = kostenPlaats;
    }

    public List<MaandBedrag> getKosten() {
        return kosten;
    }

    public void setKosten(List<MaandBedrag> kosten) {
        this.kosten = kosten;
    }

    public List<FinancieelRapportPerMaand> getSubFinancieelRapportPerMaand() {
        return subFinancieelRapportPerMaand;
    }

    public void setSubFinancieelRapportPerMaand(
            List<FinancieelRapportPerMaand> subFinancieelRapportPerMaand) 
    {
        this.subFinancieelRapportPerMaand = subFinancieelRapportPerMaand;
    }

    /**
     * 
     * @param start -
     * @return -
     */
    public double getMonthBedrag(Maand start) {
        MaandBedrag mb = zoekMaandBedrag(start);
        if (mb != null) {
            return mb.getBedrag();
        }
        return 0;

    }

    private MaandBedrag zoekMaandBedrag(Maand start) {
        for (MaandBedrag mb : kosten) {
            if (mb.getMaand().equals(start)) {
                return mb;
            }
        }
        return null;
    }

    /**
     * 
     * @param start -
     * @param bedrag -
     */
    public void updateKosten(Maand start, Double bedrag) {
        MaandBedrag mb = zoekMaandBedrag(start);
        if (mb != null) {
            double som = mb.getBedrag() + bedrag;
            mb.setBedrag(som);
        } else {
            kosten.add(new MaandBedrag(start, bedrag));
        }
    }
}

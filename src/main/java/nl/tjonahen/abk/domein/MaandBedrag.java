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
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

/**
 * 
 * @author Philippe Tjon-A-Hen philippe@tjonahen.nl
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "maandbedrag", propOrder = {
    "maand", "bedrag"
})
@XmlRootElement(name = "maandbedrag")
public class MaandBedrag {

    private Maand maand;
    private Double bedrag;

    /**
     *
     */
    public MaandBedrag() {

    }

    /**
     * 
     * @param maand -
     * @param bedrag -
     */
    public MaandBedrag(Maand maand, Double bedrag) {
        this.maand = maand;
        this.bedrag = bedrag;
    }

    public Maand getMaand() {
        return maand;
    }

    public void setMaand(Maand maand) {
        this.maand = maand;
    }

    public Double getBedrag() {
        return bedrag;
    }

    public void setBedrag(Double bedrag) {
        this.bedrag = bedrag;
    }

}

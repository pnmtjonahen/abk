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
@XmlType(name = "dagbedrag", propOrder = {
    "dag", "bedrag"
})
@XmlRootElement(name = "dagbedrag")
public class DagBedrag {
    private int dag;
    private int bedrag;

    
    /**
     * 
     * @param dag -
     */
    DagBedrag(int dag) {
        this.dag = dag;
        this.bedrag = 0;
    }

    public int getDag() {
        return dag;
    }

    public void setDag(int dag) {
        this.dag = dag;
    }

    public int getBedrag() {
        return bedrag;
    }

    public void setBedrag(int bedrag) {
        this.bedrag = bedrag;
    }
    /**
     * Add new bedrag
     * @param lbedrag -
     */
    void add(final double lbedrag) {
        this.bedrag += lbedrag; 
    }
    
    
    
}

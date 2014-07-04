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

import nl.tjonahen.abk.domein.entity.Rekening;
import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

/**
 *
 *
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "", propOrder = {
    "rekening"
})
@XmlRootElement(name = "rekeningen")
public class Rekeningen {

    @XmlElement(required = true)
    private List<Rekening> rekening;

    /**
     * 
     */
    public Rekeningen() {
        
    }
    
    /**
     * 
     * @param rekening - 
     */
    public Rekeningen(final List<Rekening> rekening) {
        this.rekening = rekening;
    }
    
    /**
     * 
     * @return - 
     */
    public List<Rekening> getRekening() {
        if (rekening == null) {
            rekening = new ArrayList<>();
        }
        return this.rekening;
    }
}

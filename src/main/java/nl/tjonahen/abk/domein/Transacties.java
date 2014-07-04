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


import nl.tjonahen.abk.domein.entity.Transactie;
import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for anonymous complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType>
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="transactie" type="{http://jaxb.abk.tjonahen.nl}transactie" maxOccurs="unbounded"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "", propOrder = {
    "transactie"
})
@XmlRootElement(name = "transacties")
public class Transacties {
    /**
     * 
     */
    public static final int MAX_ROWS_PER_PAGE = 10;

    private int aantalPaginas;
    private int huidigePagina;
    
    @XmlElement(required = true)
    protected List<Transactie> transactie;

    /**
     * Constr.
     */
    public Transacties() {
        
    }
    /**
     * 
     * @param requestedPage -
     * @param transacties -
     */
    public Transacties(final int requestedPage, final List<Transactie> transacties) {
         transactie = new ArrayList<>();

        int maxPages = transacties.size() / MAX_ROWS_PER_PAGE;
        if (transacties.size() % MAX_ROWS_PER_PAGE > 0) {
            maxPages++;
        }
        huidigePagina = requestedPage;
        if (huidigePagina > maxPages) {
            huidigePagina = maxPages;
        }
        if (huidigePagina == 0) {
            huidigePagina = 1;
        }
        aantalPaginas = maxPages;
    
        int startIndex = (huidigePagina - 1) * MAX_ROWS_PER_PAGE;

        transactie.addAll(transacties.subList(startIndex, 
                Math.min(startIndex + MAX_ROWS_PER_PAGE, transacties.size())));
        
    }

    public int getAantalPaginas() {
        return aantalPaginas;
    }

    public int getHuidigePagina() {
        return huidigePagina;
    }
    
    
    /**
     * Gets the value of the transactie property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the transactie property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getTransactie().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link Transactie }
     * 
     * @return -
     * 
     */
    public List<Transactie> getTransactie() {
        if (transactie == null) {
            transactie = new ArrayList<>();
        }
        return this.transactie;
    }

}

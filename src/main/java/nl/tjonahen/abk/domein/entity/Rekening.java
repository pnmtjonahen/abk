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
package nl.tjonahen.abk.domein.entity;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author Philippe Tjon-A-Hen philippe@tjonahen.nl
 */
@Entity
@Table(name = "REKENING")
@NamedQueries({
    @NamedQuery(name = "Rekening.findAll", query = "SELECT r FROM Rekening r")
})
@XmlRootElement
@XmlAccessorType(XmlAccessType.FIELD)
public class Rekening implements Serializable {

    private static final long serialVersionUID = 1L;
    //CHECKSTYLE:OFF
    @Id
    @Size(max = 64)
    @Column(name = "rekening")
    private String rekening;
    
    @Size(max = 255)
    @Column(name = "naam")
    private String naam;
//CHECKSTYLE:ON
    @Column(name = "beginSaldo")
    private Double beginSaldo;
    
    @OneToMany(fetch = FetchType.LAZY, mappedBy = "account")
    private List<Transactie> transacties = new ArrayList<>();

    /**
     * Constr.
     */
    public Rekening() {
    }


    public String getNaam() {
        return naam;
    }

    public void setNaam(String naam) {
        this.naam = naam;
    }

    public String getRekening() {
        return rekening;
    }

    public void setRekening(String rekening) {
        this.rekening = rekening;
    }

    public Double getBeginSaldo() {
        return beginSaldo;
    }

    public void setBeginSaldo(Double beginSaldo) {
        this.beginSaldo = beginSaldo;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += rekening != null ? rekening.hashCode() : 0;
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        if (!(object instanceof Rekening)) {
            return false;
        }
        Rekening other = (Rekening) object;
        return !(this.rekening == null && other.rekening != null 
                || this.rekening != null && !this.rekening.equals(other.rekening));
    }

    @Override
    public String toString() {
        return "nl.tjonahen.abk.domein.Rekening[ rekening=" + rekening + " ]";
    }

    public List<Transactie> getTransacties() {
        return transacties;
    }
}

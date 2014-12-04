/*
 * Copyright (C) 2014 Philippe Tjon - A - Hen, philippe@tjonahen.nl
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
package nl.tjonahen.abk.backend.entity;

import java.io.Serializable;
import java.util.Collection;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author Philippe Tjon - A - Hen, philippe@tjonahen.nl
 */
@Entity
@Table(name = "REKENING")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Rekening.findAll", query = "SELECT r FROM Rekening r"),
    @NamedQuery(name = "Rekening.findByRekening", query = "SELECT r FROM Rekening r WHERE r.rekening = :rekening"),
    @NamedQuery(name = "Rekening.findByBeginsaldo", 
            query = "SELECT r FROM Rekening r WHERE r.beginsaldo = :beginsaldo"),
    @NamedQuery(name = "Rekening.findByNaam", query = "SELECT r FROM Rekening r WHERE r.naam = :naam")})
public class Rekening implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 255)
    @Column(name = "REKENING")
    private String rekening;
    @Column(name = "BEGINSALDO")
    private Double beginsaldo;
    @Size(max = 255)
    @Column(name = "NAAM")
    private String naam;
    @OneToMany(mappedBy = "accountRekening")
    private Collection<Fintransactie> fintransactieCollection;

    /**
     * 
     */
    public Rekening() {
    }

    /**
     * 
     * @param rekening  -
     */
    public Rekening(String rekening) {
        this.rekening = rekening;
    }

    public String getRekening() {
        return rekening;
    }

    public void setRekening(String rekening) {
        this.rekening = rekening;
    }

    public Double getBeginsaldo() {
        return beginsaldo;
    }

    public void setBeginsaldo(Double beginsaldo) {
        this.beginsaldo = beginsaldo;
    }

    public String getNaam() {
        return naam;
    }

    public void setNaam(String naam) {
        this.naam = naam;
    }

    @XmlTransient
    public Collection<Fintransactie> getFintransactieCollection() {
        return fintransactieCollection;
    }

    public void setFintransactieCollection(Collection<Fintransactie> fintransactieCollection) {
        this.fintransactieCollection = fintransactieCollection;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (rekening != null ? rekening.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        if (!(object instanceof Rekening)) {
            return false;
        }
        Rekening other = (Rekening) object;
        if ((this.rekening == null && other.rekening != null) 
                || (this.rekening != null && !this.rekening.equals(other.rekening))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "nl.tjonahen.abk.backend.entity.Rekening[ rekening=" + rekening + " ]";
    }
    
}

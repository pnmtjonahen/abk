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
import java.util.List;
import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;
import nl.tjonahen.abk.domein.value.Maand;

/**
 *
 * @author Philippe Tjon-A-Hen philippe@tjonahen.nl
 */
@Entity
@Table(name = "KOSTENPLAATS")
@NamedQueries({
    @NamedQuery(name = "Kostenplaats.findAll", query = "SELECT k FROM Kostenplaats k"),
    @NamedQuery(name = "Kostenplaats.findById", query = "SELECT k FROM Kostenplaats k WHERE k.id = :id"),
    @NamedQuery(name = "Kostenplaats.findByNaam", query = "SELECT k FROM Kostenplaats k WHERE k.naam = :naam"),
    @NamedQuery(name = "Kostenplaats.findByRoot", query = "SELECT k FROM Kostenplaats k WHERE k.root = :root")
})
@XmlRootElement
@XmlAccessorType(XmlAccessType.FIELD)
public class Kostenplaats implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id")
    private Long id;
//CHECKSTYLE:OFF    
    @Size(max = 64)
    @Column(name = "naam")
    private String naam;

    @Size(max = 255)
    @Column(name = "filter")
    private String filter;
//CHECKSTYLE:ON    
    @Column(name="root")
    private Boolean root;
    
    @OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private List<Kostenplaats> subkostenplaatsen;
    
    @XmlTransient
    @ManyToOne
    private Kostenplaats parent;

    /**
     * 
     */
    public Kostenplaats() {
    }

    /**
     * 
     * @param id -
     */
    public Kostenplaats(Long id) {
        this.id = id;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Kostenplaats getParent() {
        return parent;
    }

    public void setParent(Kostenplaats parent) {
        this.parent = parent;
    }

    public String getNaam() {
        return naam;
    }

    public void setNaam(String naam) {
        this.naam = naam;
    }

    public List<Kostenplaats> getSubkostenplaatsen() {
        return subkostenplaatsen;
    }

    public void setSubkostenplaatsen(List<Kostenplaats> subkostenplaatsen) {
        this.subkostenplaatsen = subkostenplaatsen;
    }

    public String getFilter() {
        return filter;
    }

    public void setFilter(String filter) {
        this.filter = filter;
    }

    public Boolean getRoot() {
        return root;
    }

    public void setRoot(Boolean root) {
        this.root = root;
    }
    
    

    @Override
    public int hashCode() {
        int hash = 0;
        hash += id != null ? id.hashCode() : 0;
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        if (!(object instanceof Kostenplaats)) {
            return false;
        }
        Kostenplaats other = (Kostenplaats) object;
        return !(this.id != null && !this.id.equals(other.id) || this.id == null && other.id != null);
    }

    /**
     * {@inheritDoc} 
     */
    @Override
    public String toString() {
        return "nl.tjonahen.abk.domein.Kostenplaats[ id=" + id + " ]";
    }


    /**
     * 
     * @param month -
     * @param transacties -
     * @return -
     */
    public double bepaalBedrag(final Maand month, final List<Transactie> transacties) {
        double bedrag = 0;
        for (Transactie tr : transacties) {
            if (month.equals(new Maand(tr.getDatum())) && tr.filter(filter)) {
                bedrag += tr.bepaalBedrag();
            }
        }
        return bedrag;
    }
}

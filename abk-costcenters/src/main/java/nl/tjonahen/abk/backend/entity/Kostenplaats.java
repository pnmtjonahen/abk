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
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author Philippe Tjon - A - Hen, philippe@tjonahen.nl
 */
@Entity
@Table(name = "KOSTENPLAATS")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Kostenplaats.deleteAll", query = "DELETE FROM Kostenplaats"),
    @NamedQuery(name = "Kostenplaats.findAll", query = "SELECT k FROM Kostenplaats k"),
    @NamedQuery(name = "Kostenplaats.findById", query = "SELECT k FROM Kostenplaats k WHERE k.id = :id"),
    @NamedQuery(name = "Kostenplaats.findByRoot", query = "SELECT k FROM Kostenplaats k WHERE k.parent IS NULL ORDER BY k.naam")})
public class Kostenplaats implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "ID")
    private Long id;
    @Size(max = 255)
    @Column(name = "FILTER")
    private String filter;
    @Size(max = 255)
    @Column(name = "NAAM")
    private String naam;
    
    @OneToMany(mappedBy = "parent", fetch = FetchType.EAGER)
    private Collection<Kostenplaats> kostenplaatsCollection;
    
    @JoinColumn(name = "PARENT_ID", referencedColumnName = "ID")
    @ManyToOne(fetch = FetchType.EAGER)
    private Kostenplaats parent;

    @OneToMany(mappedBy = "kostenplaats", fetch = FetchType.EAGER)
    private Collection<Filter> filterCollection;
    
    /**
     * 
     */
    public Kostenplaats() {
        // for unmarshalling purpose
    }

    /**
     * 
     * @param id  -
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

    public String getFilter() {
        return filter;
    }

    public void setFilter(String filter) {
        this.filter = filter;
    }

    public String getNaam() {
        return naam;
    }

    public void setNaam(String naam) {
        this.naam = naam;
    }


    @XmlTransient
    public Collection<Kostenplaats> getKostenplaatsCollection() {
        return kostenplaatsCollection;
    }

    public void setKostenplaatsCollection(Collection<Kostenplaats> kostenplaatsCollection) {
        this.kostenplaatsCollection = kostenplaatsCollection;
    }


    public Kostenplaats getParent() {
        return parent;
    }

    public void setParent(Kostenplaats parent) {
        this.parent = parent;
    }

    public Collection<Filter> getFilterCollection() {
        return filterCollection;
    }

    public void setFilterCollection(Collection<Filter> filterCollection) {
        this.filterCollection = filterCollection;
    }

    
    @Override
    public int hashCode() {
        int hash = 0;
        hash += (id != null ? id.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        if (!(object instanceof Kostenplaats)) {
            return false;
        }
        Kostenplaats other = (Kostenplaats) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "nl.tjonahen.abk.backend.Kostenplaats[ id=" + id + " ]";
    }
    
}

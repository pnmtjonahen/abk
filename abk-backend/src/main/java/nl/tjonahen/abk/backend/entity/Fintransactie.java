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
import java.util.Date;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author Philippe Tjon - A - Hen, philippe@tjonahen.nl
 */
@Entity
@Table(name = "FINTRANSACTIE")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Fintransactie.findAll", query = "SELECT f FROM Fintransactie f"),  
    @NamedQuery(name = "Fintransactie.findById", query = "SELECT f FROM Fintransactie f WHERE f.id = :id"),
    @NamedQuery(name = "Fintransactie.findByHash", query = "SELECT f FROM Fintransactie f WHERE f.hash = :hash")
})
public class Fintransactie implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "ID")
    private Long id;
    @Column(name = "BEDRAG")
    private Double bedrag;
    @Size(max = 255)
    @Column(name = "BIJAF")
    private String bijaf;
    @Size(max = 255)
    @Column(name = "CODE")
    private String code;
    @Column(name = "DATUM")
    @Temporal(TemporalType.DATE)
    private Date datum;
    @Lob
    @Column(name = "MEDEDELING")
    private String mededeling;
    @Size(max = 255)
    @Column(name = "MUTATIESOORT")
    private String mutatiesoort;
    @Size(max = 255)
    @Column(name = "REKENING")
    private String rekening;
    @Size(max = 255)
    @Column(name = "TEGENREKENINGNAAM")
    private String tegenrekeningnaam;
    @Size(max = 255)
    @Column(name = "TEGENREKENINGREKENING")
    private String tegenrekeningrekening;
    @JoinColumn(name = "ACCOUNT_REKENING", referencedColumnName = "REKENING")
    @ManyToOne
    private Rekening accountRekening;
    
    @Size(max = 255)
    @Column(name = "hash", unique = true)
    private String hash;

    /**
     * 
     */
    public Fintransactie() {
        // for unmarshalling purpose
    }

    /**
     * 
     * @param id  -
     */
    public Fintransactie(Long id) {
        this.id = id;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Double getBedrag() {
        return bedrag;
    }

    public void setBedrag(Double bedrag) {
        this.bedrag = bedrag;
    }

    public String getBijaf() {
        return bijaf;
    }

    public void setBijaf(String bijaf) {
        this.bijaf = bijaf;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public Date getDatum() {
        return datum;
    }

    public void setDatum(Date datum) {
        this.datum = datum;
    }

    public String getMededeling() {
        return mededeling;
    }

    public void setMededeling(String mededeling) {
        this.mededeling = mededeling;
    }

    public String getMutatiesoort() {
        return mutatiesoort;
    }

    public void setMutatiesoort(String mutatiesoort) {
        this.mutatiesoort = mutatiesoort;
    }

    public String getRekening() {
        return rekening;
    }

    public void setRekening(String rekening) {
        this.rekening = rekening;
    }

    public String getTegenrekeningnaam() {
        return tegenrekeningnaam;
    }

    public void setTegenrekeningnaam(String tegenrekeningnaam) {
        this.tegenrekeningnaam = tegenrekeningnaam;
    }

    public String getTegenrekeningrekening() {
        return tegenrekeningrekening;
    }

    public void setTegenrekeningrekening(String tegenrekeningrekening) {
        this.tegenrekeningrekening = tegenrekeningrekening;
    }

    public Rekening getAccountRekening() {
        return accountRekening;
    }

    public void setAccountRekening(Rekening accountRekening) {
        this.accountRekening = accountRekening;
    }

    public String getHash() {
        return hash;
    }

    public void setHash(String hash) {
        this.hash = hash;
    }

    

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (id != null ? id.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        if (!(object instanceof Fintransactie)) {
            return false;
        }
        Fintransactie other = (Fintransactie) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "nl.tjonahen.abk.backend.entity.Fintransactie[ id=" + id + " ]";
    }
    
}

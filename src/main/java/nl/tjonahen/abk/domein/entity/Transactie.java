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
import java.util.Date;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
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
@Table(name = "FINTRANSACTIE")
@NamedQueries({
    @NamedQuery(name = "Transactie.findAll", query = "SELECT f FROM Transactie f ORDER BY f.datum desc"),
    @NamedQuery(name = "Transactie.findById", query = "SELECT f FROM Transactie f WHERE f.id = :id"),
    @NamedQuery(name = "Transactie.findByDatum", query = "SELECT f FROM Transactie f WHERE f.datum = :datum"),
    @NamedQuery(name = "Transactie.findByRekening", query = "SELECT f FROM Transactie f WHERE f.rekening = :rekening"),
    @NamedQuery(name = "Transactie.findByCode", query = "SELECT f FROM Transactie f WHERE f.code = :code"),
    @NamedQuery(name = "Transactie.findByBijAf", query = "SELECT f FROM Transactie f WHERE f.bijAf = :bijAf"),
    @NamedQuery(name = "Transactie.findByBedrag", query = "SELECT f FROM Transactie f WHERE f.bedrag = :bedrag"),
    @NamedQuery(name = "Transactie.findByMutatieSoort", 
            query = "SELECT f FROM Transactie f WHERE f.mutatiesoort = :mutatiesoort"),
    @NamedQuery(name = "Transactie.findByMededeling", 
            query = "SELECT f FROM Transactie f WHERE f.mededeling = :mededeling"),
    @NamedQuery(name = "Transactie.findByPeriod", 
            query = "SELECT f FROM Transactie f WHERE f.datum BETWEEN :startDate AND :endDate")
})
@XmlRootElement
@XmlAccessorType(XmlAccessType.FIELD)
public class Transactie implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id")
    private Long id;
    @Column(name = "datum")
    @Temporal(TemporalType.TIMESTAMP)
    private Date datum;
    //CHECKSTYLE:OFF
    @Size(max = 64, message = "rekening size")
    @Column(name = "rekening")
    private String rekening;
    @Size(max = 32, message = "code size")
    @Column(name = "code")
    private String code;
    @Size(max = 5, message = "bijAf size")
    @Column(name = "bijAf")
    private String bijAf;
    @Column(name = "bedrag")
    private Double bedrag;
    @Size(max = 32, message = "mutatiesoort size")
    @Column(name = "mutatiesoort")
    private String mutatiesoort;
    @Size(max = 1024, message = "mededeling")
    @Column(name = "mededeling")
    @Lob
    private String mededeling;
    @Size(max = 255, message = "tegenrekeningNaam size")
    @Column(name = "tegenrekeningNaam")
    private String tegenrekeningNaam;
    @Size(max = 64, message = "tegenrekeningRekening size")
    @Column(name = "tegenrekeningRekening")
    private String tegenrekeningRekening;
    //CHECKSTYLE:ON
    
    
    @XmlTransient
    @ManyToOne(optional = true)
    private Rekening account;

    /**
     * 
     */
    public Transactie() {
    }

    /**
     * 
     * @param id -
     */
    public Transactie(Long id) {
        this.id = id;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Date getDatum() {
        return new Date(datum.getTime());
    }

    public void setDatum(Date datum) {
        this.datum = new Date(datum.getTime());
    }

    public String getRekening() {
        return rekening;
    }

    public void setRekening(String rekening) {
        this.rekening = rekening;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getBijAf() {
        return bijAf;
    }

    public void setBijAf(String bijAf) {
        this.bijAf = bijAf;
    }

    public Double getBedrag() {
        return bedrag;
    }

    public void setBedrag(Double bedrag) {
        this.bedrag = bedrag;
    }

    public String getMutatiesoort() {
        return mutatiesoort;
    }

    public void setMutatiesoort(String mutatieSoort) {
        this.mutatiesoort = mutatieSoort;
    }

    public String getMededeling() {
        return mededeling;
    }

    public void setMededeling(String mededeling) {
        this.mededeling = mededeling;
    }

    public Rekening getAccount() {
        return account;
    }

    public void setAccount(Rekening account) {
        this.account = account;
    }

    public String getTegenrekeningNaam() {
        return tegenrekeningNaam;
    }

    public void setTegenrekeningNaam(String tegenrekeningNaam) {
        this.tegenrekeningNaam = tegenrekeningNaam;
    }

    public String getTegenrekeningRekening() {
        return tegenrekeningRekening;
    }

    public void setTegenrekeningRekening(String tegenrekeningRekening) {
        this.tegenrekeningRekening = tegenrekeningRekening;
    }

    /**
     * {@inheritDoc} 
     */
    @Override
    public int hashCode() {
        int hash = 0;
        hash += id != null ? id.hashCode() : 0;
        return hash;
    }

    /**
     * {@inheritDoc} 
     */
    @Override
    public boolean equals(Object object) {
        if (!(object instanceof Transactie)) {
            return false;
        }
        Transactie other = (Transactie) object;
        return !(this.id == null && other.id != null || this.id != null && !this.id.equals(other.id));
    }

    /**
     * {@inheritDoc} 
     */
    @Override
    public String toString() {
        return "nl.tjonahen.abk.domein.Transactie[ id=" + id + " ]";
    }

    /**
     * 
     * @return - 
     */
    public boolean isAf() {
        return "Af".equals(bijAf);
    }

    /**
     * 
     * @return - 
     */
    public double bepaalBedrag() {
        if (isAf()) {
            return -getBedrag().doubleValue();
        }
        return getBedrag().doubleValue();

    }

    /**
     * 
     * @return -
     */
    public Maand getMonth() {
        return new Maand(datum);
    }
    
    public boolean filter(final String filter) {
        if (filter == null || "".equals(filter)) {
            return false;
        }
        return matching(filter, tegenrekeningNaam + " " + tegenrekeningRekening + " " + mededeling);
    }

    private boolean matching(final String filter, final String tr) {
        if (filter.startsWith("equals:")) {
            return tr.equals(filter.substring("equals:".length()));
        }
        if (filter.startsWith("contains:")) {
            return tr.contains(filter.substring("contains:".length()));
        }
        return tr.matches(filter);
    }
    
}

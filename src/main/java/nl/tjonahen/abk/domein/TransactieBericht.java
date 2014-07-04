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

import java.io.Serializable;
import java.util.Date;

/**
 *
 *
 *
 */
public class TransactieBericht implements Serializable {
    private static final long serialVersionUID = 1L;

    private Double bedrag;
    private String bijAf;
    private String code;
    private Date datum;
    private String mededeling;
    private String mutatiesoort;
    private String rekening;
    private String tegenrekeningNaam;
    private String tegenrekeningRekening;

    /**
     * Gets the value of the bedrag property.
     *
     * @return possible object is {@link Double }
     *
     */
    public Double getBedrag() {
        return bedrag;
    }

    /**
     * Sets the value of the bedrag property.
     *
     * @param value allowed object is {@link Double }
     *
     */
    public void setBedrag(Double value) {
        this.bedrag = value;
    }

    /**
     * Gets the value of the bijAf property.
     *
     * @return possible object is {@link String }
     *
     */
    public String getBijAf() {
        return bijAf;
    }

    /**
     * Sets the value of the bijAf property.
     *
     * @param value allowed object is {@link String }
     *
     */
    public void setBijAf(String value) {
        this.bijAf = value;
    }

    /**
     * Gets the value of the code property.
     *
     * @return possible object is {@link String }
     *
     */
    public String getCode() {
        return code;
    }

    /**
     * Sets the value of the code property.
     *
     * @param value allowed object is {@link String }
     *
     */
    public void setCode(String value) {
        this.code = value;
    }

    /**
     * Gets the value of the datum property.
     *
     * @return possible object is {@link Date }
     *
     */
    public Date getDatum() {
        return new Date(datum.getTime());
    }

    /**
     * Sets the value of the datum property.
     *
     * @param value allowed object is {@link Date }
     *
     */
    public void setDatum(Date value) {
        this.datum = new Date(value.getTime());
    }

    /**
     * Gets the value of the mededeling property.
     *
     * @return possible object is {@link String }
     *
     */
    public String getMededeling() {
        return mededeling;
    }

    /**
     * Sets the value of the mededeling property.
     *
     * @param value allowed object is {@link String }
     *
     */
    public void setMededeling(String value) {
        this.mededeling = value;
    }

    /**
     * Gets the value of the mutatiesoort property.
     *
     * @return possible object is {@link String }
     *
     */
    public String getMutatiesoort() {
        return mutatiesoort;
    }

    /**
     * Sets the value of the mutatiesoort property.
     *
     * @param value allowed object is {@link String }
     *
     */
    public void setMutatiesoort(String value) {
        this.mutatiesoort = value;
    }

    /**
     * Gets the value of the rekening property.
     *
     * @return possible object is {@link String }
     *
     */
    public String getRekening() {
        return rekening;
    }

    /**
     * Sets the value of the rekening property.
     *
     * @param value allowed object is {@link String }
     *
     */
    public void setRekening(String value) {
        this.rekening = value;
    }

    /**
     * Gets the value of the tegenrekeningNaam property.
     *
     * @return possible object is {@link String }
     *
     */
    public String getTegenrekeningNaam() {
        return tegenrekeningNaam;
    }

    /**
     * Sets the value of the tegenrekeningNaam property.
     *
     * @param value allowed object is {@link String }
     *
     */
    public void setTegenrekeningNaam(String value) {
        this.tegenrekeningNaam = value;
    }

    /**
     * Gets the value of the tegenrekeningRekening property.
     *
     * @return possible object is {@link String }
     *
     */
    public String getTegenrekeningRekening() {
        return tegenrekeningRekening;
    }

    /**
     * Sets the value of the tegenrekeningRekening property.
     *
     * @param value allowed object is {@link String }
     *
     */
    public void setTegenrekeningRekening(String value) {
        this.tegenrekeningRekening = value;
    }
}

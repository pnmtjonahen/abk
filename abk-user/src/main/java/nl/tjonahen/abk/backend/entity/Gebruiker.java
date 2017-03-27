/*
 * Copyright (C) 2017 Philippe Tjon - A - Hen philippe@tjonahen.nl
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
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author Philippe Tjon - A - Hen
 */
@Entity
@Table(name = "GEBRUIKER")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Gebruiker.find", query = "SELECT g FROM Gebruiker g WHERE g.naam = :naam AND g.wachtwoord = :wachtwoord"),
    @NamedQuery(name= "Gebruiker.findAll", query = "SELECT g FROM Gebruiker g")
})
public class Gebruiker implements Serializable {
private static final long serialVersionUID = 1L;

    @Id
    @Basic(optional = false)
    @NotNull
    @Size(min = 8, max = 255)
    @Column(name = "NAAM")
    private String naam;
    
    @Basic(optional = false)
    @NotNull
    @Size(min = 8, max = 255)
    @Column(name = "WACHTWOORD")
    private String wachtwoord;

    public String getNaam() {
        return naam;
    }

    public void setNaam(String naam) {
        this.naam = naam;
    }

    public String getWachtwoord() {
        return wachtwoord;
    }

    public void setWachtwoord(String wachtwoord) {
        this.wachtwoord = wachtwoord;
    }


}

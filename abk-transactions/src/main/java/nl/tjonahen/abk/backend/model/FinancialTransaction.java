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
package nl.tjonahen.abk.backend.model;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author Philippe Tjon - A - Hen, philippe@tjonahen.nl
 */
@XmlRootElement
@XmlAccessorType(XmlAccessType.FIELD)
public class FinancialTransaction {

    @XmlElement(name = "id", required = true)
    private String id;
    @XmlElement(name = "meta", required = true)
    private Meta meta;
    @XmlElement(name = "amount", required = true)
    private String amount;
    @XmlElement(name = "debitCreditIndicator", required = false)
    private String debitCreditIndicator;
    @XmlElement(name = "date", required = false)
    private String date;
    @XmlElement(name = "description", required = false)
    private String description;
    @XmlElement(name = "accountNumber", required = false)
    private String accountNumber;
    @XmlElement(name = "code", required = false)
    private String code;
    @XmlElement(name = "mutatiesoort", required = false)
    private String mutatiesoort;
    @XmlElement(name = "contraAccountName", required = true)
    private String contraAccountName;
    @XmlElement(name = "contraAccountNumber", required = false)
    private String contraAccountNumber;
    @XmlElement(name = "costCenterName", required = false)
    private String costCenterName;

    public Meta getMeta() {
        return meta;
    }

    public void setMeta(Meta meta) {
        this.meta = meta;
    }

    /**
     * Update the href meta data of this financial transaction.
     * 
     * @param url -
     * @return this
     */
    public FinancialTransaction updateHref(String url) {
        this.setMeta(new Meta(url + "/" + id));
        return this;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getAmount() {
        return amount;
    }

    public void setAmount(String amount) {
        this.amount = amount;
    }

    public String getDebitCreditIndicator() {
        return debitCreditIndicator;
    }

    public void setDebitCreditIndicator(String debitCreditIndicator) {
        this.debitCreditIndicator = debitCreditIndicator;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getMutatiesoort() {
        return mutatiesoort;
    }

    public void setMutatiesoort(String mutatiesoort) {
        this.mutatiesoort = mutatiesoort;
    }

    public String getAccountNumber() {
        return accountNumber;
    }

    public void setAccountNumber(String accountNumber) {
        this.accountNumber = accountNumber;
    }

    public String getContraAccountName() {
        return contraAccountName;
    }

    public void setContraAccountName(String contraAccountName) {
        this.contraAccountName = contraAccountName;
    }

    public String getContraAccountNumber() {
        return contraAccountNumber;
    }

    public void setContraAccountNumber(String contraAccountNumber) {
        this.contraAccountNumber = contraAccountNumber;
    }
    public String getCostCenterName() {
        return costCenterName;
    }

    public void setCostCenterName(String costCenterName) {
        this.costCenterName = costCenterName;
    }


}

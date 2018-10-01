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
package nl.tjonahen.abk.backend.boundry.transaction;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import nl.tjonahen.abk.backend.entity.Fintransactie;
import nl.tjonahen.abk.backend.lambda.SerializableBiConsumer;
import nl.tjonahen.abk.backend.model.FinancialTransaction;

/**
 *
 * @author Philippe Tjon - A - Hen, philippe@tjonahen.nl
 */
public enum FilterField {
    ID((ft, t) -> ft.setId(t.getId().toString())),
    AMOUNT((ft, t) -> ft.setAmount(t.getBedrag().toString())),
    CONTRAACCOUNTNAME((ft, t) -> ft.setContraAccountName(t.getTegenrekeningnaam())),
    ACCOUNTNUMBER((ft, t) -> ft.setAccountNumber(t.getRekening())),
    DATE((ft, t) -> {
            final Date datum = t.getDatum();
            
            final LocalDateTime ldt = LocalDateTime.ofInstant(
                    Instant.ofEpochMilli(datum.getTime()), ZoneId.systemDefault());
            ft.setDate(ldt.format(DateTimeFormatter.ISO_DATE_TIME));
        }),
    DEBITCREDITINDICATOR((ft, t) -> ft.setDebitCreditIndicator( "Af".equals(t.getBijaf()) ? "debit" : "credit" )),
    DESCRIPTION((ft, t) -> ft.setDescription(t.getMededeling())),
    CODE((ft, t)  -> ft.setCode(t.getCode())),
    MUTATIESOORT((ft, t) -> ft.setMutatiesoort(t.getMutatiesoort())),
    CONTRAACCOUNTNUMBER((ft, t) -> ft.setContraAccountNumber(t.getTegenrekeningrekening())),
    COSTCENTERNAME((ft, t) -> ft.setCostCenterName(t.getKostenplaatsNaam()));


    private final SerializableBiConsumer<FinancialTransaction, Fintransactie> consumer;
    private FilterField(SerializableBiConsumer<FinancialTransaction, Fintransactie> consumer) {
        this.consumer = consumer;
    }
    
    public void consume(FinancialTransaction ft, Fintransactie t) {
        consumer.accept(ft, t);
    }
}

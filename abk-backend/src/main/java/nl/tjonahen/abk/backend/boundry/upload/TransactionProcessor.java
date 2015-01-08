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
package nl.tjonahen.abk.backend.boundry.upload;

import java.io.UnsupportedEncodingException;
import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.PostConstruct;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import nl.tjonahen.abk.backend.entity.Fintransactie;
import nl.tjonahen.abk.backend.entity.Rekening;

/**
 *
 * @author Philippe Tjon - A - Hen, philippe@tjonahen.nl
 */
@Stateless
public class TransactionProcessor {

    @PersistenceContext
    private EntityManager entityManager;

    private MessageDigest md;

    @PostConstruct
    protected void init() {
        try {
            this.md = MessageDigest.getInstance("MD5");
        } catch (NoSuchAlgorithmException ex) {
            throw new RuntimeException(ex);
        }
    }

    private Rekening findRekening(final String rekening) {
        return entityManager.find(Rekening.class, rekening);
    }

    private Rekening bepaalRekening(final Fintransactie tr) {
        Rekening rekening = findRekening(tr.getRekening());
        if (rekening == null) {
            rekening = new Rekening();
            rekening.setRekening(tr.getRekening());
            rekening.setNaam(tr.getRekening());
        }
        return rekening;
    }

    /**
     *
     * @param trans transaction to create
     */
    public void process(final Fintransactie trans) throws UnsupportedEncodingException {
        updateHash(trans);

        final Rekening rekening = bepaalRekening(trans);

        entityManager.persist(trans);

        trans.setAccountRekening(rekening);

        entityManager.persist(rekening);
    }

    private void updateHash(Fintransactie ft) throws UnsupportedEncodingException {
        md.update(ft.getRekening().getBytes("UTF-8"));
        md.update(ft.getBedrag().toString().getBytes("UTF-8"));
        md.update(ft.getCode().getBytes("UTF-8"));
        md.update(ft.getTegenrekeningnaam().getBytes("UTF-8"));
        md.update(ft.getTegenrekeningrekening().getBytes("UTF-8"));
        final Date datum = ft.getDatum();

        final LocalDateTime ldt = LocalDateTime.ofInstant(
                Instant.ofEpochMilli(datum.getTime()), ZoneId.systemDefault());

        md.update(ldt.format(DateTimeFormatter.ISO_DATE).getBytes("UTF-8"));
        md.update(ft.getBijaf().getBytes("UTF-8"));
        md.update(ft.getMededeling().getBytes("UTF-8"));
        md.update(ft.getMutatiesoort().getBytes("UTF-8"));

        byte[] digest = md.digest();
        BigInteger bigInt = new BigInteger(1, digest);
        ft.setHash(bigInt.toString(16));
    }

    public void updateHashes() {
        entityManager
                .createNamedQuery("Fintransactie.findAll", Fintransactie.class)
                .getResultList()
                .stream()
                .forEach(ft -> {
                    try {
                        updateHash(ft);
                        entityManager.merge(ft);
                    } catch (UnsupportedEncodingException ex) {
                        Logger.getLogger(TransactionProcessor.class.getName()).log(Level.SEVERE, null, ex);
                    }
                });
    }
}

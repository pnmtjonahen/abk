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

import java.util.logging.Level;
import java.util.logging.Logger;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import nl.tjonahen.abk.backend.entity.CsvReader;
import nl.tjonahen.abk.backend.entity.Fintransactie;
import nl.tjonahen.abk.backend.entity.Rekening;

/**
 *
 * @author Philippe Tjon - A - Hen, philippe@tjonahen.nl
 */
public class TransactionProcessor {
    private static final Logger LOGGER = Logger.getLogger(TransactionProcessor.class.getName());

    @PersistenceContext(unitName = "abk")
    private EntityManager entityManager;

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
    public void process(final Fintransactie trans) {
        if (isExisting(trans)) {
            final Rekening rekening = bepaalRekening(trans);
            entityManager.getTransaction().begin();
            entityManager.persist(trans);
            trans.setAccountRekening(rekening);
            entityManager.persist(rekening);
            entityManager.flush();
            entityManager.getTransaction().commit();
        } else {
            LOGGER.log(Level.INFO, "Skipping existing Transaction {0} {1} {2}", new Object[]{trans.getDatum(), trans.getBijaf(), trans.getBedrag()});
        }
    }

    private boolean isExisting(final Fintransactie trans) {
        return entityManager.createNamedQuery("Fintransactie.findByHash")
                .setParameter("hash", trans.getHash())
                .getResultList()
                .isEmpty();
    }

    public CsvReader getCsvReader() {
        return entityManager.createNamedQuery("CsvReader.findAll", CsvReader.class).getSingleResult();
    }
}

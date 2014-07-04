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
package nl.tjonahen.abk.business;

import java.util.ArrayList;
import java.util.List;
import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.ws.rs.PathParam;
import javax.xml.datatype.DatatypeConfigurationException;
import nl.tjonahen.abk.domein.entity.Kostenplaats;
import nl.tjonahen.abk.domein.Kostenplaatsen;
import nl.tjonahen.abk.domein.value.Periode;
import nl.tjonahen.abk.domein.entity.Rekening;
import nl.tjonahen.abk.domein.Rekeningen;
import nl.tjonahen.abk.domein.entity.Transactie;
import nl.tjonahen.abk.domein.TransactieBericht;
import nl.tjonahen.abk.domein.Transacties;

/**
 *
 * @author Philippe Tjon-A-Hen philippe@tjonahen.nl
 */
@Stateless
@TransactionAttribute(value = TransactionAttributeType.NOT_SUPPORTED)
public class ABKBusiness {

    @PersistenceContext
    private EntityManager entityManager;

    /**
     * 
     * @return - 
     */
    public Kostenplaatsen getKostenplaatsen() {
        return new Kostenplaatsen(entityManager
                .createNamedQuery("Kostenplaats.findByRoot", Kostenplaats.class)
                .setParameter("root", Boolean.TRUE)
                .getResultList());
    }

    /**
     * 
     * @return -
     * @throws DatatypeConfigurationException -
     */
    public Transacties getTransacties() throws DatatypeConfigurationException {
        return new Transacties(1, 
                entityManager.createNamedQuery("Transactie.findAll", Transactie.class).getResultList());
    }

    /**
     * 
     * @param pagina -
     * @return -
     * @throws DatatypeConfigurationException  -
     */
    public Transacties getTransactiesPagina(@PathParam("pagina") final int pagina) 
            throws DatatypeConfigurationException 
    {
        return new Transacties(pagina, 
                entityManager.createNamedQuery("Transactie.findAll", Transactie.class).getResultList());
    }

    /**
     * 
     * @return - 
     */
    public Rekeningen getRekeningen() {
        return new Rekeningen(
                entityManager.createNamedQuery("Rekening.findAll", Rekening.class).getResultList());
    }

    /**
     * 
     * @param updated -
     */
    @TransactionAttribute(value = TransactionAttributeType.REQUIRES_NEW)
    public void update(final Rekeningen updated) {
        final List<Rekening> rekeningen = entityManager.createNamedQuery("Rekening.findAll", Rekening.class)
                .getResultList();
        final List<Rekening> removed = new ArrayList<>();

        for (final Rekening rekening : rekeningen) {
            Rekening updatedRekening = findUpdated(rekening.getRekening(), updated.getRekening());
            if (updatedRekening == null) {
                removed.add(rekening);
            } else {
                rekening.setNaam(updatedRekening.getNaam());
                rekening.setRekening(updatedRekening.getRekening());
                rekening.setBeginSaldo(updatedRekening.getBeginSaldo());
                entityManager.merge(rekening);
            }
        }

        for (final Rekening rekening : removed) {
            entityManager.remove(rekening);
        }

        for (final Rekening rekening : updated.getRekening()) {
            if (findRekening(rekening.getRekening()) == null) {
                entityManager.persist(rekening);
            }
        }
    }

    private Rekening findUpdated(final String rekeningNummer, final List<Rekening> updated) {
        for (final Rekening rekening : updated) {
            if (rekeningNummer.equals(rekening.getRekening())) {
                return rekening;
            }
        }
        return null;

    }


    /**
     * 
     * @param rekening -
     * @return -
     */
    public Rekening findRekening(final String rekening) {
        return entityManager.find(Rekening.class, rekening);
    }

    private Rekening bepaalRekening(final TransactieBericht tr) {
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
     * @param tr - 
     */
    @TransactionAttribute(value = TransactionAttributeType.REQUIRES_NEW)
    public void create(final TransactieBericht tr) {
        final Rekening rekening = bepaalRekening(tr);
        final Transactie trans = createTransactie(tr);

        

        entityManager.persist(trans);

//        rekening.getTransacties().add(trans);
        trans.setAccount(rekening);
        
        entityManager.persist(rekening);
    }


    private Transactie createTransactie(final TransactieBericht tr) {

        Transactie trans = new Transactie();

        trans.setDatum(tr.getDatum());
        trans.setRekening(tr.getRekening());
        trans.setCode(tr.getCode());
        trans.setBijAf(tr.getBijAf());
        trans.setBedrag(tr.getBedrag());
        trans.setMutatiesoort(tr.getMutatiesoort());
        trans.setMededeling(tr.getMededeling());
        trans.setTegenrekeningNaam(tr.getTegenrekeningNaam());
        trans.setTegenrekeningRekening(tr.getTegenrekeningRekening());
        return trans;
    }

    /**
     * 
     * @param updated - 
     */
    @TransactionAttribute(value = TransactionAttributeType.REQUIRES_NEW)
    public void update(final Kostenplaatsen updated) {
        for (final Kostenplaats kp : updated.getKostenplaats()) {
            Kostenplaats current = null;
            if (kp.getId() != null) {
                current = findKostenplaats(kp.getId());
            }
            if (current == null) {
                current = new Kostenplaats();
                current.setSubkostenplaatsen(new ArrayList<Kostenplaats>());
                current.setRoot(Boolean.TRUE);
            }
            current.setNaam(kp.getNaam());
            current.setFilter(kp.getFilter());
            updateSubkostenplaat(current, kp);

            entityManager.persist(current);
        }
    }

    private Kostenplaats findKostenplaats(final Long id) {
        return entityManager.find(Kostenplaats.class, id);
    }

    /**
     * 
     * @param period -
     * @return -
     */
    public List<Transactie> findTransacties(final Periode period) {
        return entityManager.createNamedQuery("Transactie.findByPeriod", Transactie.class)
                .setParameter("startDate", period.getStart().getDate())
                .setParameter("endDate", period.getEnd().getDate())
                .getResultList();
    }

    private void updateSubkostenplaat(final Kostenplaats current, final Kostenplaats updated) {
        if (updated.getSubkostenplaatsen() == null) {
            return;
        }
        for (final Kostenplaats kp : updated.getSubkostenplaatsen()) {
            if (kp.getId() != null) {
                Kostenplaats sub = findSubkostenplaats(current.getSubkostenplaatsen(), kp.getId());
                if (sub == null) {
                    sub = new Kostenplaats();
                    sub.setRoot(Boolean.FALSE);
                    sub.setParent(current);
                    current.getSubkostenplaatsen().add(sub);
                }
                sub.setNaam(kp.getNaam());
                sub.setFilter(kp.getFilter());
            } else {
                Kostenplaats sub = new Kostenplaats();
                sub.setRoot(Boolean.FALSE);
                current.getSubkostenplaatsen().add(sub);
                sub.setNaam(kp.getNaam());
                sub.setFilter(kp.getFilter());
            }
        }
    }

    private Kostenplaats findSubkostenplaats(List<Kostenplaats> subkostenplaatsen, Long id) {
        for (final Kostenplaats kostenplaats : subkostenplaatsen) {
            if (kostenplaats.getId().equals(id)) {
                return kostenplaats;
            }
        }
        return null;
    }

    /**
     * 
     * @param id - 
     */
    @TransactionAttribute(value = TransactionAttributeType.REQUIRES_NEW)
    public void removeKostenplaats(final Long id) {
        final Kostenplaats kostenplaats = entityManager.find(Kostenplaats.class, id);
        
        entityManager.remove(kostenplaats);
    }

    /**
     * 
     * @param filter filter transactions on regexp
     * @return -
     */
    public List<Transactie> filterTransacties(final String filter) {
        final List<Transactie> result = new ArrayList<>();
        for (final Transactie transactie : entityManager.createNamedQuery("Transactie.findAll", Transactie.class)
                .getResultList()) 
        {
            if (transactie.filter(filter)) {
                result.add(transactie);
            }
        }
        return result;
    }
}

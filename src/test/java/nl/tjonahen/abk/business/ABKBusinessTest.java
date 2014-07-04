/*
 * Copyright (C) 2014 Philippe Tjon-A-Hen philippe@tjonahen.nl
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
import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import javax.xml.datatype.DatatypeConfigurationException;
import nl.tjonahen.abk.domein.Kostenplaatsen;
import nl.tjonahen.abk.domein.Rekeningen;
import nl.tjonahen.abk.domein.Transacties;
import nl.tjonahen.abk.domein.entity.Kostenplaats;
import nl.tjonahen.abk.domein.entity.Rekening;
import nl.tjonahen.abk.domein.entity.Transactie;
import nl.tjonahen.abk.domein.value.Maand;
import nl.tjonahen.abk.domein.value.Periode;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import org.junit.Before;
import org.junit.Test;

import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import static org.mockito.Mockito.atLeastOnce;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import org.mockito.MockitoAnnotations;

/**
 *
 * @author Philippe Tjon-A-Hen philippe@tjonahen.nl
 */
public class ABKBusinessTest {

    @Mock
    private EntityManager entityManager;
    
    @Mock
    private TypedQuery query;

    @InjectMocks
    private ABKBusiness systemUnderTest;

    /**
     * Init mocks
     */
    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    private static List<Transactie> getDummyTransacties(int count) {
        
        List<Transactie> list = new ArrayList<>();
        
        for (int i = 0; i < count; i++) {
            final Transactie transactie = new Transactie();
            transactie.setTegenrekeningRekening("this is transaction" + i );
            list.add(transactie);
        }
        
        return list;
    }

    /**
     * Test of getKostenplaatsen method, of class ABKBusiness.
     */
    @Test
    public void testGetKostenplaatsen() {

        when(entityManager.createNamedQuery("Kostenplaats.findByRoot", Kostenplaats.class)).thenReturn(query);
        when(query.setParameter("root", Boolean.TRUE)).thenReturn(query);
        when(query.getResultList()).thenReturn(new ArrayList());
        
        Kostenplaatsen result = systemUnderTest.getKostenplaatsen();
        assertNotNull(result);

    }

    /**
     * Test of getTransacties method, of class ABKBusiness.
     */
    @Test
    public void testGetTransacties() throws DatatypeConfigurationException {
        when(entityManager.createNamedQuery("Transactie.findAll", Transactie.class)).thenReturn(query);
        when(query.getResultList()).thenReturn(getDummyTransacties(0));
        
        Transacties result = systemUnderTest.getTransacties();
        assertNotNull(result);
        assertEquals(1, result.getHuidigePagina());

    }

    @Test
    public void testFilterTransacties() {
        when(entityManager.createNamedQuery("Transactie.findAll", Transactie.class)).thenReturn(query);
        when(query.getResultList()).thenReturn(getDummyTransacties(2));
        
        assertEquals(1, systemUnderTest.filterTransacties(".*transaction1.*").size());
    }

    /**
     * Test of getTransactiesPagina method, of class ABKBusiness.
     */
    @Test
    public void testGetTransactiesPagina() throws DatatypeConfigurationException {
        when(entityManager.createNamedQuery("Transactie.findAll", Transactie.class)).thenReturn(query);
        when(query.getResultList()).thenReturn(getDummyTransacties(30));
        
        Transacties result = systemUnderTest.getTransactiesPagina(2);
        assertNotNull(result);
        assertEquals(2, result.getHuidigePagina());
    }

    /**
     * Test of getRekeningen method, of class ABKBusiness.
     */
    @Test
    public void testGetRekeningen()  {
        when(entityManager.createNamedQuery("Rekening.findAll", Rekening.class)).thenReturn(query);
        when(query.getResultList()).thenReturn(new ArrayList());
        
        Rekeningen result = systemUnderTest.getRekeningen();
        assertNotNull(result);
    }

    /**
     * Test of update method, of class ABKBusiness.
     */
    @Test
    public void testUpdateRekeningenAllNew() {
        when(entityManager.createNamedQuery("Rekening.findAll", Rekening.class)).thenReturn(query);
        when(query.getResultList()).thenReturn(new ArrayList());
        
        final Rekeningen rekeningen = new Rekeningen();
        rekeningen.getRekening().add(createRekening("1234567", "dummy"));
        
        systemUnderTest.update(rekeningen);

        final ArgumentCaptor<Rekening> captor = ArgumentCaptor.forClass(Rekening.class);
        verify(entityManager).persist(captor.capture());
        
        final Rekening persist = captor.getValue();
        assertEquals("1234567", persist.getRekening());
        assertEquals("dummy", persist.getNaam());

    }

    /**
     * Test of update method, of class ABKBusiness.
     */
    @Test
    public void testUpdateRekeningenRemove() {
        when(entityManager.createNamedQuery("Rekening.findAll", Rekening.class)).thenReturn(query);
        final List<Rekening> arrayList = new ArrayList<>();
        arrayList.add(createRekening("1234567", "dummy"));
        when(query.getResultList()).thenReturn(arrayList);
        
        final Rekeningen rekeningen = new Rekeningen();
        
        systemUnderTest.update(rekeningen);

        final ArgumentCaptor<Rekening> captor = ArgumentCaptor.forClass(Rekening.class);
        verify(entityManager).remove(captor.capture());
        
        final Rekening persist = captor.getValue();
        assertEquals("1234567", persist.getRekening());
        assertEquals("dummy", persist.getNaam());

    }
    /**
     * Test of update method, of class ABKBusiness.
     */
    @Test
    public void testUpdateRekeningenUpdate() {
        when(entityManager.createNamedQuery("Rekening.findAll", Rekening.class)).thenReturn(query);
        final List<Rekening> arrayList = new ArrayList<>();
        arrayList.add(createRekening("1234567", "dummy"));
        when(query.getResultList()).thenReturn(arrayList);
        
        final Rekeningen rekeningen = new Rekeningen();
        rekeningen.getRekening().add(createRekening("1234567", "dummy2"));
        
        systemUnderTest.update(rekeningen);

        final ArgumentCaptor<Rekening> captor = ArgumentCaptor.forClass(Rekening.class);
        verify(entityManager).merge(captor.capture());
        
        final Rekening persist = captor.getValue();
        assertEquals("1234567", persist.getRekening());
        assertEquals("dummy2", persist.getNaam());

    }

    private Rekening createRekening(final String rekeningNr, final String naam) {
        final Rekening rekening = new Rekening();
        rekening.setRekening(rekeningNr);
        rekening.setNaam(naam);
        return rekening;
    }

    /**
     * Test of findRekening method, of class ABKBusiness.
     */
    @Test
    public void testFindRekening() {
        when(entityManager.find(Rekening.class, "1234567")).thenReturn(new Rekening());
        
        Rekening result = systemUnderTest.findRekening("1234567");
        assertNotNull(result);
    }




    /**
     * Test of update method, of class ABKBusiness.
     */
    @Test
    public void testUpdateKostenplaatsenEmpty() {
        systemUnderTest.update(new Kostenplaatsen());
    }
    /**
     * Test of update method, of class ABKBusiness.
     */
    @Test
    public void testUpdateKostenplaatsenAllNew() {
        final ArrayList<Kostenplaats> list = new ArrayList<>();
        
        final Kostenplaats kostenplaatsNew = new Kostenplaats();
        kostenplaatsNew.setSubkostenplaatsen(new ArrayList<Kostenplaats>());
        kostenplaatsNew.getSubkostenplaatsen().add(new Kostenplaats());
        list.add(kostenplaatsNew);
        
        final Kostenplaatsen kostenplaatsen = new Kostenplaatsen(list);

        systemUnderTest.update(kostenplaatsen);
 
        
    }
    /**
     * Test of update method, of class ABKBusiness.
     */
    @Test
    public void testUpdateKostenplaatsenAllExisting() {
        
        
        final Kostenplaats exsisting = new Kostenplaats(1L);
        exsisting.setSubkostenplaatsen(new ArrayList<Kostenplaats>());
        exsisting.getSubkostenplaatsen().add(new Kostenplaats(2L));
        // remove is done with seperate method. so no change should occure
        exsisting.getSubkostenplaatsen().add(new Kostenplaats(4L));

        final ArrayList<Kostenplaats> list = new ArrayList<>();
        // update existing
        list.add(new Kostenplaats(1L));
        list.get(0).setSubkostenplaatsen(new ArrayList<Kostenplaats>());
        // update existing 
        list.get(0).getSubkostenplaatsen().add(new Kostenplaats(2L));
        // update (with id) == new
        list.get(0).getSubkostenplaatsen().add(new Kostenplaats(3L));
        // add new sub
        list.get(0).getSubkostenplaatsen().add(new Kostenplaats());
        // add new
        list.add(new Kostenplaats());
        
        final Kostenplaatsen kostenplaatsen = new Kostenplaatsen(list);

        
        when(entityManager.find(Kostenplaats.class, 1L)).thenReturn(exsisting);

        systemUnderTest.update(kostenplaatsen);
        final ArgumentCaptor<Kostenplaats> captor = ArgumentCaptor.forClass(Kostenplaats.class);
        verify(entityManager, atLeastOnce()).persist(captor.capture());
        
        final List<Kostenplaats> updated = captor.getAllValues();
        
        assertEquals(2, updated.size());
        assertEquals(4, updated.get(0).getSubkostenplaatsen().size());
        assertEquals(0, updated.get(1).getSubkostenplaatsen().size());
        
    }

    
    /**
     * Test of findTransacties method, of class ABKBusiness.
     */
    @Test
    public void testFindTransacties() {
        final Periode periode = new Periode(new Maand(1, 2014), new Maand(12, 2014));

        when(entityManager.createNamedQuery("Transactie.findByPeriod", Transactie.class)).thenReturn(query);
        when(query.setParameter("startDate", periode.getStart().getDate())).thenReturn(query);
        when(query.setParameter("endDate", periode.getEnd().getDate())).thenReturn(query);
        when(query.getResultList()).thenReturn(new ArrayList());
        
        List<Transactie> result = systemUnderTest.findTransacties(periode);
        assertNotNull(result);
    }

    /**
     * Test of removeKostenplaats method, of class ABKBusiness.
     */
    @Test
    public void testRemoveKostenplaats() {
        when(entityManager.find(Kostenplaats.class, 1L)).thenReturn(new Kostenplaats(1L));
        
        systemUnderTest.removeKostenplaats(1L);
        
        final ArgumentCaptor<Kostenplaats> captor = ArgumentCaptor.forClass(Kostenplaats.class);
        
        verify(entityManager, atLeastOnce()).remove(captor.capture());

        Kostenplaats kp = captor.getValue();
        
        assertEquals(1L, kp.getId().longValue());
    }

}

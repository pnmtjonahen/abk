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

import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.Date;
import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaQuery;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;
import nl.tjonahen.abk.backend.entity.Fintransactie;

import nl.tjonahen.abk.backend.model.FinancialTransaction;
import nl.tjonahen.abk.backend.model.FinancialTransactions;
import nl.tjonahen.abk.backend.model.OrderBy;
import nl.tjonahen.abk.backend.model.Where;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import static org.mockito.Matchers.any;
import org.mockito.Mock;
import static org.mockito.Mockito.when;
import org.mockito.MockitoAnnotations;

/**
 *
 * @author Philippe Tjon - A - Hen, philippe@tjonahen.nl
 */
public class FinancialTransactionsResourceTest {

    @Mock
    private EntityManager entityManager;

    @Mock
    private UriInfo uriInfo;

    @Mock
    private FinancialTransactionQueryBuilder financialTransactionQueryBuilder;

    @Mock
    private FinancialTransactionQuery financialTransactionQuery;

    @Mock
    private CriteriaQuery<Fintransactie> cb;

    @Mock
    private CriteriaQuery<Long> cbCount;

    @Mock
    private TypedQuery<Fintransactie> typedQuery;

    @Mock
    private TypedQuery<Long> typedCountQuery;

//    @Mock
//    private TypedQuery<Kostenplaats> kostenPLaatsQuery2;

    @InjectMocks
    private FinancialTransactionsResource systemUnderTest;

    /**
     * Init mocks
     */
    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void testGet() throws URISyntaxException {

        when(financialTransactionQueryBuilder.start()).thenReturn(financialTransactionQuery);
        when(financialTransactionQuery.where(any(Where.class))).thenReturn(financialTransactionQuery);
        when(financialTransactionQuery.orderBy(any(OrderBy.class))).thenReturn(financialTransactionQuery);
        when(financialTransactionQuery.createSelect()).thenReturn(cb);

        when(entityManager.createQuery(cb)).thenReturn(typedQuery);
        final ArrayList<Fintransactie> arrayList = new ArrayList<>();
        final Fintransactie fintransactie = new Fintransactie(1L);
        fintransactie.setDatum(new Date());
        fintransactie.setMededeling("Message");
        arrayList.add(fintransactie);

        when(typedQuery.getResultList()).thenReturn(arrayList);

        when(financialTransactionQueryBuilder.start()).thenReturn(financialTransactionQuery);
        when(financialTransactionQuery.where(any(Where.class))).thenReturn(financialTransactionQuery);
        when(financialTransactionQuery.createCount()).thenReturn(cbCount);
        when(entityManager.createQuery(cbCount)).thenReturn(typedCountQuery);
        when(typedCountQuery.getSingleResult()).thenReturn(new Long(0));

        when(uriInfo.getAbsolutePath()).thenReturn(new URI("transactions"));

        final FinancialTransactions get = systemUnderTest.get(uriInfo, "", "", "", 0, 1);

        assertNotNull(get.getList());
        assertNotNull(get.getFirst());
        assertNotNull(get.getLast());
        assertNull(get.getNext());
        assertNull(get.getPrevious());
        assertEquals(1, get.getLimit());
        assertEquals(0, get.getOffset());
        assertEquals(0, get.getSize());
    }

    @Test
    public void testGetByIdOnlyDesciption() throws URISyntaxException {

        when(entityManager.createNamedQuery("Fintransactie.findById", Fintransactie.class)).thenReturn(typedQuery);
        when(typedQuery.setParameter("id", 1L)).thenReturn(typedQuery);
        final ArrayList<Fintransactie> arrayList = new ArrayList<>();
        final Fintransactie fintransactie = new Fintransactie(1L);
        fintransactie.setDatum(new Date());
        fintransactie.setMededeling("Message");
        fintransactie.setBijaf("Bij");
        arrayList.add(fintransactie);
        when(typedQuery.getResultList()).thenReturn(arrayList);

        when(uriInfo.getAbsolutePath()).thenReturn(new URI("transactions/1"));

        Response response = systemUnderTest.get(uriInfo, 1L, "DESCRIPTION,DATE,DEBITCREDITINDICATOR");

        assertEquals(200, response.getStatus());
        FinancialTransaction ft = (FinancialTransaction) response.getEntity();

        assertNull(ft.getAmount());
        assertNotNull(ft.getDate());
        assertNotNull(ft.getDescription());
        assertEquals("credit", ft.getDebitCreditIndicator());
    }

    @Test
    public void testGetByIdNoFiltering() throws URISyntaxException {

        when(entityManager.createNamedQuery("Fintransactie.findById", Fintransactie.class)).thenReturn(typedQuery);
        when(typedQuery.setParameter("id", 1L)).thenReturn(typedQuery);
        final ArrayList<Fintransactie> arrayList = new ArrayList<>();
        final Fintransactie fintransactie = new Fintransactie(1L);
        fintransactie.setBedrag(200.0);
        fintransactie.setDatum(new Date());
        fintransactie.setBijaf("Af");
        arrayList.add(fintransactie);
        when(typedQuery.getResultList()).thenReturn(arrayList);

        when(uriInfo.getAbsolutePath()).thenReturn(new URI("transactions/1"));

        Response response = systemUnderTest.get(uriInfo, 1L, "");

        assertEquals(200, response.getStatus());
        FinancialTransaction ft = (FinancialTransaction) response.getEntity();

        assertNotNull(ft.getAmount());
        assertNotNull(ft.getDate());
        assertNull(ft.getDescription());
        assertEquals("debit", ft.getDebitCreditIndicator());
    }

    @Test
    public void testGetByIdNotFound() throws URISyntaxException {

        when(entityManager.createNamedQuery("Fintransactie.findById", Fintransactie.class)).thenReturn(typedQuery);
        when(typedQuery.setParameter("id", 1L)).thenReturn(typedQuery);
        final ArrayList<Fintransactie> arrayList = new ArrayList<>();
        when(typedQuery.getResultList()).thenReturn(arrayList);

        when(uriInfo.getAbsolutePath()).thenReturn(new URI("transactions/1"));

        Response response = systemUnderTest.get(uriInfo, 1L, "");

        assertEquals(404, response.getStatus());
    }
}

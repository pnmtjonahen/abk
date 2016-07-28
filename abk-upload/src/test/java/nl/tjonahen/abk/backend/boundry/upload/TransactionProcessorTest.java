/*
 * Copyright (C) 2016 Philippe Tjon - A - Hen, philippe@tjonahen.nl
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

import java.util.ArrayList;
import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import javax.persistence.TypedQuery;
import nl.tjonahen.abk.backend.entity.Fintransactie;
import nl.tjonahen.abk.backend.entity.Rekening;
import org.junit.Test;
import org.junit.Before;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import static org.mockito.Mockito.when;
import org.mockito.MockitoAnnotations;

/**
 *
 * @author Philippe Tjon - A - Hen, philippe@tjonahen.nl
 */
public class TransactionProcessorTest {

    @Mock
    private EntityManager entityManager;

    @Mock
    private EntityTransaction entityTransaction;

    @Mock
    private TypedQuery<Rekening> findHashQuery;

    @InjectMocks
    private TransactionProcessor systemUnderTest;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
    }

    /**
     * Test of process method, of class TransactionProcessor.
     */
    @Test
    public void testProcessSameHash()  {
        Fintransactie trans = new Fintransactie();
        trans.setHash("123456789abcdef");
        when(entityManager.createNamedQuery("Fintransactie.findByHash")).thenReturn(findHashQuery);
        when(findHashQuery.setParameter("hash", trans.getHash())).thenReturn(findHashQuery);
        final ArrayList<Rekening> arrayList = new ArrayList<>();
        arrayList.add(new Rekening());
        when(findHashQuery.getResultList()).thenReturn(arrayList);
        systemUnderTest.process(trans);
    }
    @Test
    public void testProcess()  {
        Fintransactie trans = new Fintransactie();
        trans.setHash("123456789abcdef");
        when(entityManager.createNamedQuery("Fintransactie.findByHash")).thenReturn(findHashQuery);
        when(findHashQuery.setParameter("hash", trans.getHash())).thenReturn(findHashQuery);
        when(findHashQuery.getResultList()).thenReturn(new ArrayList<>());
        when(entityManager.getTransaction()).thenReturn(entityTransaction);
        systemUnderTest.process(trans);
    }

}

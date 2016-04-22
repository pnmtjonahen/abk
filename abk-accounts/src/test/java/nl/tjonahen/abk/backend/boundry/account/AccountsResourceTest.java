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
package nl.tjonahen.abk.backend.boundry.account;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import javax.ws.rs.core.UriInfo;
import nl.tjonahen.abk.backend.entity.Rekening;
import nl.tjonahen.abk.backend.model.Account;
import nl.tjonahen.abk.backend.model.Accounts;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import static org.mockito.Mockito.when;
import org.mockito.MockitoAnnotations;

/**
 *
 * @author Philippe Tjon - A - Hen, philippe@tjonahen.nl
 */
public class AccountsResourceTest {

    @Mock
    private EntityManager entityManager;

    @Mock
    private UriInfo uriInfo;

    @Mock
    private TypedQuery<Rekening> typedQuery;

    @InjectMocks
    private AccountsResource systemUnderTest;

    /**
     * Init mocks
     */
    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void testGetNoSaldi() throws URISyntaxException {
        when(entityManager.createNamedQuery("Rekening.findAll", Rekening.class)).thenReturn(typedQuery);
        final ArrayList<Rekening> arrayList = new ArrayList<>();
        final Rekening rekening = new Rekening();
        rekening.setNaam("mijn");
        rekening.setRekening("1234567");
        arrayList.add(rekening);
        when(typedQuery.getResultList()).thenReturn(arrayList);
        when(uriInfo.getAbsolutePath()).thenReturn(new URI(""));

        final Accounts get = systemUnderTest.get();
        assertNotNull(get);
        assertFalse(get.getList().isEmpty());

    }
    @Test
    public void testGet() throws URISyntaxException {
        when(entityManager.createNamedQuery("Rekening.findAll", Rekening.class)).thenReturn(typedQuery);
        final ArrayList<Rekening> arrayList = new ArrayList<>();
        final Rekening rekening = new Rekening();
        rekening.setNaam("mijn");
        rekening.setRekening("1234567");
        rekening.setBeginsaldo(new Double("1000000.00"));
        arrayList.add(rekening);
        when(typedQuery.getResultList()).thenReturn(arrayList);
        when(uriInfo.getAbsolutePath()).thenReturn(new URI(""));

        final Accounts get = systemUnderTest.get();
        assertNotNull(get);
        assertFalse(get.getList().isEmpty());
        
        Account account = get.getList().get(0);
        
        assertEquals("mijn", account.getDescription());
        assertEquals("1234567", account.getNumber());
        assertEquals("1000000.0", account.getStartsaldi());
    }

}

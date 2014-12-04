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
package nl.tjonahen.abk.backend.boundry.contraaccount;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import javax.ws.rs.core.UriInfo;
import nl.tjonahen.abk.backend.entity.Tegenrekening;
import nl.tjonahen.abk.backend.model.ContraAccount;
import nl.tjonahen.abk.backend.model.ContraAccounts;
import org.junit.Test;
import static org.junit.Assert.*;
import org.junit.Before;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import static org.mockito.Mockito.when;
import org.mockito.MockitoAnnotations;

/**
 *
 * @author Philippe Tjon - A - Hen, philippe@tjonahen.nl
 */
public class ContraAccountsResourceTest {
    

    @Mock
    private EntityManager entityManager;

    @Mock
    private UriInfo uriInfo;

    @Mock
    private TypedQuery typedQuery;

    @InjectMocks
    private ContraAccountsResource systemUnderTest;

    /**
     * Init mocks
     */
    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void testGet() throws URISyntaxException {
        when(entityManager.createNamedQuery("Tegenrekening.findAll", Tegenrekening.class)).thenReturn(typedQuery);
        final ArrayList<Tegenrekening> arrayList = new ArrayList<>();
        final Tegenrekening rekening = new Tegenrekening();
        rekening.setNaam("mijn");
        rekening.setRekening("1234567");
        arrayList.add(rekening);
        when(typedQuery.getResultList()).thenReturn(arrayList);
        when(uriInfo.getAbsolutePath()).thenReturn(new URI(""));

        final ContraAccounts get = systemUnderTest.get();
        assertNotNull(get);
        assertFalse(get.getList().isEmpty());
        
        ContraAccount account = get.getList().get(0);
        
        assertEquals("mijn", account.getDescription());
        assertEquals("1234567", account.getNumber());

    }


}

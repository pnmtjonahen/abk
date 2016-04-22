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
package nl.tjonahen.abk.backend.boundry.admin;

import java.util.ArrayList;
import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import nl.tjonahen.abk.backend.entity.CsvReader;
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
public class AdminResourceTest {
    @Mock
    private EntityManager entityManager;

    @Mock
    private TypedQuery<CsvReader> typedQuery;
    
    @InjectMocks
    private AdminResource systemUnderTest;
    /**
     * Init mocks
     */
    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
    }    

    /**
     * Test of getCsvReader method, of class AdminResource.
     */
    @Test
    public void testGetCsvReader()  {
        when(entityManager.createNamedQuery("CsvReader.findAll", CsvReader.class)).thenReturn(typedQuery);
        final ArrayList<CsvReader> arrayList = new ArrayList<>();
        CsvReader reader = new CsvReader();
        reader.setDryRun(false);
        reader.setHeaders(true);
        reader.setId(1L);
        reader.setScript("function parse() {}");
        arrayList.add(reader);
        when(typedQuery.getResultList()).thenReturn(arrayList);
        assertNotNull(systemUnderTest.getCsvReader());
    }
    
}

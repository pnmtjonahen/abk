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
package nl.tjonahen.abk.backend.boundry.costcenter;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;
import nl.tjonahen.abk.backend.entity.Kostenplaats;
import nl.tjonahen.abk.backend.model.CostCenter;
import nl.tjonahen.abk.backend.model.CostCenters;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import static org.mockito.Matchers.any;
import org.mockito.Mock;
import static org.mockito.Mockito.doAnswer;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import org.mockito.MockitoAnnotations;

/**
 *
 * @author Philippe Tjon - A - Hen, philippe@tjonahen.nl
 */
public class CostCentersResourceTest {

    @Mock
    private EntityManager entityManager;

    @Mock
    private UriInfo uriInfo;

    @Mock
    private TypedQuery<Kostenplaats> kostenPLaatsQuery;

    @Mock
    private TypedQuery<Kostenplaats> kostenPLaatsQuery2;

    @InjectMocks
    private CostCentersResource systemUnderTest;

    /**
     * Init mocks
     */
    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void testGetCostCentersEmtpy() throws URISyntaxException {
        when(entityManager.createNamedQuery("Kostenplaats.findByRoot", Kostenplaats.class)).thenReturn(kostenPLaatsQuery);
        final ArrayList<Kostenplaats> arrayList = new ArrayList<>();
        when(kostenPLaatsQuery.getResultList()).thenReturn(arrayList);
        when(uriInfo.getAbsolutePath()).thenReturn(new URI(""));

        final CostCenters get = systemUnderTest.get(uriInfo, 1);
        assertNotNull(get);
        assertTrue(get.getList().isEmpty());

    }
    @Test
    public void testGetCostCenters() throws URISyntaxException {
        when(entityManager.createNamedQuery("Kostenplaats.findByRoot", Kostenplaats.class)).thenReturn(kostenPLaatsQuery);
        final ArrayList<Kostenplaats> arrayList = new ArrayList<>();
        final Kostenplaats kostenplaats = new Kostenplaats();
        kostenplaats.setId(1L);
        kostenplaats.setFilter("filter");
        kostenplaats.setNaam("name");
        kostenplaats.setKostenplaatsCollection(new ArrayList<>());
        kostenplaats.setParent(new Kostenplaats(2L));
        arrayList.add(kostenplaats);
        when(kostenPLaatsQuery.getResultList()).thenReturn(arrayList);
        when(uriInfo.getAbsolutePath()).thenReturn(new URI("costcenters"));

        final CostCenters get = systemUnderTest.get(uriInfo, 1);
        assertNotNull(get);
        assertFalse(get.getList().isEmpty());
        
        CostCenter costcenter = get.getList().get(0);
        
        assertNotNull(costcenter.getParent());
        assertEquals("costcenters/1", costcenter.getMeta().getHref());
        assertNotNull(costcenter.getList());

        
    }

    @Test
    public void testGetCostCenterNotFound() throws URISyntaxException {
        when(entityManager.createNamedQuery("Kostenplaats.findById", Kostenplaats.class)).thenReturn(kostenPLaatsQuery);
        final ArrayList<Kostenplaats> arrayList = new ArrayList<>();
        when(kostenPLaatsQuery.setParameter("id", 1L)).thenReturn(kostenPLaatsQuery2);
        when(kostenPLaatsQuery2.getResultList()).thenReturn(arrayList);
        when(uriInfo.getAbsolutePath()).thenReturn(new URI(""));

        Response response = systemUnderTest.get(uriInfo, 1L, 1);

        assertEquals(404, response.getStatus());

    }

    @Test
    public void testGetCostCenterFound() throws URISyntaxException {
        when(entityManager.createNamedQuery("Kostenplaats.findById", Kostenplaats.class)).thenReturn(kostenPLaatsQuery);
        final ArrayList<Kostenplaats> arrayList = new ArrayList<>();
        final Kostenplaats kostenplaats = new Kostenplaats();
        kostenplaats.setKostenplaatsCollection(new ArrayList<>());
        arrayList.add(kostenplaats);
        when(kostenPLaatsQuery.setParameter("id", 1L)).thenReturn(kostenPLaatsQuery2);
        when(kostenPLaatsQuery2.getResultList()).thenReturn(arrayList);
        when(uriInfo.getAbsolutePath()).thenReturn(new URI("costcenters/1"));

        Response response = systemUnderTest.get(uriInfo, 1L, 1);

        assertEquals(200, response.getStatus());

    }

    @Test
    public void testPost() throws URISyntaxException {

        final CostCenter costCenter = new CostCenter();

        costCenter.setId(1L);
        costCenter.setFilter("");
        costCenter.setName("");

        when(uriInfo.getAbsolutePath()).thenReturn(new URI("costcenters"));

        doAnswer(invocation -> {
            Kostenplaats theobject = (Kostenplaats) invocation.getArguments()[0];
            theobject.setKostenplaatsCollection(new ArrayList<>());
            theobject.setId(1L);
            return null;
        }).when(entityManager).refresh(any(Kostenplaats.class));

        Response response = systemUnderTest.post(uriInfo, costCenter);
        assertEquals(201, response.getStatus());
    }

    @Test
    public void testPutNotFound() {
        final CostCenter costCenter = new CostCenter();

        Response response = systemUnderTest.put(1L, costCenter);
        assertEquals(304, response.getStatus());
    }
    @Test
    public void testPutFound() {
        when(entityManager.find(Kostenplaats.class, 1L)).thenReturn(new Kostenplaats());
        final CostCenter costCenter = new CostCenter();
        Response response = systemUnderTest.put(1L, costCenter);
        assertEquals(200, response.getStatus());
    }
    @Test
    public void testPutWithParent() {
        when(entityManager.find(Kostenplaats.class, 1L)).thenReturn(new Kostenplaats());
        when(entityManager.find(Kostenplaats.class, 2L)).thenReturn(new Kostenplaats());
        final CostCenter costCenter = new CostCenter();
        costCenter.setParent(new CostCenter());
        costCenter.getParent().setId(2L);
        Response response = systemUnderTest.put(1L, costCenter);
        assertEquals(200, response.getStatus());
        
    }
    
    @Test
    public void testDeleteNotFound() {
        Response response = systemUnderTest.delete(1L);
        assertEquals(304, response.getStatus());
    }
    @Test
    public void testDeleteFound() {
        when(entityManager.find(Kostenplaats.class, 1L)).thenReturn(new Kostenplaats());
        Response response = systemUnderTest.delete(1L);
        assertEquals(202, response.getStatus());
        
        verify(entityManager).remove(any(Kostenplaats.class));
    }
    
    @Test
    public void testPostAll() {
        when(entityManager.createNamedQuery("Kostenplaats.deleteAll")).thenReturn(kostenPLaatsQuery);
        
        final ArrayList<CostCenter> costCenters = new ArrayList<>();
        
        final CostCenter costCenter = new CostCenter();
        costCenter.setName("Root");
        costCenter.setFilter("*");
        
        final ArrayList<CostCenter> subCostCenters = new ArrayList<>();
        final CostCenter subCostCenter = new CostCenter();
        subCostCenter.setParent(costCenter);
        subCostCenter.setName("Child");
        subCostCenter.setFilter("ch*");
        final ArrayList<CostCenter> subSubCostCenter = new ArrayList<>();
        subSubCostCenter.add(new CostCenter());
        subCostCenter.setList(subSubCostCenter);
        subCostCenters.add(subCostCenter);
        subCostCenters.add(new CostCenter());
        
        
        costCenter.setList(subCostCenters);
        costCenters.add(costCenter);
        costCenters.add(new CostCenter());
        
        Response response = systemUnderTest.post(costCenters);
        assertEquals(202, response.getStatus());
        
    }
}

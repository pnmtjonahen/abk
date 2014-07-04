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

package nl.tjonahen.abk;

import nl.tjonahen.abk.business.ABKBusiness;
import nl.tjonahen.abk.domein.Kostenplaatsen;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

/**
 *
 * @author Philippe Tjon-A-Hen philippe@tjonahen.nl
 */
public class KostenplaatsenBoundaryTest {
    @Mock
    private ABKBusiness abkBusiness;
         
    @InjectMocks
    private KostenplaatsenBoundary systemUnderTest; 
    
    /**
     * InitMocks DI for mockito
     */
    @Before
    public void before() {
        MockitoAnnotations.initMocks(this);
    }
    
    /**
     * Test getKostenplaatsen.
     */
    @Test
    public void testGetKostenplaatsen() {
        Mockito.when(abkBusiness.getKostenplaatsen()).thenReturn(new Kostenplaatsen());
        Assert.assertNotNull(systemUnderTest.getKostenplaatsen());
        Mockito.verify(abkBusiness).getKostenplaatsen();
    }

    /**
     * Test delete
     */
    @Test
    public void testDelete() {
        systemUnderTest.delete(1L);
        Mockito.verify(abkBusiness).removeKostenplaats(1L);
    }
    
    /**
     * Test update
     */
    @Test
    public void testUpdate() {
        final Kostenplaatsen kostenplaatsen = new Kostenplaatsen();
        systemUnderTest.update(kostenplaatsen);
        Mockito.verify(abkBusiness).update(kostenplaatsen);
    }
            
}

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
import nl.tjonahen.abk.domein.Rekeningen;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

/**
 *
 * @author Philippe Tjon-A-Hen philippe@tjonahen.nl
 */
public class RekeningenBoundaryTest {
    

    @Mock
    private ABKBusiness aBKBusiness;
    
    @InjectMocks
    private RekeningenBoundary systemUnderTest;    
    
    /**
     * Init mocks
     */
    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
    }
    

    /**
     * Test of getRekeningen method, of class RekeningenBoundary.
     */
    @Test
    public void testGetRekeningen() {
        Rekeningen expResult = null;
        Rekeningen result = systemUnderTest.getRekeningen();
        Assert.assertEquals(expResult, result);
    }

    /**
     * Test of update method, of class RekeningenBoundary.
     */
    @Test
    public void testUpdate() {
        Rekeningen updated = null;
        systemUnderTest.update(updated);
    }
    
}

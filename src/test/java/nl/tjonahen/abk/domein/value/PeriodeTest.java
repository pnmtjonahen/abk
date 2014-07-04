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

package nl.tjonahen.abk.domein.value;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import org.junit.Test;

/**
 *
 * @author Philippe Tjon-A-Hen philippe@tjonahen.nl
 */
public class PeriodeTest {
    

    /**
     * Test of getEnd method, of class Periode.
     */
    @Test
    public void testGetEnd() {
        Periode instance = new Periode();
        Maand expResult = null;
        Maand result = instance.getEnd();
        assertEquals(expResult, result);
    }

    /**
     * Test of setEnd method, of class Periode.
     */
    @Test
    public void testSetEnd() {
        Maand end = null;
        Periode instance = new Periode();
        instance.setEnd(end);
    }

    /**
     * Test of getStart method, of class Periode.
     */
    @Test
    public void testGetStart() {
        Periode instance = new Periode();
        Maand expResult = null;
        Maand result = instance.getStart();
        assertEquals(expResult, result);
    }

    /**
     * Test of setStart method, of class Periode.
     */
    @Test
    public void testSetStart() {
        Maand start = null;
        Periode instance = new Periode();
        instance.setStart(start);
    }

    /**
     * Test of isInPeriod method, of class Periode.
     */
    @Test
    public void testIsInPeriodMaand() {
        final Periode instance = new Periode(new Maand(1, 1), new Maand(3, 1));
        
        assertTrue(instance.isInPeriod(new Maand(2, 1)));
        assertFalse(instance.isInPeriod(new Maand(4, 1)));
    }

    /**
     * Test of isInPeriod method, of class Periode.
     */
    @Test
    public void testIsInPeriodKwartaal() {
        final Periode instance = new Periode(new Maand(1, 1), new Maand(3, 1));
        assertTrue(instance.isInPeriod(new Kwartaal(1, 1)));
        assertFalse(instance.isInPeriod(new Kwartaal(2, 1)));
    }
    
}

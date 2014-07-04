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

import java.util.Date;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import org.junit.Test;

/**
 *
 * @author Philippe Tjon-A-Hen philippe@tjonahen.nl
 */
public class MaandTest {

    /**
     * Test of toString method, of class Maand.
     */
    @Test
    public void testToString() {
        Maand instance = new Maand();
        String result = instance.toString();
        assertEquals("0-0", result);
    }

    /**
     * Test of getDate method, of class Maand.
     */
    @Test
    public void testGetDate() {
        Maand instance = new Maand();
        Date result = instance.getDate();
        assertNotNull(result);
    }

    /**
     * Test of getMonth method, of class Maand.
     */
    @Test
    public void testGetMonth() {
        Maand instance = new Maand();
        int expResult = 0;
        int result = instance.getMonth();
        assertEquals(expResult, result);
    }

    /**
     * Test of setMonth method, of class Maand.
     */
    @Test
    public void testSetMonth() {
        int monthNr = 0;
        Maand instance = new Maand();
        instance.setMonth(monthNr);
    }

    /**
     * Test of getYear method, of class Maand.
     */
    @Test
    public void testGetYear() {
        Maand instance = new Maand();
        int expResult = 0;
        int result = instance.getYear();
        assertEquals(expResult, result);
    }

    /**
     * Test of setYear method, of class Maand.
     */
    @Test
    public void testSetYear() {
        int year = 0;
        Maand instance = new Maand();
        instance.setYear(year);
    }

    /**
     * Test of greaterThen method, of class Maand.
     */
    @Test
    public void testGreaterThen() {
        assertTrue(new Maand(6, 2014).greaterThen(new Maand(1, 2014)));
        assertTrue(new Maand(6, 2015).greaterThen(new Maand(6, 2014)));
    
        assertFalse(new Maand(1, 2014).greaterThen(new Maand(6, 2014)));
        assertFalse(new Maand(6, 2014).greaterThen(new Maand(6, 2015)));

    }

    /**
     * Test of isPreviousMonth method, of class Maand.
     */
    @Test
    public void testIsPreviousMonth() {
        assertTrue(new Maand(Maand.DECEMBER, 2013).isPreviousMonth(new Maand(Maand.JANUARY, 2014)));

        assertFalse(new Maand(Maand.DECEMBER, 2013).isPreviousMonth(new Maand(Maand.DECEMBER, 2013)));
        assertFalse(new Maand(Maand.DECEMBER, 2013).isPreviousMonth(new Maand(Maand.FEBRUARY, 2014)));
        assertFalse(new Maand(Maand.NOVEMBER, 2013).isPreviousMonth(new Maand(Maand.JANUARY, 2014)));
        assertFalse(new Maand(Maand.DECEMBER, 2013).isPreviousMonth(new Maand(Maand.NOVEMBER, 2013)));

        assertTrue(new Maand(Maand.JANUARY, 2013).isPreviousMonth(new Maand(Maand.FEBRUARY, 2013)));

    }

    /**
     * Test of previous method, of class Maand.
     */
    @Test
    public void testPrevious() {
        Maand instance = new Maand();
        Maand result = instance.previous();
        assertEquals("-1-0", result.toString());
    }

    /**
     * Test of equals method, of class Maand.
     */
    @Test
    public void testEquals() {
        Maand instance = new Maand(1, 1);
        assertFalse(instance.equals(null));
        assertFalse(instance.equals("1-1"));
        assertTrue(instance.equals(new Maand(1, 1)));
    }

    /**
     * Test of hashCode method, of class Maand.
     */
    @Test
    public void testHashCode() {
        Maand instance = new Maand();
        int expResult = 0;
        int result = instance.hashCode();
        assertEquals(expResult, result);
    }

    /**
     * Test of beforeOrEquals method, of class Maand.
     */
    @Test
    public void testBeforeOrEquals() {
        Maand instance = new Maand(6, 2014);
        assertTrue(instance.beforeOrEquals(new Maand(6, 2015)));
        assertTrue(instance.beforeOrEquals(new Maand(6, 2014)));
        assertTrue(instance.beforeOrEquals(new Maand(7, 2014)));
        assertFalse(instance.beforeOrEquals(new Maand(5, 2014)));
    }

    /**
     * Test of isInQuarter method, of class Maand.
     */
    @Test
    public void testIsInQuarter() {
        assertFalse(new Maand(6, 2014).isInQuarter(new Kwartaal(1, 2014)));
        assertTrue(new Maand(6, 2014).isInQuarter(new Kwartaal(2, 2014)));
        assertFalse(new Maand(6, 2014).isInQuarter(new Kwartaal(3, 2014)));
        assertFalse(new Maand(6, 2014).isInQuarter(new Kwartaal(4, 2014)));
        assertFalse(new Maand(6, 2014).isInQuarter(new Kwartaal(2, 2013)));
        assertFalse(new Maand(6, 2014).isInQuarter(new Kwartaal()));
        assertFalse(new Maand().isInQuarter(new Kwartaal()));
    }

    /**
     * Test of getQuarter method, of class Maand.
     */
    @Test
    public void testGetQuarter() {
        assertEquals("Q4-0", new Maand().getQuarter().toString());
        assertEquals("Q1-2014", new Maand(1, 2014).getQuarter().toString());
        assertEquals("Q1-2014", new Maand(2, 2014).getQuarter().toString());
        assertEquals("Q1-2014", new Maand(3, 2014).getQuarter().toString());
        assertEquals("Q2-2014", new Maand(4, 2014).getQuarter().toString());
        assertEquals("Q2-2014", new Maand(5, 2014).getQuarter().toString());
        assertEquals("Q2-2014", new Maand(6, 2014).getQuarter().toString());
        assertEquals("Q3-2014", new Maand(7, 2014).getQuarter().toString());
        assertEquals("Q3-2014", new Maand(8, 2014).getQuarter().toString());
        assertEquals("Q3-2014", new Maand(9, 2014).getQuarter().toString());
        assertEquals("Q4-2014", new Maand(10, 2014).getQuarter().toString());
        assertEquals("Q4-2014", new Maand(11, 2014).getQuarter().toString());
        assertEquals("Q4-2014", new Maand(12, 2014).getQuarter().toString());
    }

    /**
     * Test of isAfther method, of class Maand.
     */
    @Test
    public void testIsAfther() {
        assertFalse(new Maand().isAfther(new Maand()));
        assertFalse(new Maand(1, 2014).isAfther(new Maand(12, 2014)));
        assertTrue(new Maand(1, 2015).isAfther(new Maand(12, 2014)));
        assertTrue(new Maand(12, 2014).isAfther(new Maand(11, 2014)));
    }

    /**
     * Test of next method, of class Maand.
     */
    @Test
    public void testNext() {
        Maand instance = new Maand();
        Maand result = instance.next();
        assertEquals("1-0", result.toString());
    }

    /**
     * Test of diffInMonth method, of class Maand.
     */
    @Test
    public void testDiffInMonth() {
        Maand other = new Maand();
        Maand instance = new Maand();
        int expResult = 0;
        int result = instance.diffInMonth(other);
        assertEquals(expResult, result);
    }

    /**
     * Test of rollBack method, of class Maand.
     */
    @Test
    public void testRollBack() {
        int n = 0;
        Maand instance = new Maand();
        Maand result = instance.rollBack(n);
        assertEquals("0-0", result.toString());
    }

    /**
     * Test of compareTo method, of class Maand.
     */
    @Test
    public void testCompareTo() {
        assertEquals(0, new Maand().compareTo(new Maand()));
        assertEquals(-1, new Maand(1, 2014).compareTo(new Maand(2, 2014)));
        assertEquals(1, new Maand(2, 2014).compareTo(new Maand(1, 2014)));

        assertEquals(-1, new Maand(1, 2014).compareTo(new Maand(1, 2015)));
        assertEquals(1, new Maand(1, 2015).compareTo(new Maand(1, 2014)));

    }

}

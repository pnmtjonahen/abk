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

import java.util.Calendar;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import org.junit.Test;

/**
 *
 * @author Philippe Tjon-A-Hen philippe@tjonahen.nl
 */
public class KwartaalTest {

    /**
     * Test of date construct
     */
    @Test
    public void testConstr() {
        Calendar cal = Calendar.getInstance();

        cal.set(Calendar.YEAR, 2014);
        cal.set(Calendar.MONTH, 2);
        
        Kwartaal kwartaal = new Kwartaal(cal.getTime());
        
        assertEquals("Q1-2014", kwartaal.toString());
    }

    /**
     * Test of toString method, of class Kwartaal.
     */
    @Test
    public void testToString() {
        Kwartaal instance = new Kwartaal();
        String expResult = "Q0-0";
        String result = instance.toString();
        assertEquals(expResult, result);
    }

    /**
     * Test of getQuarter method, of class Kwartaal.
     */
    @Test
    public void testGetQuarter() {
        Kwartaal instance = new Kwartaal();
        int expResult = 0;
        int result = instance.getQuarter();
        assertEquals(expResult, result);
    }

    /**
     * Test of setQuarter method, of class Kwartaal.
     */
    @Test
    public void testSetQuarter() {
        int quarter = 0;
        Kwartaal instance = new Kwartaal();
        instance.setQuarter(quarter);
    }

    /**
     * Test of getYear method, of class Kwartaal.
     */
    @Test
    public void testGetYear() {
        Kwartaal instance = new Kwartaal();
        int expResult = 0;
        int result = instance.getYear();
        assertEquals(expResult, result);
    }

    /**
     * Test of setYear method, of class Kwartaal.
     */
    @Test
    public void testSetYear() {
        int year = 0;
        Kwartaal instance = new Kwartaal();
        instance.setYear(year);
    }

    /**
     * Test of isPreviousQuarter method, of class Kwartaal.
     */
    @Test
    public void testIsPreviousQuarter() {
        assertFalse(new Kwartaal().isPreviousQuarter(new Kwartaal()));
        assertFalse(new Kwartaal(2, 2014).isPreviousQuarter(new Kwartaal(1, 2014)));
        assertFalse(new Kwartaal(1, 2014).isPreviousQuarter(new Kwartaal(2, 2015)));
        assertFalse(new Kwartaal(2, 2014).isPreviousQuarter(new Kwartaal(1, 2015)));
        assertTrue(new Kwartaal(1, 2014).isPreviousQuarter(new Kwartaal(2, 2014)));
        assertTrue(new Kwartaal(4, 2014).isPreviousQuarter(new Kwartaal(1, 2015)));
    }

    /**
     * Test of isMoreRecentThan method, of class Kwartaal.
     */
    @Test
    public void testIsMoreRecentThan() {
        assertFalse(new Kwartaal().isMoreRecentThan(new Kwartaal()));
        assertFalse(new Kwartaal(1, 2014).isMoreRecentThan(new Kwartaal(2, 2014)));
        assertFalse(new Kwartaal(1, 2014).isMoreRecentThan(new Kwartaal(1, 2015)));
        assertTrue(new Kwartaal(1, 2015).isMoreRecentThan(new Kwartaal(1, 2014)));
        assertTrue(new Kwartaal(2, 2014).isMoreRecentThan(new Kwartaal(1, 2014)));
    }

    /**
     * Test of previous method, of class Kwartaal.
     */
    @Test
    public void testPrevious() {
        assertEquals("Q-1-0", new Kwartaal().previous().toString());
        assertEquals("Q4-2013", new Kwartaal(1, 2014).previous().toString());
        assertEquals("Q1-2014", new Kwartaal(2, 2014).previous().toString());
    }

    /**
     * Test of equals method, of class Kwartaal.
     */
    @Test
    public void testEquals() {
        assertFalse(new Kwartaal(1, 2014).equals(null));
        assertFalse(new Kwartaal(1, 2014).equals("Q1-2014"));
        assertTrue(new Kwartaal(1, 2014).equals(new Kwartaal(1, 2014)));
    }

    /**
     * Test of hashCode method, of class Kwartaal.
     */
    @Test
    public void testHashCode() {
        Kwartaal instance = new Kwartaal();
        int expResult = 0;
        int result = instance.hashCode();
        assertEquals(expResult, result);
    }

    /**
     * Test of isAfther method, of class Kwartaal.
     */
    @Test
    public void testIsAfther() {
        assertFalse(new Kwartaal().isAfther(new Kwartaal()));
        assertFalse(new Kwartaal(1, 2014).isAfther(new Kwartaal(2, 2014)));
        assertTrue(new Kwartaal(1, 2014).isAfther(new Kwartaal(2, 2013)));
        assertTrue(new Kwartaal(2, 2014).isAfther(new Kwartaal(1, 2014)));
    }

    /**
     * Test of next method, of class Kwartaal.
     */
    @Test
    public void testNext() {
        assertEquals("Q1-0", new Kwartaal().next().toString());
        assertEquals("Q2-2014", new Kwartaal(1, 2014).next().toString());
        assertEquals("Q1-2014", new Kwartaal(4, 2013).next().toString());
    }

    /**
     * Test of compareTo method, of class Kwartaal.
     */
    @Test
    public void testCompareTo() {
        assertEquals(0, new Kwartaal().compareTo(new Kwartaal()));
        assertEquals(0, new Kwartaal(1, 2014).compareTo(new Kwartaal(1, 2014)));
        assertEquals(1, new Kwartaal(2, 2014).compareTo(new Kwartaal(1, 2014)));
        assertEquals(-1, new Kwartaal(1, 2014).compareTo(new Kwartaal(2, 2014)));
        assertEquals(1, new Kwartaal(1, 2015).compareTo(new Kwartaal(2, 2014)));
        assertEquals(-1, new Kwartaal(1, 2014).compareTo(new Kwartaal(2, 2015)));
    }

    /**
     * Test of getPeriod method, of class Kwartaal.
     */
    @Test
    public void testGetPeriod() {
        Kwartaal instance = new Kwartaal(1, 2014);
        Periode result = instance.getPeriod();
        assertEquals("1-2014", result.getStart().toString());
        assertEquals("3-2014", result.getEnd().toString());
    }

}

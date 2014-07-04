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

package nl.tjonahen.abk.domein;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import org.junit.Test;

/**
 *
 * @author Philippe Tjon-A-Hen philippe@tjonahen.nl
 */
public class YearReportTest {
    

    /**
     * Test of addKosten method, of class YearReport.
     */
    @Test
    public void testAddKosten() {
        final YearReport instance = new YearReport();
        for (int i = 0; i < 12; i++){
            instance.addKosten(i, 10.0 + i);
        }
        assertEquals("10.00", instance.getCurrent());
        assertEquals("11.00", instance.getCurrentMin1());
        assertEquals("12.00", instance.getCurrentMin2());
        assertEquals("13.00", instance.getCurrentMin3());
        assertEquals("14.00", instance.getCurrentMin4());
        assertEquals("15.00", instance.getCurrentMin5());
        assertEquals("16.00", instance.getCurrentMin6());
        assertEquals("17.00", instance.getCurrentMin7());
        assertEquals("18.00", instance.getCurrentMin8());
        assertEquals("19.00", instance.getCurrentMin9());
        assertEquals("20.00", instance.getCurrentMin10());
        assertEquals("21.00", instance.getCurrentMin11());
    }

    /**
     * Test of get/set method, of class YearReport.
     */
    @Test
    public void testGetSet() {
        final YearReport instance = new YearReport();
        instance.setName("test");
        
        assertEquals("test", instance.getName());
        
        assertNotNull(instance.getChildren());
        instance.setChildren(null);
        assertNull(instance.getChildren());
    }
}

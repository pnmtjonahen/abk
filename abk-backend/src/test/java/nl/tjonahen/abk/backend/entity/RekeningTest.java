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
package nl.tjonahen.abk.backend.entity;

import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author Philippe Tjon - A - Hen, philippe@tjonahen.nl
 */
public class RekeningTest {

    /**
     * Test of hashCode method, of class Rekening.
     */
    @Test
    public void testHashCode() {
        Rekening instance = new Rekening();
        int expResult = 0;
        int result = instance.hashCode();
        assertEquals(expResult, result);
    }

    /**
     * Test of equals method, of class Rekening.
     */
    @Test
    public void testEquals() {
        Rekening instance = new Rekening();
        instance.setRekening("5521208");
        assertFalse(instance.equals("5521208"));
        final Rekening other = new Rekening();
        assertFalse(instance.equals(other));
        other.setRekening("NL31 INGB 0005521208");
        assertFalse(instance.equals(other));

        assertTrue(instance.equals(instance));
        
    }

    /**
     * Test of toString method, of class Rekening.
     */
    @Test
    public void testToString() {
        Rekening instance = new Rekening();
        String expResult = "nl.tjonahen.abk.backend.entity.Rekening[ rekening=null ]";
        String result = instance.toString();
        assertEquals(expResult, result);
    }
    
}

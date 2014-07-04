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

package nl.tjonahen.abk.domein.entity;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import org.junit.Test;

/**
 *
 * @author Philippe Tjon-A-Hen philippe@tjonahen.nl
 */
public class RekeningTest {
    

    /**
     * Test of get/set ... method, of class Rekening.
     */
    @Test
    public void testGetSet() {
        Rekening instance = new Rekening();
        instance.setBeginSaldo(0.0);
        assertEquals(0.0, instance.getBeginSaldo(), 0.0);
        
        instance.setNaam("dummy");
        assertEquals("dummy", instance.getNaam());
        
        instance.setRekening("1234567890");
        assertEquals("1234567890", instance.getRekening());
        
        assertNotNull(instance.getTransacties());
    }


    /**
     * Test of hashCode method, of class Rekening.
     */
    @Test
    public void testHashCode() {
        final Rekening rekening = new Rekening();
        rekening.setRekening("12345");
        assertEquals("12345".hashCode(), rekening.hashCode());
    }

    /**
     * Test of equals method, of class Rekening.
     */
    @Test
    public void testEquals() {
        assertFalse(new Rekening().equals(null));
        assertFalse(new Rekening().equals("123456"));
        
        assertTrue(new Rekening().equals(new Rekening()));
        
        final Rekening rekening = new Rekening();
        rekening.setRekening("12345");
        assertFalse(new Rekening().equals(rekening));
        assertFalse(rekening.equals(new Rekening()));
    }

    /**
     * Test of toString method, of class Rekening.
     */
    @Test
    public void testToString() {
        final Rekening rekening = new Rekening();
        rekening.setRekening("12345");
        assertEquals("nl.tjonahen.abk.domein.Rekening[ rekening=12345 ]", rekening.toString());
    }

}

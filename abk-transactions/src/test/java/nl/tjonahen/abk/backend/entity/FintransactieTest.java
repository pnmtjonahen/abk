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
public class FintransactieTest {

    /**
     * Test of hashCode method, of class Fintransactie.
     */
    @Test
    public void testHashCode() {
        Fintransactie instance = new Fintransactie();
        int expResult = 0;
        int result = instance.hashCode();
        assertEquals(expResult, result);
    }

    /**
     * Test of equals method, of class Fintransactie.
     */
    @Test
    public void testEquals() {
        Fintransactie instance = new Fintransactie();
        assertFalse(instance.equals("a string"));
        Fintransactie other = new Fintransactie();
        assertTrue(instance.equals(other));
        other.setId(10L);
        assertFalse(instance.equals(other));
        assertFalse(other.equals(instance));

        instance.setId(10L);
        assertTrue(instance.equals(other));

    }

    /**
     * Test of toString method, of class Fintransactie.
     */
    @Test
    public void testToString() {
        Fintransactie instance = new Fintransactie();
        String expResult = "nl.tjonahen.abk.backend.entity.Fintransactie[ id=null ]";
        String result = instance.toString();
        assertEquals(expResult, result);
    }
    
    @Test
    public void testGetterSetter() {
        Fintransactie instance = new Fintransactie();
        
        instance.setCode(null);
        instance.setAccountRekening(null);
        assertNull(instance.getAccountRekening());
        instance.setMutatiesoort(null);
        instance.setMededeling(null);
        instance.setRekening(null);
        instance.setTegenrekeningnaam(null);
        instance.setTegenrekeningrekening(null);
        
        
    }

}

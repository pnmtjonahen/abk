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

import java.util.Collection;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author Philippe Tjon - A - Hen, philippe@tjonahen.nl
 */
public class KostenplaatsTest {

    /**
     * Test of hashCode method, of class Kostenplaats.
     */
    @Test
    public void testHashCode() {
        Kostenplaats instance = new Kostenplaats();
        assertEquals(0, instance.hashCode());
        instance.setId(10L);
        assertEquals(instance.getId().hashCode(), instance.hashCode());

    }

    /**
     * Test of equals method, of class Kostenplaats.
     */
    @Test
    public void testEquals() {
        Kostenplaats instance = new Kostenplaats();
        assertFalse(instance.equals("a string"));
        Kostenplaats other = new Kostenplaats();
        assertTrue(instance.equals(other));
        other.setId(10L);
        assertFalse(instance.equals(other));
        assertFalse(other.equals(instance));

        instance.setId(10L);
        assertTrue(instance.equals(other));

    }

    /**
     * Test of toString method, of class Kostenplaats.
     */
    @Test
    public void testToString() {
        Kostenplaats instance = new Kostenplaats();
        String result = instance.toString();
        assertEquals("nl.tjonahen.abk.backend.Kostenplaats[ id=null ]", result);
    }

}

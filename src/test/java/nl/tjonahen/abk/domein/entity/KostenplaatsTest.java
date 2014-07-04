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

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import nl.tjonahen.abk.domein.value.Maand;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import org.junit.Test;

/**
 *
 * @author Philippe Tjon-A-Hen philippe@tjonahen.nl
 */
public class KostenplaatsTest {
    
    /**
     * Test of bepaalBedrag method, of class Kostenplaats.
     */
    @Test
    public void testBepaalBedragMaandList() {
        List<Transactie> transacties = new ArrayList<>();
        
        final Transactie transactie = new Transactie();
        transactie.setMededeling("mededeling dummy text");
        transactie.setTegenrekeningRekening("");
        transactie.setTegenrekeningNaam("jummy");
        transactie.setBedrag(100.0);
        transactie.setBijAf("Af");
        Calendar cal = Calendar.getInstance();
        cal.set(Calendar.YEAR, 2014);
        cal.set(Calendar.MONTH, 0);
        transactie.setDatum(cal.getTime());
        
        transacties.add(transactie);
        
        Kostenplaats instance = new Kostenplaats();
        instance.setFilter(".*jummy.*");
        assertEquals(-100.0, instance.bepaalBedrag(new Maand(1, 2014), transacties), 0.0);

        instance.setFilter("contains:dummy");
        assertEquals(-100.0, instance.bepaalBedrag(new Maand(1, 2014), transacties), 0.0);

        instance.setFilter("equals:jummy  mededeling dummy text");
        assertEquals(-100.0, instance.bepaalBedrag(new Maand(1, 2014), transacties), 0.0);

        instance.setFilter(null);
        assertEquals(0.0, instance.bepaalBedrag(new Maand(1, 2014), transacties), 0.0);

        instance.setFilter("");
        assertEquals(0.0, instance.bepaalBedrag(new Maand(1, 2014), transacties), 0.0);
    }

    
    @Test
    public void testGetSet() {
        Kostenplaats kostenplaats = new Kostenplaats(1L);
        
        kostenplaats.setId(2L);
        assertEquals(2L, kostenplaats.getId().longValue());
        
        kostenplaats.setFilter("dummy");
        assertEquals("dummy", kostenplaats.getFilter());
        
        kostenplaats.setNaam("dummy");
        assertEquals("dummy", kostenplaats.getNaam());
        
        kostenplaats.setParent(new Kostenplaats());
        assertNotNull(kostenplaats.getParent());
        
        kostenplaats.setRoot(Boolean.TRUE);
        assertTrue(kostenplaats.getRoot());
        
        kostenplaats.setSubkostenplaatsen(new ArrayList<Kostenplaats>());
        assertNotNull(kostenplaats.getSubkostenplaatsen());
        
        
    }

    /**
     * Test of hashCode method, of class Kostenplaats.
     */
    @Test
    public void testHashCode() {
        final Kostenplaats instance = new Kostenplaats();
        instance.setId(1L);
        assertEquals(1, instance.hashCode());
    }

    /**
     * Test of equals method, of class Kostenplaats.
     */
    @Test
    public void testEquals() {
        assertFalse(new Kostenplaats().equals(null));
        assertFalse(new Kostenplaats().equals("123456"));
        
        assertTrue(new Kostenplaats().equals(new Kostenplaats()));
        
        final Kostenplaats instance = new Kostenplaats();
        instance.setId(1l);
        assertFalse(new Kostenplaats().equals(instance));
        assertFalse(instance.equals(new Kostenplaats()));
    }

    /**
     * Test of toString method, of class Kostenplaats.
     */
    @Test
    public void testToString() {
        final Kostenplaats instance = new Kostenplaats();
        instance.setId(1L);
        assertEquals("nl.tjonahen.abk.domein.Kostenplaats[ id=1 ]", instance.toString());
    }    
}

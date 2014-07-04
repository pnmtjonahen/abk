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

import java.util.Calendar;
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
public class TransactieTest {

    /**
     * Test of get/set ...method, of class Transactie.
     */
    @Test
    public void testGetSet() {
        Transactie instance = new Transactie(2L);
        
        instance.setAccount(new Rekening());
        assertNotNull(instance.getAccount());
        
        instance.setBedrag(0.0);
        assertEquals(0.0, instance.getBedrag(), 0.0);
        
        instance.setBijAf("plus");
        assertEquals("plus", instance.getBijAf());
        
        instance.setCode("code");
        assertEquals("code", instance.getCode());
        
        instance.setDatum(new Date());
        assertNotNull(instance.getDatum());
        
        instance.setId(1L);
        assertEquals(1L, instance.getId().longValue());
        
        instance.setMededeling("mededeling");
        assertEquals("mededeling", instance.getMededeling());
        
        instance.setMutatiesoort("INC");
        assertEquals("INC", instance.getMutatiesoort());
        
        instance.setRekening("12345");
        assertEquals("12345", instance.getRekening());
        
        
        instance.setTegenrekeningNaam("tegnaam");
        assertEquals("tegnaam", instance.getTegenrekeningNaam());
        
        instance.setTegenrekeningRekening("tegrek");
        assertEquals("tegrek", instance.getTegenrekeningRekening());
    }

    /**
     * Test of hashCode method, of class Transactie.
     */
    @Test
    public void testHashCode() {
        Transactie instance = new Transactie();
        instance.setId(1L);
        assertEquals(1, instance.hashCode());
    }

    /**
     * Test of equals method, of class Transactie.
     */
    @Test
    public void testEquals() {
        assertFalse(new Transactie().equals(null));
        assertFalse(new Transactie().equals("1"));
        assertTrue(new Transactie().equals(new Transactie()));
        final Transactie transactie = new Transactie();
        transactie.setId(1L);
        assertFalse(transactie.equals(new Transactie()));
        assertFalse(new Transactie().equals(transactie));
        assertTrue(transactie.equals(transactie));
    }

    /**
     * Test of toString method, of class Transactie.
     */
    @Test
    public void testToString() {
        Transactie instance = new Transactie();
        instance.setId(1L);
        assertEquals("nl.tjonahen.abk.domein.Transactie[ id=1 ]", instance.toString());
        
    }

    /**
     * Test of isAf method, of class Transactie.
     */
    @Test
    public void testIsAf() {
        Transactie transactie = new Transactie();
        
        transactie.setBijAf("Af");
        assertTrue(transactie.isAf());
        
        transactie.setBijAf("Bij");
        assertFalse(transactie.isAf());
    }

    /**
     * Test of bepaalBedrag method, of class Transactie.
     */
    @Test
    public void testBepaalBedrag() {
        Transactie transactie = new Transactie();
        transactie.setBedrag(100.0);
        transactie.setBijAf("Af");
        assertEquals(-100.0, transactie.bepaalBedrag(), 0.0);
        
        transactie.setBijAf("Bij");
        assertEquals(100.0, transactie.bepaalBedrag(), 0.0);
    }

    /**
     * Test of getMonth method, of class Transactie.
     */
    @Test
    public void testGetMonth() {
        
        Calendar cal = Calendar.getInstance();
        cal.set(Calendar.YEAR, 2014);
        cal.set(Calendar.MONTH, 0);
        
        Transactie transactie = new Transactie();
        transactie.setDatum(cal.getTime());
        
        assertEquals("1-2014", transactie.getMonth().toString());
    }
    
}

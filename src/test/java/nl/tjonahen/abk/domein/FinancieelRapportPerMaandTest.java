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

import java.util.ArrayList;
import nl.tjonahen.abk.domein.entity.Kostenplaats;
import nl.tjonahen.abk.domein.value.Maand;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import org.junit.Test;

/**
 *
 * @author Philippe Tjon-A-Hen philippe@tjonahen.nl
 */
public class FinancieelRapportPerMaandTest {
    

    /**
     * Test of getKostenPlaats, setKostenPlaats methods, of class FinancieelRapportPerMaand.
     */
    @Test
    public void testGetSetKostenPlaats() {
        FinancieelRapportPerMaand instance = new FinancieelRapportPerMaand();
        assertNull(instance.getKostenPlaats());
        
        instance.setKostenPlaats(new Kostenplaats());
        assertNotNull(instance.getKostenPlaats());
        
    }


    /**
     * Test of getKosten, setKosten methods, of class FinancieelRapportPerMaand.
     */
    @Test
    public void testGetKosten() {
        FinancieelRapportPerMaand instance = new FinancieelRapportPerMaand();
        assertNotNull(instance.getKosten());

        instance.setKosten(new ArrayList<MaandBedrag>());
        assertNotNull(instance.getKosten());
    }


    /**
     * Test of get/set-SubFinancieelRapportPerMaand method, of class FinancieelRapportPerMaand.
     */
    @Test
    public void testGetSubFinancieelRapportPerMaand() {
        FinancieelRapportPerMaand instance = new FinancieelRapportPerMaand();
        assertNotNull(instance.getSubFinancieelRapportPerMaand());
        
        instance.setSubFinancieelRapportPerMaand(new ArrayList<FinancieelRapportPerMaand>());
        
        assertNotNull(instance.getSubFinancieelRapportPerMaand());
    }


    /**
     * Test of getMonthBedrag method, of class FinancieelRapportPerMaand.
     */
    @Test
    public void testGetMonthBedrag() {
        FinancieelRapportPerMaand instance = new FinancieelRapportPerMaand();
        instance.setKosten(new ArrayList<MaandBedrag>());
        
        assertEquals(0.0, instance.getMonthBedrag(null), 0.0);
        final ArrayList<MaandBedrag> kosten = new ArrayList<>();
        kosten.add(new MaandBedrag(new Maand(1, 2014), 100.0));
        instance.setKosten(kosten);
        assertEquals(0.0, instance.getMonthBedrag(null), 0.0);
        assertEquals(0.0, instance.getMonthBedrag(new Maand(2, 2014)), 0.0);
        assertEquals(100.0, instance.getMonthBedrag(new Maand(1, 2014)), 0.0);
        
        
    }

    /**
     * Test of updateKosten method, of class FinancieelRapportPerMaand.
     */
    @Test
    public void testUpdateKosten() {
        Maand start = null;
        Double bedrag = null;
        FinancieelRapportPerMaand instance = new FinancieelRapportPerMaand();
        instance.updateKosten(start, bedrag);
    }
    
}

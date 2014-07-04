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
import java.util.List;
import nl.tjonahen.abk.domein.entity.Kostenplaats;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import org.junit.Test;

/**
 *
 * @author Philippe Tjon-A-Hen philippe@tjonahen.nl
 */
public class KostenplaatsenTest {
    

    /**
     * Test of getKostenplaats method, of class Kostenplaatsen.
     */
    @Test
    public void testGetKostenplaats() {
        Kostenplaatsen instance = new Kostenplaatsen();
        List<Kostenplaats> expResult = null;
        List<Kostenplaats> result = instance.getKostenplaats();
        assertNotNull(result);
    }
    /**
     * Test of getKostenplaats method, of class Kostenplaatsen.
     */
    @Test
    public void testGetKostenplaatsList() {
        final ArrayList<Kostenplaats> arrayList = new ArrayList<>();
        Kostenplaatsen instance = new Kostenplaatsen(arrayList);
        
        List<Kostenplaats> result = instance.getKostenplaats();
        assertTrue(arrayList == result);
    }
    
}

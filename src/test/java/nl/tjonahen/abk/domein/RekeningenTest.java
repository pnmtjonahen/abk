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
import nl.tjonahen.abk.domein.entity.Rekening;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import org.junit.Test;

/**
 *
 * @author Philippe Tjon-A-Hen philippe@tjonahen.nl
 */
public class RekeningenTest {
    

    /**
     * Test of getRekening method, of class Rekeningen.
     */
    @Test
    public void testGetRekening() {
        assertNotNull(new Rekeningen().getRekening());
        final ArrayList<Rekening> rekeningen = new ArrayList<>();
        final List<Rekening> result = new Rekeningen(rekeningen).getRekening();
        assertNotNull(result);
        assertTrue(rekeningen == result);
    }
    
}

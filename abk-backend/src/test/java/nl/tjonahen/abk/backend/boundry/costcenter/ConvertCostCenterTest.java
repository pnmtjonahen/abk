/*
 * Copyright (C) 2014 Philippe Tjon - A - Hen, philippe@tjonahen.nl
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
package nl.tjonahen.abk.backend.boundry.costcenter;

import java.util.ArrayList;
import nl.tjonahen.abk.backend.entity.Kostenplaats;
import nl.tjonahen.abk.backend.model.CostCenter;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import org.junit.Test;

/**
 *
 * @author Philippe Tjon - A - Hen, philippe@tjonahen.nl
 */
public class ConvertCostCenterTest {

    @Test
    public void testConvert() {
        ConvertCostCenter ccc = new ConvertCostCenter(0);
        final Kostenplaats kostenplaats = new Kostenplaats();
        final ArrayList<Kostenplaats> arrayList = new ArrayList<>();
        final Kostenplaats kostenplaats1 = new Kostenplaats();
        kostenplaats1.setKostenplaatsCollection(new ArrayList<>());
        arrayList.add(kostenplaats1);
        kostenplaats.setKostenplaatsCollection(arrayList);
        final CostCenter convert = ccc.convert(kostenplaats);
        
        assertNotNull(convert);
        
        assertNotNull(convert.getList());
        assertEquals(1, convert.getList().size());
        
    }
    
}

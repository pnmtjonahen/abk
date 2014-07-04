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

import nl.tjonahen.abk.domein.value.Maand;
import org.junit.Assert;
import org.junit.Test;

/**
 *
 * @author Philippe Tjon-A-Hen philippe@tjonahen.nl
 */
public class MaandBedragTest {

    /**
     * Default
     */
    @Test
    public void testPojoDefault() {
        final MaandBedrag maandBedrag = new MaandBedrag();
        
        Assert.assertNull(maandBedrag.getBedrag());
        Assert.assertNull(maandBedrag.getMaand());
    }
    
    /**
     * Construct
     */
    @Test
    public void testPojoConstructor() {
        final MaandBedrag maandBedrag = new MaandBedrag(new Maand(), 0.0);

        Assert.assertNotNull(maandBedrag.getBedrag());
        Assert.assertNotNull(maandBedrag.getMaand());
    }
    /**
     * Setter
     */
    @Test
    public void testPojoDefaultSetter() {
        final MaandBedrag maandBedrag = new MaandBedrag();

        maandBedrag.setMaand(new Maand());
        maandBedrag.setBedrag(0.0);
        Assert.assertNotNull(maandBedrag.getBedrag());
        Assert.assertNotNull(maandBedrag.getMaand());
    }
        
}

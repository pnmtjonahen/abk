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
public class CsvReaderTest {
    

    /**
     * Test of getId method, of class CsvReader.
     */
    @Test
    public void testId() {
        CsvReader instance = new CsvReader();
        instance.setId(null);
        assertNull(instance.getId());
    }


    /**
     * Test of isHeaders method, of class CsvReader.
     */
    @Test
    public void testIsHeaders() {
        CsvReader instance = new CsvReader();
        instance.setHeaders(true);
        assertTrue(instance.isHeaders());
    }


    /**
     * Test of isDryRun method, of class CsvReader.
     */
    @Test
    public void testIsDryRun() {
        CsvReader instance = new CsvReader();
        instance.setDryRun(true);
        assertTrue(instance.isDryRun());
    }

    
}

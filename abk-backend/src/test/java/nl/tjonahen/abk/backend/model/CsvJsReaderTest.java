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
package nl.tjonahen.abk.backend.model;

import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author Philippe Tjon - A - Hen, philippe@tjonahen.nl
 */
public class CsvJsReaderTest {
    

    /**
     * Test of getHeaders method, of class CsvJsReader.
     */
    @Test
    public void testGetters() {
        CsvJsReader instance = new CsvJsReader(Boolean.TRUE, Boolean.TRUE, "dummy");
        assertEquals("true", instance.getDryrun());
        instance.setDryrun("dummy");
        assertEquals("dummy", instance.getDryrun());
        assertEquals("true",instance.getHeaders());
        instance.setHeaders("dummy");
        assertEquals("dummy",instance.getHeaders());
        assertEquals("dummy", instance.getScript());
        instance.setScript("");
        assertEquals("", instance.getScript());
    }

    
}

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
package nl.tjonahen.abk.backend.model;

import nl.tjonahen.abk.backend.model.OrderBy;
import java.util.List;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import org.junit.Test;

/**
 *
 * @author Philippe Tjon - A - Hen, philippe@tjonahen.nl
 */
public class OrderByTest {
    

    /**
     * Test of getFields method, of class OrderBy.
     */
    @Test
    public void testDefault() {
        
        OrderBy orderBy = new OrderBy("");
        
        assertTrue(orderBy.isDesc());
        assertEquals(0, orderBy.getFields().size());
    }
    
    /**
     * Test of getFields method, of class OrderBy.
     */
    @Test
    public void testFieldDefault() {
        
        OrderBy orderBy = new OrderBy("field");
        
        assertTrue(orderBy.isDesc());
        final List<String> fields = orderBy.getFields();
        assertEquals(1, fields.size());
        assertEquals("field", fields.get(0));
    }

    /**
     * Test of getFields method, of class OrderBy.
     */
    @Test
    public void testFieldAsc() {
        
        OrderBy orderBy = new OrderBy("field ASC");
        
        assertFalse(orderBy.isDesc());
        final List<String> fields = orderBy.getFields();
        assertEquals(1, fields.size());
        assertEquals("field", fields.get(0));
        
    }
    
    /**
     * Test of getFields method, of class OrderBy.
     */
    @Test
    public void testFieldUnknown() {
        
        OrderBy orderBy = new OrderBy("field ASCI");
        
        assertTrue(orderBy.isDesc());
        final List<String> fields = orderBy.getFields();
        assertEquals(1, fields.size());
        assertEquals("field", fields.get(0));
        
    }

}

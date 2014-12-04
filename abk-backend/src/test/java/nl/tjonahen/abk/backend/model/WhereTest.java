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

import nl.tjonahen.abk.backend.model.Where;
import static org.junit.Assert.assertEquals;
import org.junit.Test;

/**
 *
 * @author Philippe Tjon - A - Hen, philippe@tjonahen.nl
 */
public class WhereTest {
    

    /**
     * Test of getWhereClause method, of class Where.
     */
    @Test
    public void testGetWhereClause() {
        Where instance = new Where("");
        
        assertEquals(0, instance.getWhereClause().size());
    }
    /**
     * Test of getWhereClause method, of class Where.
     */
    @Test
    public void testGetWhereClauseWithValues() {
        Where instance = new Where("key=value,key2=value2");
        
        assertEquals(2, instance.getWhereClause().size());
    }
     /**
     * Test of getWhereClause method, of class Where.
     */
    @Test
    public void testGetWhereClauseWithBrokenValues() {
        Where instance = new Where("key=");
        
        assertEquals(0, instance.getWhereClause().size());
    }
     /**
     * Test of getWhereClause method, of class Where.
     */
    @Test
    public void testGetWhereClauseWithBrokenKey() {
        Where instance = new Where("=value");
        
        assertEquals(0, instance.getWhereClause().size());
    }
    
}

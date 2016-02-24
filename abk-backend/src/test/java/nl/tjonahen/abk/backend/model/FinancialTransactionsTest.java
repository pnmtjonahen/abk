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

import java.util.ArrayList;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import org.junit.Test;

/**
 *
 * @author Philippe Tjon - A - Hen, philippe@tjonahen.nl
 */
public class FinancialTransactionsTest {
    
    @Test
    public void testDefaultConstr() {
        assertNotNull(new FinancialTransactions());
    }

    /**
     * Test of updateHref method, of class FinancialTransactions.
     */
    @Test
    public void testUpdateHrefEmptyList() {
        FinancialTransactions instance = new FinancialTransactions(new ArrayList<>(), 0, 10, 5);
        instance.updateHref("/base", "");
        
        assertEquals("/base?offset=0", instance.getFirst().getMeta().getHref());
        assertEquals("/base?offset=5", instance.getLast().getMeta().getHref());
        assertNull(instance.getNext());
        assertNull(instance.getPrevious());
        
    }

    /**
     * Test of updateHref method, of class FinancialTransactions.
     */
    @Test
    public void testUpdateHrefWithParamsAndNextPrevious() {
        FinancialTransactions instance = new FinancialTransactions(new ArrayList<>(), 5, 10, 100);
        instance.updateHref("/base", "datum=''");
        
        assertEquals("/base?offset=0&fields=datum=''", instance.getFirst().getMeta().getHref());
        assertEquals("/base?offset=90&fields=datum=''", instance.getLast().getMeta().getHref());
        assertEquals("/base?offset=15&fields=datum=''", instance.getNext().getMeta().getHref());
        assertEquals("/base?offset=0&fields=datum=''", instance.getPrevious().getMeta().getHref());
        
    }

    
}

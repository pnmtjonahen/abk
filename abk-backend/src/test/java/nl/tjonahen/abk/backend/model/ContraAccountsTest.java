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
import org.junit.Test;

/**
 *
 * @author Philippe Tjon - A - Hen, philippe@tjonahen.nl
 */
public class ContraAccountsTest {
    

    /**
     * Test of getList method, of class ContraAccounts.
     */
    @Test
    public void testGetList() {
        final ArrayList<ContraAccount> arrayList = new ArrayList<>();
        ContraAccount account = new ContraAccount();
        account.setDescription("Test rekening");
        account.setNumber("123456789");
        arrayList.add(account);
        ContraAccounts instance = new ContraAccounts(arrayList);
        
        assertNotNull(instance.getList());
        assertEquals(1, instance.getList().size());
        assertEquals("Test rekening", instance.getList().get(0).getDescription());
        assertEquals("123456789", instance.getList().get(0).getNumber());
    }
    
    
}

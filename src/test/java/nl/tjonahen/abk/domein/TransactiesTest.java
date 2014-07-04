/*
 * Copyright (C) 2013 Philippe Tjon-A-Hen philippe@tjonahen.nl
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

import nl.tjonahen.abk.domein.entity.Transactie;
import java.util.ArrayList;
import java.util.List;
import org.junit.Assert;
import static org.junit.Assert.assertNotNull;
import org.junit.Test;

/**
 *
 * @author Philippe Tjon-A-Hen philippe@tjonahen.nl
 */
public class TransactiesTest {

    /**
     * -
     */
    @Test
    public void testPagingEmtpyList() {
        final Transacties transacties = new Transacties(1, new ArrayList<Transactie>());
        
        Assert.assertEquals(0, transacties.getAantalPaginas());
        Assert.assertEquals(1, transacties.getHuidigePagina());
        
    }

    /**
     * -
     */
    @Test
    public void testPagingBeforeFirst() {
        final Transacties transacties = new Transacties(0, getTransacties(20));
        
        Assert.assertEquals(2, transacties.getAantalPaginas());
        Assert.assertEquals(1, transacties.getHuidigePagina());
        
    }
    
    /**
     * -
     */
    @Test
    public void testPagingAfterLast() {
        final Transacties transacties = new Transacties(3, getTransacties(20));
        
        Assert.assertEquals(2, transacties.getAantalPaginas());
        Assert.assertEquals(2, transacties.getHuidigePagina());
        
    }
    
    @Test
    public void testGetTransactions() {
        Transacties transacties = new Transacties();
        assertNotNull(transacties.getTransactie());
        
        transacties = new Transacties(3, getTransacties(25));
        assertNotNull(transacties.getTransactie());
    }
    

    private List<Transactie> getTransacties(final int count) {
        final List<Transactie> transacties = new ArrayList<>();
        
        for (int i = 0; i < count; i++) {
            transacties.add(new Transactie());
        }
        
        return transacties;
    }
}

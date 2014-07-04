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

package nl.tjonahen.abk;

import nl.tjonahen.abk.business.ABKBusiness;
import nl.tjonahen.abk.domein.Transacties;
import static org.junit.Assert.assertNull;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

/**
 *
 * @author Philippe Tjon-A-Hen philippe@tjonahen.nl
 */
public class TransactiesBoundaryTest {
    
    @Mock
    private ABKBusiness aBKBusiness;
    
    @InjectMocks
    private TransactiesBoundary systemUnderTest;    
    
    /**
     * Init mocks
     */
    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    /**
     * Test of getTransacties method, of class TransactiesBoundary.
     */
    @Test
    public void testGetTransacties() throws Exception {
        Transacties result = systemUnderTest.getTransacties();
        assertNull(result);
    }

    /**
     * Test of getTransactiesPagina method, of class TransactiesBoundary.
     */
    @Test
    public void testGetTransactiesPagina() throws Exception {
        int pagina = 0;
        Transacties result = systemUnderTest.getTransactiesPagina(pagina);
        assertNull(result);
    }
    
}

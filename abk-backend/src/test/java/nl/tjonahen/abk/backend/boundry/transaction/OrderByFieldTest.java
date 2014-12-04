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
package nl.tjonahen.abk.backend.boundry.transaction;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.Path;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import javax.persistence.metamodel.SingularAttribute;
import nl.tjonahen.abk.backend.entity.Fintransactie;
import static org.junit.Assert.assertNotNull;
import org.junit.Before;
import org.junit.Test;
import static org.mockito.Matchers.any;
import org.mockito.Mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import org.mockito.MockitoAnnotations;

/**
 *
 * @author Philippe Tjon - A - Hen, philippe@tjonahen.nl
 */
public class OrderByFieldTest {
    
    @Mock
    private CriteriaBuilder cb;
    @Mock
    private Root<Fintransactie> root;
    @Mock
    private Path<String> expression;
    @Mock
    private Predicate predicate;


    /**
     * Init mocks
     */
    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
    }    
    /**
     * Test of valueOf method, of class FinancialTransactionOrderField.
     */
    @Test
    public void testValueOf() {
        assertNotNull(OrderByField.valueOf("id".toUpperCase()));
    }

    @Test(expected = IllegalArgumentException.class)
    public void testValueOfUnknonw() {
        assertNotNull(OrderByField.valueOf("bogus".toUpperCase()));
    }
    
    @Test
    public void testDesc() {
        when(root.get(any(SingularAttribute.class))).thenReturn(expression);
        OrderByField.valueOf("ID").order(true, cb, root);
        
        verify(cb).desc(expression);
    }
    @Test
    public void testAsc() {
        when(root.get(any(SingularAttribute.class))).thenReturn(expression);
        OrderByField.valueOf("ID").order(false, cb, root);
        
        verify(cb).asc(expression);
    }
    
}

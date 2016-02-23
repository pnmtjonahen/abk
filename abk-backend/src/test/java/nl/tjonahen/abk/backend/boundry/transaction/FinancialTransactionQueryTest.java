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

import javax.persistence.EntityManager;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Expression;
import javax.persistence.criteria.Path;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import nl.tjonahen.abk.backend.entity.Fintransactie;
import nl.tjonahen.abk.backend.entity.Fintransactie_;
import nl.tjonahen.abk.backend.model.OrderBy;
import nl.tjonahen.abk.backend.model.Where;
import static org.junit.Assert.assertNotNull;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import static org.mockito.Matchers.any;
import org.mockito.Mock;
import static org.mockito.Mockito.when;
import org.mockito.MockitoAnnotations;

/**
 *
 * @author Philippe Tjon - A - Hen, philippe@tjonahen.nl
 */
public class FinancialTransactionQueryTest {
    
    @Mock
    private EntityManager entityManager;

    @Mock
    private  CriteriaBuilder cb;
    @Mock
    private CriteriaQuery<Fintransactie> cq;
    @Mock
    private Root<Fintransactie> fintransactieRoot;
            
    @Mock
    private CriteriaQuery<Long> cqCount;

    @Mock
    private Predicate predicate;

    
    @Mock
    private Path<?> expression;
    
    @InjectMocks
    private FinancialTransactionQuery systemUnderTest;

    /**
     * Init mocks
     */
    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
    }
    
    @Test
    public void testSelectBuilderNoWhereOrderDesc() {
        when(entityManager.getCriteriaBuilder()).thenReturn(cb);
        when(cb.createQuery(Fintransactie.class)).thenReturn(cq);
        when(cq.from(Fintransactie.class)).thenReturn(fintransactieRoot);
        
        final CriteriaQuery<Fintransactie> createSelect = systemUnderTest.where(new Where(""))
                                                                        .orderBy(new OrderBy("date"))
                                                                        .createSelect();
        
        assertNotNull(createSelect);
    }
    @Test
    public void testSelectBuilderNoWhereOrderAsc() {
        when(entityManager.getCriteriaBuilder()).thenReturn(cb);
        when(cb.createQuery(Fintransactie.class)).thenReturn(cq);
        when(cq.from(Fintransactie.class)).thenReturn(fintransactieRoot);
        
        final CriteriaQuery<Fintransactie> createSelect = systemUnderTest.where(new Where(""))
                                                                        .orderBy(new OrderBy("date ASC"))
                                                                        .createSelect();
        
        assertNotNull(createSelect);
    }
    @Test
    @SuppressWarnings("unchecked")
    public void testCountBuilder() {
        when(entityManager.getCriteriaBuilder()).thenReturn(cb);
        when(cb.createQuery(Long.class)).thenReturn(cqCount);
        when(cqCount.from(Fintransactie.class)).thenReturn(fintransactieRoot);
        
        when(fintransactieRoot.get(Fintransactie_.mededeling)).thenReturn(expression);
        when(cb.like(any(Expression.class), any(String.class))).thenReturn(predicate);
        
        when(cb.and(any(Predicate.class), any(Predicate.class))).thenReturn(predicate);
        
        final CriteriaQuery<Long> createSelect = systemUnderTest.where(
                new Where("description=*KEM*,contraAccountName=*name*,mutatiesoort=INC,code=AA,debitCreditIndicator=Bij,date=2014-06-02T14:21:44.348"))
                                                                        .createCount();
        
        assertNotNull(createSelect);
    }
    @Test
    @SuppressWarnings("unchecked")
    public void testCountBuilderDateRange() {
        when(entityManager.getCriteriaBuilder()).thenReturn(cb);
        when(cb.createQuery(Long.class)).thenReturn(cqCount);
        when(cqCount.from(Fintransactie.class)).thenReturn(fintransactieRoot);
        
        when(fintransactieRoot.get(Fintransactie_.mededeling)).thenReturn(expression);
        when(cb.between(any(Expression.class), any(String.class), any(String.class))).thenReturn(predicate);
        
        when(cb.and(any(Predicate.class), any(Predicate.class))).thenReturn(predicate);
        
        final CriteriaQuery<Long> createSelect = systemUnderTest.where(
                new Where("date=[2014-06-02T14:21:44.348 2015-06-02T14:21:44.348]"))
                                                                        .createCount();
        
        assertNotNull(createSelect);
    }

    @Test(expected = java.lang.IllegalArgumentException.class)
    public void testCountBuilderUnknownAttribute() {
        when(entityManager.getCriteriaBuilder()).thenReturn(cb);
        when(cb.createQuery(Long.class)).thenReturn(cqCount);
        when(cqCount.from(Fintransactie.class)).thenReturn(fintransactieRoot);
        
        final CriteriaQuery<Long> createSelect = systemUnderTest.where(
                new Where("fiets=*KEM*"))
                .createCount();
        
        assertNotNull(createSelect);
    }
}

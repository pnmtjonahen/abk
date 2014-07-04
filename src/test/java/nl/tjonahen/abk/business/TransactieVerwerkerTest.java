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

package nl.tjonahen.abk.business;

import java.util.HashSet;
import javax.ejb.EJBException;
import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import javax.validation.Path;
import javax.validation.metadata.ConstraintDescriptor;
import nl.tjonahen.abk.domein.TransactieBericht;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

/**
 *
 * @author Philippe Tjon-A-Hen philippe@tjonahen.nl
 */
public class TransactieVerwerkerTest {
    
    @Mock
    private ABKBusiness aBKBusiness;
    
    @InjectMocks
    private TransactieVerwerker systemUnderTest;    
    
    /**
     * Init mocks
     */
    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    /**
     * Test of onMessage method, of class TransactieVerwerker.
     */
    @Test
    public void testOnMessage() {
        TransactieBericht transactie = new TransactieBericht();
        systemUnderTest.onMessage(transactie);
        
        Mockito.verify(aBKBusiness).create(transactie);
    }
    
    /**
     * Test of onMessage method, of class TransactieVerwerker.
     */
    @Test
    public void testOnMessageEJBException() {
        TransactieBericht transactie = new TransactieBericht();
        final NullPointerException nullPointerException = new NullPointerException("Dummy..");
        final EJBException ejbException = new EJBException(nullPointerException);
        
        Mockito.doThrow(ejbException).when(aBKBusiness).create(transactie);
        systemUnderTest.onMessage(transactie);
        
        Mockito.verify(aBKBusiness).create(transactie);
    }
    /**
     * Test of onMessage method, of class TransactieVerwerker.
     */
    @Test
    public void testOnMessageConstraintViolationException() {
        TransactieBericht transactie = new TransactieBericht();
        final HashSet<ConstraintViolation<?>> constraintViolationSet = new HashSet<ConstraintViolation<?>>();
        constraintViolationSet.add(new ConstraintViolation<Object>() {

            @Override
            public String getMessage() {
                return "Dummy...";
            }

            @Override
            public String getMessageTemplate() {
                throw new UnsupportedOperationException("Not supported yet."); 
            }

            @Override
            public Object getRootBean() {
                throw new UnsupportedOperationException("Not supported yet."); 
            }

            @Override
            public Class<Object> getRootBeanClass() {
                throw new UnsupportedOperationException("Not supported yet."); 
            }

            @Override
            public Object getLeafBean() {
                throw new UnsupportedOperationException("Not supported yet."); 
            }

            @Override
            public Object[] getExecutableParameters() {
                throw new UnsupportedOperationException("Not supported yet."); 
            }

            @Override
            public Object getExecutableReturnValue() {
                throw new UnsupportedOperationException("Not supported yet."); 
            }

            @Override
            public Path getPropertyPath() {
                throw new UnsupportedOperationException("Not supported yet."); 
            }

            @Override
            public Object getInvalidValue() {
                throw new UnsupportedOperationException("Not supported yet."); 
            }

            @Override
            public ConstraintDescriptor<?> getConstraintDescriptor() {
                throw new UnsupportedOperationException("Not supported yet."); 
            }

            @Override
            public <U> U unwrap(Class<U> type) {
                throw new UnsupportedOperationException("Not supported yet."); 
            }
        });
        final ConstraintViolationException cve = new ConstraintViolationException(constraintViolationSet);
        final EJBException ejbException = new EJBException(cve);
        
        Mockito.doThrow(ejbException).when(aBKBusiness).create(transactie);
        systemUnderTest.onMessage(transactie);
        
        Mockito.verify(aBKBusiness).create(transactie);
    }
    
    
}

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
package nl.tjonahen.abk.business;

import javax.ejb.EJB;
import javax.ejb.EJBException;
import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.event.Observes;
import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import nl.tjonahen.abk.domein.TransactieBericht;

/**
 *
 * @author Philippe Tjon-A-Hen philippe@tjonahen.nl
 */
@ApplicationScoped
public class TransactieVerwerker {

    @EJB
    private ABKBusiness aBKBusiness;

    /**
     * Observes transactieBericht.
     * @param transactie 
     */
    public void onMessage(@Observes final TransactieBericht transactie) {
        try {
            aBKBusiness.create(transactie);
        } catch (EJBException ex) {
            if (ex.getCausedByException() instanceof ConstraintViolationException) {
                for (ConstraintViolation cv : ((ConstraintViolationException) ex.getCausedByException())
                        .getConstraintViolations()) 
                {
                    System.err.println("=================> " + cv.getMessage());
                }
            }

        }
    }

}

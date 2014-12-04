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
import javax.persistence.criteria.Order;
import javax.persistence.criteria.Root;
import javax.persistence.metamodel.SingularAttribute;
import nl.tjonahen.abk.backend.entity.Fintransactie;
import nl.tjonahen.abk.backend.entity.Fintransactie_;

/**
 *
 * @author Philippe Tjon - A - Hen, philippe@tjonahen.nl
 */
public enum OrderByField {

    ID(Fintransactie_.id),
    AMOUNT(Fintransactie_.bedrag),
    DEBITCREDITINDICATOR(Fintransactie_.bijaf),
    DATE(Fintransactie_.datum),
    DESCRIPTION(Fintransactie_.mededeling),
    ACCOUNTNUMBER(Fintransactie_.rekening),
    CODE(Fintransactie_.code),
    MUTATIESOORT(Fintransactie_.mutatiesoort),
    CONTRAACCOUNTNAME(Fintransactie_.tegenrekeningnaam);


    private final SingularAttribute singularAttribute;

    private OrderByField(final SingularAttribute singularAttribute) {
        this.singularAttribute = singularAttribute;
    }

    private Order desc(final CriteriaBuilder cb,
            final Root<Fintransactie> root) {
        return cb.desc(root.get(singularAttribute));
    }

    private Order asc(final CriteriaBuilder cb,
            final Root<Fintransactie> root) {
        return cb.asc(root.get(singularAttribute));
    }

    public Order order(final boolean isDesc, final CriteriaBuilder cb,
            final Root<Fintransactie> root) {
        if (isDesc) {
            return desc(cb, root);
        }
        return asc(cb, root);
    }
}

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
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import nl.tjonahen.abk.backend.entity.Fintransactie;

/**
 *
 * @author Philippe Tjon - A - Hen, philippe@tjonahen.nl
 */
@FunctionalInterface
public interface PredicateSupplier {
    
    /**
     * Supply a predicate for a query builder with a root element and a value
     * @param cb -
     * @param root -
     * @param value -
     * @return -
     */
    public Predicate supply(CriteriaBuilder cb, Root<Fintransactie> root, String value);
}

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

import nl.tjonahen.abk.backend.model.OrderBy;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import javax.persistence.EntityManager;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Order;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import nl.tjonahen.abk.backend.model.Where;
import nl.tjonahen.abk.backend.entity.Fintransactie;

/**
 *
 * @author Philippe Tjon - A - Hen, philippe@tjonahen.nl
 */
class FinancialTransactionQuery {

    private final EntityManager entityManager;

    private OrderBy orderBy;
    private Where where;

    FinancialTransactionQuery(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    FinancialTransactionQuery orderBy(final OrderBy orderBy) {
        this.orderBy = orderBy;
        return this;
    }

    FinancialTransactionQuery where(final Where where) {
        this.where = where;
        return this;
    }

    CriteriaQuery<Fintransactie> createSelect() {
        final CriteriaBuilder cb = entityManager.getCriteriaBuilder();
        final CriteriaQuery<Fintransactie> cq = cb.createQuery(Fintransactie.class);
        final Root<Fintransactie> fintransactieRoot = cq.from(Fintransactie.class);

        cq.select(fintransactieRoot);

        addWhereClause(cb, fintransactieRoot, cq);

        cq.orderBy(order(orderBy, cb, fintransactieRoot));

        return cq;
    }


    CriteriaQuery<Long> createCount() {
        final CriteriaBuilder cb = entityManager.getCriteriaBuilder();
        final CriteriaQuery<Long> cq = cb.createQuery(Long.class);
        final Root<Fintransactie> fintransactieRoot = cq.from(Fintransactie.class);

        cq.select(cb.count(fintransactieRoot));

        addWhereClause(cb, fintransactieRoot, cq);
        return cq;
    }
    
    private void addWhereClause(final CriteriaBuilder cb, final Root<Fintransactie> fintransactieRoot, 
                        final CriteriaQuery<?> cq) {
        final Optional<Predicate> reduce = where.getWhereClause()
                .stream()
                .map(kv -> PredicateField.valueOf(kv.getKey().toUpperCase())
                                        .predicate(cb, fintransactieRoot, kv.getValue()))
                .reduce((a, b) -> cb.and(a, b));
        if (reduce.isPresent()) {
            cq.where(reduce.get());
        }
    }


    private List<Order> order(final OrderBy orderBy, CriteriaBuilder cb, Root<Fintransactie> fintransactieRoot) {
        return orderBy.getFields()
                .stream()
                .filter(f -> !f.isEmpty())
                .map(f -> OrderByField.valueOf(f.toUpperCase()).order(orderBy.isDesc(), cb, fintransactieRoot))
                .collect(Collectors.toList());
    }

}

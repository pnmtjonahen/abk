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

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import nl.tjonahen.abk.backend.entity.Fintransactie;
import nl.tjonahen.abk.backend.entity.Fintransactie_;

/**
 *
 * @author Philippe Tjon - A - Hen, philippe@tjonahen.nl
 */
public enum PredicateField {

    DESCRIPTION((cb, r, v) -> cb.like(r.get(Fintransactie_.tegenrekeningnaam), replaceWildCards(v))),
    CONTRAACCOUNTNAME((cb, r, v) -> cb.like(r.get(Fintransactie_.tegenrekeningnaam), replaceWildCards(v))),
    MUTATIESOORT((cb, r, v) -> cb.equal(r.get(Fintransactie_.mutatiesoort), v)),
    CODE((cb, r, v) -> cb.equal(r.get(Fintransactie_.code), v)),
    DEBITCREDITINDICATOR((cb, r, v) -> cb.equal(r.get(Fintransactie_.tegenrekeningnaam), v)),
    DATE((cb, r, v) -> {
        if (range(v)) {
            return cb.between(r.get(Fintransactie_.datum), toFirstDate(v), toSecondDate(v));
        }
        return cb.equal(r.get(Fintransactie_.datum), toDate(v));
        
    });

    private final PredicateSupplier ps;

    private PredicateField(PredicateSupplier ps) {
        this.ps = ps;
    }

    public Predicate predicate(CriteriaBuilder cb, Root<Fintransactie> root, String value) {
        return ps.supply(cb, root, value);
    }

    private static String replaceWildCards(String value) {
        return value.replace("*", "%");
    }

    private static boolean range(String value) {
        return value.startsWith("[") && value.endsWith("]");
    }

    private static Date toDate(String value) {
        return Date.from(LocalDateTime.parse(value, DateTimeFormatter.ISO_DATE_TIME)
                .atZone(ZoneId.systemDefault()).toInstant());
    }

    private static Date toFirstDate(String value) {
        String[] sub = value.substring(1, value.length() - 1).split(" ");

        return Date.from(LocalDateTime.parse(sub[0], DateTimeFormatter.ISO_DATE_TIME)
                .atZone(ZoneId.systemDefault()).toInstant());
    }

    private static Date toSecondDate(String value) {
        String[] sub = value.substring(1, value.length() - 1).split(" ");
        return Date.from(LocalDateTime.parse(sub[1], DateTimeFormatter.ISO_DATE_TIME)
                .atZone(ZoneId.systemDefault()).toInstant());
    }
}

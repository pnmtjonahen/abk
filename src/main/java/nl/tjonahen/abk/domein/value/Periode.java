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
package nl.tjonahen.abk.domein.value;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

/**
 *
 * @author Philippe Tjon-A-Hen philippe@tjonahen.nl
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "period", propOrder = {
    "start", "end"
})
@XmlRootElement(name = "period")
public class Periode {

    private Maand start;
    private Maand end;

    /**
     * Default constructor for Hibernate. Should not be used.
     */
    public Periode() {
    }

    /**
     * 
     * @param startMonth -
     * @param endMonth -
     */
    public Periode(final Maand startMonth, final Maand endMonth) {
        this.start = startMonth;
        this.end = endMonth;
    }

    public Maand getEnd() {
        return end;
    }

    public void setEnd(Maand end) {
        this.end = end;
    }

    public Maand getStart() {
        return start;
    }

    public void setStart(Maand start) {
        this.start = start;
    }

    /**
     * Test if a month fals within this period.
     *
     * @param test month to test against
     * @return true if test month falls within the period.
     */
    public boolean isInPeriod(Maand test) {
        return start.beforeOrEquals(test) && end.isAfther(test);
    }

    /**
     * Test if a quarter false within this period.
     *
     * @param test quarter to test
     * @return true if test quarter false within this period.
     */
    public boolean isInPeriod(Kwartaal test) {
        final Maand firstMonth = new Maand(test.getQuarter() * MONTH_PER_QUEARTER - 2, test.getYear());
        final Maand lastMonth = new Maand(test.getQuarter() * MONTH_PER_QUEARTER, test.getYear());

        if (isInPeriod(firstMonth)) {
            return true;
        }
        return isInPeriod(lastMonth);
    }
    private static final int MONTH_PER_QUEARTER = 3;

}

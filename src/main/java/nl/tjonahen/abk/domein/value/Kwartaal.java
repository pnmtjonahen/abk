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

import java.util.Calendar;
import java.util.Date;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

/**
 * 
 * @author Philippe Tjon-A-Hen philippe@tjonahen.nl
 */

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "quarter", propOrder = {
    "quarter", "year"
})
@XmlRootElement(name = "quarter")
@SuppressWarnings("unchecked")
public class Kwartaal implements Comparable<Kwartaal> {

    /**
     * Constant for first quarter
     */
    public static final int FIRST_QUARTER = 1;

    /**
     * Constant for second quarter
     */
    public static final int SECOND_QUARTER = 2;

    /**
     * Constant for third quarter
     */
    public static final int THIRD_QUARTER = 3;

    /**
     * Constant for fourth quarter
     */
    public static final int FOURTH_QUARTER = 4;

    private static final int ZEROBASED_QUARTERNUMBER = 3;
    private int quarter;

    private int year;

    /**
     * Default constructor
     */
    public Kwartaal() {
    }

    /**
     * Constructor with quarter and year
     * 
     * @param quarter
     *            quarter number or one of the quarter constants
     * @param year
     *            year
     */
    public Kwartaal(int quarter, int year) {
        this.quarter = quarter;
        this.year = year;
    }

    /**
     * 
     * @param date - 
     */
    public Kwartaal(Date date) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        int month = cal.get(Calendar.MONTH) + 1;
        this.quarter = month / ZEROBASED_QUARTERNUMBER + month % ZEROBASED_QUARTERNUMBER > 0 ? 1 : 0;
        this.year = cal.get(Calendar.YEAR);
    }

    /**
     * {@inheritDoc} 
     */
    @Override
    public String toString() {
        return "Q" + quarter + "-" + year;
    }
    /**
     * @return Returns the quarter.
     */
    public int getQuarter() {
        return quarter;
    }

    /**
     * @param quarter
     *            The quarter to set.
     */
    public void setQuarter(int quarter) {
        this.quarter = quarter;
    }

    /**
     * @return Returns the year.
     */
    public int getYear() {
        return year;
    }

    /**
     * @param year
     *            The year to set.
     */
    public void setYear(int year) {
        this.year = year;
    }

    /**
     * 
     * @param l -
     * @return -
     */
    public boolean isPreviousQuarter(Kwartaal l) {
        if (year == l.year) {
            if (this.quarter == l.quarter - 1) {
                return true;
            }
        } else if (year == l.year - 1 && this.quarter == FOURTH_QUARTER && l.quarter == FIRST_QUARTER) {
           return true;
        }

        return false;
    }

    /**
     * @param l quarter t test against.
     * @return true if this quarter is more resent than l.
     */
    public boolean isMoreRecentThan(Kwartaal l) {
        if (this.year > l.year) {
            return true;
        }
        return this.year == l.year && this.quarter > l.quarter;
    }

    /**
     * Get previous quarter from current quarter
     * 
     * @return previous Quarter relative to this.
     */
    public Kwartaal previous() {
        Kwartaal prev = new Kwartaal();
        if (this.quarter == FIRST_QUARTER) {
            prev.quarter = FOURTH_QUARTER;
            prev.year = this.year - 1;
        } else {
            prev.quarter = this.quarter - 1;
            prev.year = this.year;
        }
        return prev;
    }
    
    /**
     *  {@inheritDoc}
     */
    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (obj instanceof Kwartaal) {
            Kwartaal r = (Kwartaal) obj;
            return this.year == r.year && this.quarter == r.quarter;
        }
        return false;
    }
    
    /**
     *  {@inheritDoc}
     */
    @Override
    public int hashCode() {
        return year+quarter;
    }

    /**
     * @param endQuarter quarter to test.
     * @return true if this quarter is after the test quarter
     */
    public boolean isAfther(Kwartaal endQuarter) {
        if (year > endQuarter.year) {
            return true;
        }
        if (year == endQuarter.year && quarter > endQuarter.quarter) {
            return true;
        }
        return false;
    }

    /**
     * @return next quarter relative to this.
     */
    public Kwartaal next() {
        Kwartaal next = new Kwartaal();
        if (this.quarter == FOURTH_QUARTER) {
            next.quarter = FIRST_QUARTER;
            next.year = this.year + 1;
        } else {
            next.quarter = this.quarter + 1;
            next.year = this.year;
        }
        return next;
    }

    /**
     * {@inheritDoc}
     *
     * @see java.lang.Comparable#compareTo(java.lang.Object)
     */
    @Override
    public int compareTo(final Kwartaal o) {
        
        if (year == o.year) {
            return quarter - o.quarter;
        }
        return year - o.year;
    }

    /**
     * @return period of this quarter.
     */
    public Periode getPeriod() {
        final Periode p = new Periode();

        
        final Maand start = new Maand(quarter * ZEROBASED_QUARTERNUMBER - 2 , year);
        final Maand end = new Maand(quarter * ZEROBASED_QUARTERNUMBER, year);
        
        p.setStart(start);
        p.setEnd(end);
        return p;
    }
}

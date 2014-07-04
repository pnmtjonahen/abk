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
@XmlType(name = "month", propOrder = {
    "month", "year"
})
@XmlRootElement(name = "month")
public class Maand {

    /**
     * Constant for January
     */
    public static final int JANUARY = 1;

    /**
     * Constant for February
     */
    public static final int FEBRUARY = 2;

    /**
     * Constant for March
     */
    public static final int MARCH = 3;

    /**
     * Constant for April
     */
    public static final int APRIL = 4;

    /**
     * Constant for May
     */
    public static final int MAY = 5;

    /**
     * Constant for June
     */
    public static final int JUNE = 6;

    /**
     * Constant for July
     */
    public static final int JULY = 7;

    /**
     * Constant for August
     */
    public static final int AUGUST = 8;

    /**
     * Constant for September
     */
    public static final int SEPTEMBER = 9;

    /**
     * Constant for October
     */
    public static final int OCTOBER = 10;

    /**
     * Constant for November
     */
    public static final int NOVEMBER = 11;

    /**
     * Constant for December
     */
    public static final int DECEMBER = 12;

    private static final int LAST_MONTH = 12;
    private static final int LAST_MONTHQ1 = 3;
    private static final int LAST_MONTHQ2 = 6;
    private static final int LAST_MONTHQ3 = 9;

    private int month;

    private int year;

    /**
     * {@inheritDoc} 
     */
    @Override
    public String toString() {
        return month + "-" + year;
    }

    /**
     * Default constructor
     */
    public Maand() {
    }

    /**
     * Constructor with month and year
     * 
     * @param month
     *            month-number or one of the month constants
     * @param year
     *            year
     */
    public Maand(final int month, final int year) {
        this.month = month;
        this.year = year;
    }

    /**
     * 
     * @param date - 
     */
    public Maand(final Date date) {
        final Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        this.month = cal.get(Calendar.MONTH) + 1;
        this.year = cal.get(Calendar.YEAR);
    }

    /**
     * Return value as a java util date.
     * 
     * @return java Date of current month.
     */

    public Date getDate() {
        final Calendar cal = Calendar.getInstance();

        cal.set(Calendar.HOUR, 0);
        cal.set(Calendar.MINUTE, 0);
        cal.set(Calendar.SECOND, 0);
        cal.set(Calendar.MILLISECOND, 0);

// first day of month
        cal.set(Calendar.DATE, 1); 
        cal.set(Calendar.MONTH, this.month - 1);
        cal.set(Calendar.YEAR, this.year);
        return cal.getTime();
    }

    /**
     * @return Returns the month.
     */
    public int getMonth() {
        return month;
    }

    /**
     * @param monthNr
     *            The month to set.
     */
    public void setMonth(int monthNr) {
        this.month = monthNr;
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
     * checks this > r.
     * 
     * @param r
     *            right parameter
     * @return true if this is greater than
     */
    public boolean greaterThen(Maand r) {
        if (year > r.year) {
            return true;
        } else if (year == r.year && month > r.month) {
            return true;
        }
        return false;
    }

    /**
     * check is this is previous.
     * 
     * @param r
     *            righ parameter
     * @return true is this is previous to r
     */
    public boolean isPreviousMonth(Maand r) {
        if (year == r.year) {
            if (month == r.month - 1) { 
                // previous month
                return true;
            }
        } else if (year == r.year - 1 && month == LAST_MONTH && r.month == 1) { 
                // december previous year is
                // previous month to januar
                return true;
        }
        return false;
    }

    /**
     * 
     * @return - 
     */
    public Maand previous() {
        int m = month;
        int y = year;

        m--;
        if (m == 0) { 
            // month was januar
            m = LAST_MONTH; 
            // set to december
            y--;
        }
        return new Maand(m, y);
    }

    /*
     * 
     * {@inheritDoc}
     */
    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (obj instanceof Maand) {
            Maand r = (Maand) obj;
            return this.year == r.year && this.month == r.month;
        }
        return false;
    }

    /**
     * 
     * {@inheritDoc} 
     */
    @Override
    public int hashCode() {
        return year + month;
    }

    /**
     * checks for this <= r
     * 
     * @param r
     *            right parameter
     * @return true is this <= r
     */
    public boolean beforeOrEquals(Maand r) {
        if (this.year < r.year) {
            return true;
        }
        return this.year == r.year && this.month <= r.month;
    }


    /**
     * Check if this month is within the quarter.
     * 
     * @param quarter
     *            quarter to check against
     * @return true if month falls within quarter
     */
    public boolean isInQuarter(Kwartaal quarter) {
        if (this.year == quarter.getYear()) {
            switch (quarter.getQuarter()) {
                case Kwartaal.FIRST_QUARTER:
                    return this.month > 0 && this.month <= LAST_MONTHQ1;
                case Kwartaal.SECOND_QUARTER:
                    return this.month > LAST_MONTHQ1 && this.month <= LAST_MONTHQ2;
                case Kwartaal.THIRD_QUARTER:
                    return this.month > LAST_MONTHQ2 && this.month <= LAST_MONTHQ3;
                case Kwartaal.FOURTH_QUARTER:
                    return this.month > LAST_MONTHQ3 && this.month <= LAST_MONTH;
                default:
                    return false;
            }
        }
        return false;
    }

    /**
     * Gets the quarter where this month is in.
     * 
     * @return quarter corresponding to month.
     */
    public Kwartaal getQuarter() {
        int y = this.year;
        if (this.month > 0 && this.month <= LAST_MONTHQ1) {
            return new Kwartaal(Kwartaal.FIRST_QUARTER, y);
        }
        if (this.month > LAST_MONTHQ1 && this.month <= LAST_MONTHQ2) {
            return new Kwartaal(Kwartaal.SECOND_QUARTER, y);
        }
        if (this.month > LAST_MONTHQ2 && this.month <= LAST_MONTHQ3) {
            return new Kwartaal(Kwartaal.THIRD_QUARTER, y);
        }
        return new Kwartaal(Kwartaal.FOURTH_QUARTER, y);
    }

    /**
     * @param endMonth
     *            month to test against
     * @return true if this month is after testmonth
     */
    public boolean isAfther(Maand endMonth) {
        if (year > endMonth.year) {
            return true;
        }
        return year == endMonth.year && month > endMonth.month;
    }

    /**
     * @return next month relative to this
     */
    public Maand next() {
        int m = month;
        int y = year;

        m++;
        if (m == LAST_MONTH + 1) { 
            // month was december
            m = 1; 
            // set to jan
            y++;
        }
        return new Maand(m, y);
    }

    /**
     * Calculates the difference in months between this month and the other.
     * @param other the other month
     * @return absolute difference in month 
     */
    public int diffInMonth(Maand other) {
        return Math.abs(this.year * LAST_MONTH + this.month - other.year * LAST_MONTH + other.month);
    }
    
    /**
     * Rolls the month backwards (previous month) n times.
     * @param n number of rolls to perform
     * @return the new month
     */
    public Maand rollBack(int n) {
        Maand newMonth = this;
        for (int i = 0; i < n; i++) {
            newMonth = newMonth.previous();
        }
        return newMonth;
    }

    /**
     * @param m2 compares month2 against this month
     * @return 0 if equal 1 if more and -1 if less
     */
    public int compareTo(Maand m2) {
        int y = year - m2.year;
        if (y == 0) {
            return month - m2.month;
        }
        return y;
    }
    
}

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

package nl.tjonahen.abk.domein;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

/**
 *
 * @author Philippe Tjon-A-Hen philippe@tjonahen.nl
 */
@XmlAccessorType(XmlAccessType.PROPERTY)
@XmlType(name = "yearreport", propOrder = {
    "name", "children", "currentMin11", "currentMin10", "currentMin9", "currentMin8", "currentMin7", "currentMin6", "currentMin5", "currentMin4", 
        "currentMin3", "currentMin2", "currentMin1", "current"
})
@XmlRootElement(name = "financieelrapportpermaand")
public class YearReport {
    private String name;
    private final double[] kosten;
    private List<YearReport> children;

    /**
     * Constructor
     */
    public YearReport() {
        this.kosten = new double[NR_MONTHS];
        this.children = new ArrayList<>();
    }
    private static final int NR_MONTHS = 13;
    private static final int CURRENT_MONTH = 0;
    private static final int CURRENT_MONTH_MIN01 = 1;
    private static final int CURRENT_MONTH_MIN02 = 2;
    private static final int CURRENT_MONTH_MIN03 = 3;
    private static final int CURRENT_MONTH_MIN04 = 4;
    private static final int CURRENT_MONTH_MIN05 = 5;
    private static final int CURRENT_MONTH_MIN06 = 6;
    private static final int CURRENT_MONTH_MIN07 = 7;
    private static final int CURRENT_MONTH_MIN08 = 8;
    private static final int CURRENT_MONTH_MIN09 = 9;
    private static final int CURRENT_MONTH_MIN10 = 10;
    private static final int CURRENT_MONTH_MIN11 = 11;

    /**
     * 
     * @param index -
     * @param kost -
     */
    public void addKosten(final int index, final double kost) {
        kosten[index] += kost;
    }
    
    public String getName() {
        return name;
    }

    public void setName(final String name) {
        this.name = name;
    }

    public List<YearReport> getChildren() {
        return children;
    }

    public void setChildren(List<YearReport> children) {
        this.children = children;
    }

    public String getCurrent() {
        return convertKosten(CURRENT_MONTH);
    }

    public String getCurrentMin1() {
        return convertKosten(CURRENT_MONTH_MIN01);
    }
    
    public String getCurrentMin2() {
        return convertKosten(CURRENT_MONTH_MIN02);
    }
    
    public String getCurrentMin3() {
        return convertKosten(CURRENT_MONTH_MIN03);
    }
    
    public String getCurrentMin4() {
        return convertKosten(CURRENT_MONTH_MIN04);
    }
    
    public String getCurrentMin5() {
        return convertKosten(CURRENT_MONTH_MIN05);
    }
    
    public String getCurrentMin6() {
        return convertKosten(CURRENT_MONTH_MIN06);
    }
    
    public String getCurrentMin7() {
        return convertKosten(CURRENT_MONTH_MIN07);
    }
    
    public String getCurrentMin8() {
        return convertKosten(CURRENT_MONTH_MIN08);
    }
    
    public String getCurrentMin9() {
        return convertKosten(CURRENT_MONTH_MIN09);
    }
    
    public String getCurrentMin10() {
        return convertKosten(CURRENT_MONTH_MIN10);
    }
    
    public String getCurrentMin11() {
        return convertKosten(CURRENT_MONTH_MIN11);
    }
    
    private String convertKosten(int index) {
        return String.format("%.2f", kosten[index]);
    }

    /**
     * Pull up the sum of all children
     */
    public void sum() {
        for (YearReport child : children) {
            for (int i = 0; i < kosten.length; i++) {
                kosten[i] += child.kosten[i];
            }
        }
    }
    
}

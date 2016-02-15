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
package nl.tjonahen.abk.backend.model;

import java.util.List;

/**
 * Order By clause
 * 
 * @author Philippe Tjon - A - Hen, philippe@tjonahen.nl
 */
public class OrderBy {

    private final Fields fields;
    private final boolean desc;

    /**
     * 
     * @param fields  -
     */
    public OrderBy(String fields) {

        String[] sub = fields.split(" ");
        
        this.fields = new Fields(sub[0]);
        if (sub.length > 1) {
            this.desc = !"ASC".equals(sub[1].trim());
        } else {
            this.desc = true;
        }
    }

    public List<String> getFields() {
        return fields.getList();
    }

    public boolean isDesc() {
        return desc;
    }

}

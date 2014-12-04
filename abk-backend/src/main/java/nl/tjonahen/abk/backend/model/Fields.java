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

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

/**
 * The fields for output
 *
 * @author Philippe Tjon - A - Hen, philippe@tjonahen.nl
 */
public class Fields {
    private static final String ID = "id";

    private final List<String> list;
    /**
     * Constructor
     *
     * @param fields -
     */
    public Fields(String fields) {
        this.list = Arrays.asList(fields.split(",")).stream().filter(f -> !f.isEmpty()).collect(Collectors.toList());
    }

    public List<String> getList() {
        return list;
    }


    public boolean containsId() {
        return list.contains("id");
    }

    public void addId() {
        list.add(ID);
    }
}
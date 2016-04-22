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
import java.util.Optional;
import java.util.stream.Collectors;

/**
 *
 * @author Philippe Tjon - A - Hen, philippe@tjonahen.nl
 */
public class KeyValue {
    private static final int PAIR = 2;

    private final String key;
    private final String value;

    /**
     * 
     * @param key -
     * @param value - 
     */
    public KeyValue(final String key, final String value) {
        this.key = key;
        this.value = value;
    }

    public String getKey() {
        return key;
    }

    public String getValue() {
        return value;
    }
    /**
     * 
     * @param data -
     * @return new KeyValue
     */
    public static Optional<KeyValue> convert(final String data) {
        final List<String> sub = Arrays.asList(data.split("="))
                                    .stream()
                                    .filter(f -> !f.isEmpty())
                                    .collect(Collectors.toList());
        if (sub.size() == PAIR) {
            return Optional.of(new KeyValue(sub.get(0), sub.get(1)));
        }
        return Optional.empty();
    }

}

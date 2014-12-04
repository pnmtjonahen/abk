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
package nl.tjonahen.abk.backend;

/**
 *
 * @author Philippe Tjon - A - Hen, philippe@tjonahen.nl
 */
public final class ResourcePaths {

    /**
     * Root rest resource path of the application
     */
    public static final String APPLICATION_PATH = "rest";

    /**
     * transactions path
     */
    public static final String TRANSACTIONS_PATH = "/transactions";

    /**
     * accounts path
     */
    public static final String ACCOUNTS_PATH = "/accounts";

    private ResourcePaths() {
    }
}

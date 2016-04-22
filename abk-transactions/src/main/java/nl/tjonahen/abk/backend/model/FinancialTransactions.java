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
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

/**
 *
 * @author Philippe Tjon - A - Hen, philippe@tjonahen.nl
 */
@XmlRootElement
@XmlType(name = "", propOrder = {
    "offset", "limit", "size", "first", "next", "previous", "last", "list"
})
@XmlAccessorType(XmlAccessType.FIELD)
public class FinancialTransactions {
    private static final String FORMAT_OFFSET = "%s?offset=%d%s";

    @XmlElement
    private int offset;
    @XmlElement
    private int limit;
    @XmlElement
    private int size;
    @XmlElement
    private PageReference first;
    @XmlElement
    private PageReference next;
    @XmlElement
    private PageReference previous;
    @XmlElement
    private PageReference last;
    @XmlElement
    private List<FinancialTransaction> list;

    /**
     * Default constructor for unmarschalling.
     */
    public FinancialTransactions() {
        // for unmarshalling puposes
    }

    
    /**
     * @param offset the offset in the result list
     * @param limit the limit
     * @param size maximum number of transaction available
     * @param list -
     */
    public FinancialTransactions(List<FinancialTransaction> list, int offset, int limit, int size) {
        this.list = list;
        this.offset = offset;
        this.limit = limit;
        this.size = size;
    }

    public List<FinancialTransaction> getList() {
        return list;
    }

    public FinancialTransactions updateHref(String baseUrl, String fields) {
        String fieldsParam = "";
        if (!fields.isEmpty()) {
            fieldsParam = "&fields=" + fields;
        }
        list.forEach(f -> f.updateHref(baseUrl));

        this.first = new PageReference(new Meta(
                String.format("%s?offset=0%s", baseUrl, fieldsParam)));
        if (offset > 0) {
            this.previous = new PageReference(new Meta(
                    String.format(FORMAT_OFFSET, baseUrl, (offset - limit) < 0 ? 0 : offset - limit, fieldsParam)));
        }
        if ((offset + limit) < size) {
            this.next = new PageReference(new Meta(
                    String.format(FORMAT_OFFSET, baseUrl, offset + limit, fieldsParam)));
        }
        this.last = new PageReference(new Meta(
                String.format(FORMAT_OFFSET, baseUrl, (size - limit) < 0 ? size : size - limit, fieldsParam)));
        return this;
    }

    public int getOffset() {
        return offset;
    }

    public int getLimit() {
        return limit;
    }

    public int getSize() {
        return size;
    }

    public PageReference getFirst() {
        return first;
    }

    public PageReference getNext() {
        return next;
    }

    public PageReference getPrevious() {
        return previous;
    }

    public PageReference getLast() {
        return last;
    }

}

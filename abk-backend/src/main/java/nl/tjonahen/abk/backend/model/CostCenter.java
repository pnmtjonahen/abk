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

/**
 *
 * @author Philippe Tjon - A - Hen, philippe@tjonahen.nl
 */
@XmlRootElement
@XmlAccessorType(XmlAccessType.FIELD)
public class CostCenter {
    @XmlElement(name = "id", required = true)
    private Long id;
    @XmlElement(name = "meta", required = true)
    private Meta meta;
    @XmlElement(name = "filter", required = false)
    private String filter;
    @XmlElement(name = "name", required = false)
    private String name;
 
    @XmlElement(name = "parent", required = false)
    private CostCenter parent;

    @XmlElement(name = "list", required = false)
    private List<CostCenter> list;
    
    public CostCenter() {
    }
    
    public List<CostCenter> getList() {
        return list;
    }


    public void setList(List<CostCenter> list) {
        this.list = list;
    }
    
    public CostCenter updateHref(String baseUrl) {
        this.setMeta(new Meta(baseUrl + "/" + id));
        
        if (this.parent != null) {
            this.parent.updateHref(baseUrl);
        }
        
        if (this.list != null) {
            this.list.forEach(c -> c.updateHref(baseUrl));
        }
        return this;
    }
    public Meta getMeta() {
        return meta;
    }

    public void setMeta(Meta meta) {
        this.meta = meta;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getFilter() {
        return filter;
    }

    public void setFilter(String filter) {
        this.filter = filter;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public CostCenter getParent() {
        return parent;
    }

    public void setParent(CostCenter parent) {
        this.parent = parent;
    }

    
}

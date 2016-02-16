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
package nl.tjonahen.abk.backend.boundry.costcenter;

import java.util.stream.Collectors;
import nl.tjonahen.abk.backend.model.CostCenter;
import nl.tjonahen.abk.backend.entity.Kostenplaats;

/**
 *
 * @author Philippe Tjon - A - Hen, philippe@tjonahen.nl
 */
public class ConvertCostCenter {

    private final int expand;
    private int currentExpand;

    public ConvertCostCenter(int expand) {
        this.expand = expand;
        this.currentExpand = 0;
    }

    public CostCenter convert(Kostenplaats kostenplaats) {
        currentExpand++;

        final CostCenter costCenter = new CostCenter();
        costCenter.setId(kostenplaats.getId());
        costCenter.setFilter(kostenplaats.getFilter());
        costCenter.setName(kostenplaats.getNaam());

        if (kostenplaats.getParent() != null) {
            costCenter.setParent(convertReferenceOnly(kostenplaats.getParent()));
        }
        
        if (currentExpand <= expand) {
            costCenter.setList(kostenplaats.getKostenplaatsCollection()
                    .stream()
                    .map(this::convert)
                    .collect(Collectors.toList()));
        } else {
            costCenter.setList(kostenplaats.getKostenplaatsCollection()
                    .stream()
                    .map(ConvertCostCenter::convertReferenceOnly)
                    .collect(Collectors.toList()));
        }
        currentExpand--;
        return costCenter;
    }
    
    private static CostCenter convertReferenceOnly(Kostenplaats kostenplaats) {
        final CostCenter costCenter = new CostCenter();
        costCenter.setId(kostenplaats.getId());
        return costCenter;
    }
}

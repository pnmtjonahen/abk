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

import java.io.IOException;
import java.util.ArrayList;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;
import org.junit.Test;

/**
 *
 * @author Philippe Tjon - A - Hen, philippe@tjonahen.nl
 */
public class CostCentersTest {

    @Test
    public void testWriteXML() throws JAXBException, IOException {
        JAXBContext jaxbContext = JAXBContext.newInstance(new Class<?>[] {CostCenters.class, CostCenter.class});
        Marshaller m = jaxbContext.createMarshaller();

        
        CostCenter cc = new CostCenter();
        cc.setName("name");
        cc.setFilter("filter");
        final ArrayList<CostCenter> arrayList = new ArrayList<>();
        arrayList.add(cc);
        arrayList.add(cc);
        arrayList.add(cc);
        
        CostCenters centers = new CostCenters(arrayList);
        
        m.marshal(centers, System.out);
    }
    @Test
    public void testReadXML() throws JAXBException, IOException {
        JAXBContext jaxbContext = JAXBContext.newInstance(new Class<?>[] {CostCenters.class, CostCenter.class});
        Unmarshaller m = jaxbContext.createUnmarshaller();

        m.unmarshal(this.getClass().getResourceAsStream("/costcenters.xml"));
    }
    @Test
    public void testReadJSon() throws JAXBException, IOException {
        
    }
}

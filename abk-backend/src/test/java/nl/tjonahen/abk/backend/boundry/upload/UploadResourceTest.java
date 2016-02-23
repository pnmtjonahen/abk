/*
 * Copyright (C) 2016 Philippe Tjon - A - Hen, philippe@tjonahen.nl
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
package nl.tjonahen.abk.backend.boundry.upload;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.util.ArrayList;
import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;
import nl.tjonahen.abk.backend.entity.CsvReader;
import org.junit.Test;
import static org.junit.Assert.*;
import org.junit.Before;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import static org.mockito.Mockito.when;
import org.mockito.MockitoAnnotations;

/**
 *
 * @author Philippe Tjon - A - Hen, philippe@tjonahen.nl
 */
public class UploadResourceTest {

    @Mock
    private TransactionProcessor transactionProcessor;

    @Mock
    private EntityManager entityManager;
    @Mock
    TypedQuery<CsvReader> csvReaderQuery;

    @Mock
    private HttpServletRequest request;
    @Mock
    private Part part;

    @Mock
    private HttpServletResponse response;

    @InjectMocks
    private UploadResource SystemUnderTest;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
    }

    /**
     * Test of getServletInfo method, of class UploadResource.
     */
    @Test
    public void testGetServletInfo() {
        UploadResource instance = new UploadResource();
        assertEquals("Processes uploaded stransaction files", instance.getServletInfo());
    }

    @Test
    public void testDoPost() throws ServletException, IOException {
        when(entityManager.createNamedQuery("CsvReader.findAll", CsvReader.class)).thenReturn(csvReaderQuery);
        final ArrayList<CsvReader> csvReaderList = new ArrayList<CsvReader>();
        final CsvReader csvReader = new CsvReader();
        csvReader.setScript(loadScript());
        csvReaderList.add(csvReader);
        when(csvReaderQuery.getResultList()).thenReturn(csvReaderList);
        when(response.getWriter()).thenReturn(new PrintWriter(System.out));
        final ArrayList<Part> partsList = new ArrayList<Part>();
        partsList.add(part);
        when(part.getSubmittedFileName()).thenReturn("dummy.csv");
        when(part.getInputStream()).thenReturn(new ByteArrayInputStream("\"20111222\",\"1350002 NS-TIEL 201>\\TIEL> \\N\",\"5521208\",\"425008215\",\"BA\",\"Af\",\"13,00\",\"Betaalautomaat\",\"PASVOLGNR 017     22-12-2011 06:03TRANSACTIENR 1100332       \"".getBytes()));
        when(request.getParts()).thenReturn(partsList);

        SystemUnderTest.doPost(request, response);
    }

    private String loadScript() {
        return getStringFromInputStream(this.getClass().getResourceAsStream("/uploadresource.js"));

    }

    // convert InputStream to String

    private static String getStringFromInputStream(InputStream is) {

        BufferedReader br = null;
        StringBuilder sb = new StringBuilder();

        String line;
        try {

            br = new BufferedReader(new InputStreamReader(is));
            while ((line = br.readLine()) != null) {
                sb.append(line);
            }

        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (br != null) {
                try {
                    br.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }

        return sb.toString();

    }
}

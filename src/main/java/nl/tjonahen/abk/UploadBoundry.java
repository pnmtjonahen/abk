/*
 * Copyright (C) 2013 Philippe Tjon-A-Hen philippe@tjonahen.nl
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
package nl.tjonahen.abk;

import nl.tjonahen.csvreader.CsvReader;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.event.Event;
import javax.inject.Inject;
import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import nl.tjonahen.abk.domein.TransactieBericht;
import org.glassfish.jersey.media.multipart.FormDataContentDisposition;
import org.glassfish.jersey.media.multipart.FormDataParam;

/**
 *
 * @author Philippe Tjon-A-Hen philippe@tjonahen.nl
 */
@Path("/upload")
@ApplicationScoped
public class UploadBoundry {
    private static final Logger LOGGER = Logger.getLogger(UploadBoundry.class.getName());
    
    @Inject
    private Event<TransactieBericht> transactieBerichtEvent; 


    /**
     *
     * @param uploadedInputStream -
     * @param fileDetail -
     */
    @POST
    @Consumes(MediaType.MULTIPART_FORM_DATA)
    @Produces(MediaType.TEXT_PLAIN)
    public void recieveFile(
            @FormDataParam("file") InputStream uploadedInputStream,
            @FormDataParam("file") FormDataContentDisposition fileDetail) 
    {

        final CsvReader reader;
        try {
            reader = new CsvReader(new BufferedReader(new InputStreamReader(uploadedInputStream, "UTF-8")));
        } catch (UnsupportedEncodingException ex) {
            LOGGER.log(Level.SEVERE, null, ex);
            return;
        }
        try {
            reader.readHeaders();
            while (reader.readRecord()) {
                final TransactieBericht tr = new TransactieBericht();

                //                "Datum",
                final GregorianCalendar c = new GregorianCalendar();
                int count = 0;
                c.setTime(makeDate(reader.get(count++)));
                tr.setDatum(c.getTime());
                //                "Naam / Omschrijving",
                tr.setTegenrekeningNaam(reader.get(count++));
                //                "Rekening",
                tr.setRekening(reader.get(count++));
                //                "Tegenrekening",
                tr.setTegenrekeningRekening(reader.get(count++));
                //                "Code",
                tr.setCode(reader.get(count++));
                //                "Af Bij",
                tr.setBijAf(reader.get(count++));
                //                "Bedrag (EUR)",
                tr.setBedrag(Double.valueOf(reader.get(count++).replace(',', '.')));
                //                "MutatieSoort",
                tr.setMutatiesoort(reader.get(count++));
                //                "Mededelingen"
                tr.setMededeling(reader.get(count++));

                transactieBerichtEvent.fire(tr);
            }
        } catch (IOException | NumberFormatException ex) {
            LOGGER.severe(reader.getRawRecord());

        }
        reader.close();
    }

    private Date makeDate(String incDate) {
        // 20070720 yyyymmdd
        Calendar cal = new GregorianCalendar();
//CHECKSTYLE:OFF
        String strYear = incDate.substring(0, 4);
        int year = Integer.parseInt(strYear);

        String strMonth = incDate.substring(4, 6);
        int month = Integer.parseInt(strMonth) - 1;

        String strDay = incDate.substring(6, 8);
        int day = Integer.parseInt(strDay);
//CHECKSTYLE:ON
        cal.set(Calendar.YEAR, year);
        cal.set(Calendar.MONTH, month);
        cal.set(Calendar.DATE, day);

        return cal.getTime();
    }

}

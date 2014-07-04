/*
 * Copyright (C) 2014 Philippe Tjon-A-Hen philippe@tjonahen.nl
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

import java.io.InputStream;
import java.util.Calendar;
import java.util.Date;
import javax.enterprise.event.Event;
import nl.tjonahen.abk.domein.TransactieBericht;
import org.glassfish.jersey.media.multipart.FormDataContentDisposition;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

/**
 *
 * @author Philippe Tjon-A-Hen philippe@tjonahen.nl
 */
public class UploadBoundryTest {
    
    @Mock
    private Event<TransactieBericht> transactieBerichtEvent; 
    
    @InjectMocks
    private UploadBoundry systemUnderTest;    
    
    /**
     * Init mocks
     */
    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
    }
    
    /**
     * Test of recieveFile method, of class UploadBoundry.
     */
    @Test
    public void testRecieveFile() {
        final InputStream uploadedInputStream = this.getClass().getResourceAsStream("/test.csv");
        final FormDataContentDisposition fileDetail = null;
        final ArgumentCaptor<TransactieBericht> captor = ArgumentCaptor.forClass(TransactieBericht.class);
        
        systemUnderTest.recieveFile(uploadedInputStream, fileDetail);
        
        Mockito.verify(transactieBerichtEvent).fire(captor.capture());
        final TransactieBericht value = captor.getValue();
        
        Assert.assertEquals(new Double("20.00"), value.getBedrag());
        Assert.assertEquals("Af", value.getBijAf());
        Assert.assertEquals("GM", value.getCode());
        final Date datum = value.getDatum();
        final Calendar cal = Calendar.getInstance();
        cal.setTime(datum);
        
        Assert.assertEquals(2012, cal.get(Calendar.YEAR));
        
        Assert.assertEquals("21-12-2012 11:35 017     180923492340        ", value.getMededeling());
        Assert.assertEquals("Geldautomaat", value.getMutatiesoort());
        Assert.assertEquals("1234567", value.getRekening());
        Assert.assertEquals("Dummy omschrijving", value.getTegenrekeningNaam());
        Assert.assertEquals("", value.getTegenrekeningRekening());
        
    }
    
}

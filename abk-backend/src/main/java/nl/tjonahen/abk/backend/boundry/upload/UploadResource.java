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
package nl.tjonahen.abk.backend.boundry.upload;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.EJB;
import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;
import nl.tjonahen.abk.backend.entity.Fintransactie;
import nl.tjonahen.csvreader.CsvReader;

/**
 *
 * @author Philippe Tjon-A-Hen philippe@tjonahen.nl
 */
@WebServlet(urlPatterns = {"/upload"})
@MultipartConfig(location = "/tmp")
public class UploadResource extends HttpServlet {

    private static final Logger LOGGER = Logger.getLogger(UploadResource.class.getName());

    @EJB
    private TransactionProcessor transactionProcessor;

    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code> methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        try (PrintWriter out = response.getWriter()) {
            out.println("<!DOCTYPE html>");
            out.println("<html>");
            out.println("<head>");
            out.println("<title>CSV Transactions Upload Servlet</title>");
            out.println("</head>");
            out.println("<body>");
            out.println("Receiving the uploaded file ...<br>");
            out.println("Received " + request.getParts().size() + " parts ...<br>");
            for (Part part : request.getParts()) {
                final String fileName = part.getSubmittedFileName();
                if (processPart(part.getInputStream())) {
                    out.println("... process sucess... " + fileName + " part<br>");
                } else {
                    out.println("... process error... " + fileName + " part<br>");
                }
            }
            out.println("</body>");
            out.println("</html>");
        }
    }

    private boolean processPart(InputStream inputStream) throws FileNotFoundException {
        final CsvReader reader;
        try {
            reader = new CsvReader(new BufferedReader(new InputStreamReader(inputStream, "UTF-8")));
        } catch (UnsupportedEncodingException ex) {
            LOGGER.log(Level.SEVERE, null, ex);
            return false;
        }
        try {
            reader.readHeaders();
            while (reader.readRecord()) {
                Fintransactie trans = new Fintransactie();
                //                "Datum",
                final GregorianCalendar c = new GregorianCalendar();
                int count = 0;
                c.setTime(makeDate(reader.get(count++)));
                trans.setDatum(c.getTime());
//                "Naam / Omschrijving",
                trans.setTegenrekeningnaam(reader.get(count++));
//                "Rekening",
                trans.setRekening(reader.get(count++));
//                "Tegenrekening",
                trans.setTegenrekeningrekening(reader.get(count++));
//                "Code",
                trans.setCode(reader.get(count++));
//                "Af Bij",
                trans.setBijaf(reader.get(count++));
//                "Bedrag (EUR)",
                trans.setBedrag(Double.valueOf(reader.get(count++).replace(',', '.')));
//                "MutatieSoort",
                trans.setMutatiesoort(reader.get(count++));
//                "Mededelingen"
                trans.setMededeling(reader.get(count++));

                transactionProcessor.process(trans);
            }
        } catch (IOException | NumberFormatException ex) {
            LOGGER.severe(reader.getRawRecord());

        }
        reader.close();
        return true;
    }

    /**
     * Handles the HTTP <code>POST</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Processes uploaded stransaction files";
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

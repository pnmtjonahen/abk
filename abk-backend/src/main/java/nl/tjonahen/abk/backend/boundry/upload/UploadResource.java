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
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.EJB;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.script.ScriptException;
import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;
import nl.tjonahen.abk.backend.CsvJSScripting;
import nl.tjonahen.abk.backend.entity.CsvReader;
import nl.tjonahen.abk.backend.entity.Fintransactie;
import nl.tjonahen.abk.backend.model.FinancialTransaction;

/**
 *
 * @author Philippe Tjon-A-Hen philippe@tjonahen.nl
 */
@WebServlet(urlPatterns = {"/upload"})
@MultipartConfig(location = "/tmp")
public class UploadResource extends HttpServlet {

    private static final Logger LOGGER = Logger.getLogger(UploadResource.class.getName());
    private static final long serialVersionUID = 1L;

    @EJB
    private TransactionProcessor transactionProcessor;

    @PersistenceContext
    private EntityManager entityManager;

    private MessageDigest md;

    private CsvJSScripting scripting;
    
    private boolean headers;
    private boolean dryRun;

    @Override
    public void init() throws ServletException {
        super.init();

        final CsvReader reader = entityManager.createNamedQuery("CsvReader.findAll", CsvReader.class).getResultList().get(0);
        this.scripting = new CsvJSScripting(reader.getScript());
        this.headers = reader.isHeaders();
        this.dryRun = reader.isDryRun();
        try {
            this.md = MessageDigest.getInstance("MD5");
        } catch (NoSuchAlgorithmException ex) {
            throw new ServletException(ex);
        }
        
    }

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
                if (processPartJsParsing(part.getInputStream())) {
                    out.println("... process sucess... " + fileName + " part<br>");
                } else {
                    out.println("... process error... " + fileName + " part<br>");
                }
            }
            out.println("</body>");
            out.println("</html>");
        }
    }

 

    private boolean processPartJsParsing(InputStream inputStream) {
        try {
            new BufferedReader(new InputStreamReader(inputStream, "UTF-8"))
                    .lines()
                    .skip(headers ? 1 : 0)
                    .forEach(s -> {
                        try {
                            FinancialTransaction ft = scripting.parse(s);
                            Fintransactie trans = new Fintransactie();
                            trans.setDatum(makeDate(ft.getDate()));
                            trans.setTegenrekeningnaam(ft.getContraAccountName());
                            trans.setRekening(ft.getAccountNumber());
                            trans.setTegenrekeningrekening(ft.getContraAccountNumber());
                            trans.setCode(ft.getCode());
                            trans.setBijaf(ft.getDebitCreditIndicator().equals("debit") ? "Af" : "Bij");
                            trans.setBedrag(Double.valueOf(ft.getAmount().replace(',', '.')));
                            trans.setMutatiesoort(ft.getMutatiesoort());
                            trans.setMededeling(ft.getDescription());
                            updateHash(trans);
                            if (!dryRun) {
                                transactionProcessor.process(trans);
                            }
                        } catch (UnsupportedEncodingException | NumberFormatException | NoSuchMethodException | ScriptException ex) {
                            LOGGER.severe(ex.getMessage() + " data->" + s);
                        }
                    });
        } catch (UnsupportedEncodingException ex) {
            LOGGER.log(Level.SEVERE, null, ex);
            return false;
        }

        return true;
    }

        private void updateHash(Fintransactie ft) throws UnsupportedEncodingException {
        md.update(ft.getRekening().getBytes("UTF-8"));
        md.update(ft.getBedrag().toString().getBytes("UTF-8"));
        md.update(ft.getCode().getBytes("UTF-8"));
        md.update(ft.getTegenrekeningnaam().getBytes("UTF-8"));
        md.update(ft.getTegenrekeningrekening().getBytes("UTF-8"));
        final Date datum = ft.getDatum();

        final LocalDateTime ldt = LocalDateTime.ofInstant(
                Instant.ofEpochMilli(datum.getTime()), ZoneId.systemDefault());

        md.update(ldt.format(DateTimeFormatter.ISO_DATE).getBytes("UTF-8"));
        md.update(ft.getBijaf().getBytes("UTF-8"));
        md.update(ft.getMededeling().getBytes("UTF-8"));
        md.update(ft.getMutatiesoort().getBytes("UTF-8"));

        byte[] digest = md.digest();
        BigInteger bigInt = new BigInteger(1, digest);
        ft.setHash(bigInt.toString(16));
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
